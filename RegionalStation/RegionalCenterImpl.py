import logging
import math
import pickle
import traceback
from threading import Lock

import CosNaming

import Environment__POA
from Models.MonitoringStation import MonitoringStation


# Implementation of IDL POA class
class RegionalCenterImpl(Environment__POA.RegionalCentre):

    def __init__(self, settings, center, root):
        self.settings = settings
        self.center = center
        self.stationList = []
        self.rootContext = root
        self.lock = Lock()
        if self.settings.load:
            with open('stationList.out', 'rb') as input:
                self.stationList = pickle.load(input)

    # called by monitoring station to try and raise an alarm
    def raise_alarm(self, reading):
        self.lock.acquire(True)
        try:
            station = next(x for x in self.stationList if x.name == reading.station_name)
            station.add_reading(reading)
            readings = [reading]
            # check the log to see if we should consider raising an alarm
            if station.should_raise():
                # get all station in the same group
                verification_stations = list(filter(lambda a: a.group == station.group and a.name != station.name,
                                                    self.stationList))
                confirmations = 0
                stations_asked = 0
                for vstation in verification_stations:
                    try:
                        # get reading from verification stations
                        station_name = [CosNaming.NameComponent(vstation.name, "")]
                        station_remote = self.rootContext.resolve(station_name)
                        ver_reading = station_remote.get_reading()
                        readings.append(ver_reading)
                        # reset failed contacts
                        vstation.failed_contacts = 0
                        if ver_reading.status:
                            # if contacted successfully, check reading value vs alarm threshold
                            stations_asked += 1
                            if ver_reading.reading_value >= ver_reading.alarm_level:
                                confirmations += 1
                    except CosNaming.NamingContext.NotFound:
                        logging.warning("Station not found in the name list: " + station.name)
                        self.failed_to_contact_station(station)
                    else:
                        logging.warning("Error getting data from a station: " + station.name)
                        self.failed_to_contact_station(station)
                # check that we got enough verifications to warrant triggering alarm at environmental centre
                confirmations_needed = math.floor((stations_asked + 1) / 2)
                if confirmations >= confirmations_needed:
                    centre_name = [CosNaming.NameComponent(self.settings.centre, "")]
                    centre_remote = self.rootContext.resolve(centre_name)
                    centre_remote.raise_alarm(self.settings.name, readings)
        except StopIteration:
            logging.warning("Received alarm from an unregistered station " + reading.station_name)
        except:
            traceback.print_exc()
            logging.error("Error processing alarm")
        self.lock.release()

    def add_monitoring_station(self, station_name, station_group, station_location):
        self.lock.acquire(True)
        # remove any stations that have the same name
        self.stationList = list(filter(lambda a: a.name != station_name, self.stationList))
        self.stationList.append(MonitoringStation(name=station_name, group=station_group,
                                                  location=station_location, settings=self.settings))
        # save station list to disk for future use
        with open('stationList.out', 'wb') as output:
            pickle.dump(self.stationList, output, pickle.HIGHEST_PROTOCOL)
        self.lock.release()

    # Get full alarm log from all registered stations
    def log(self):
        reading_set = []
        for station in self.stationList:
            reading_set += station.readingsLog
        # sort reading by time
        reading_set.sort(key=lambda x: x.date_time, reverse=False)
        return reading_set

    def name(self):
        return self.settings.name

    # Collect readings from all stations
    def take_readings(self):
        self.lock.acquire(True)
        reading_set = []
        for station in self.stationList:
            try:
                station_name = [CosNaming.NameComponent(station.name, "")]
                station_remote = self.rootContext.resolve(station_name)
                reading_set.append(station_remote.get_reading())
                # reset failed contacts
                station.failed_contacts = 0
            except CosNaming.NamingContext.NotFound:
                logging.warning("Station not found in the name list: " + station.name)
                self.failed_to_contact_station(station)
            else:
                logging.warning("Error getting data from a station: " + station.name)
                self.failed_to_contact_station(station)
        self.lock.release()
        return reading_set

    # Remove station from the list after 10 failed contacts.
    def failed_to_contact_station(self, station):
        station.failed_contacts += 1
        if station.failed_contacts > 10:
            self.stationList = list(filter(lambda a: a.name != station.name, self.stationList))
