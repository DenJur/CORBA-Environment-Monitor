# Monitoring station record. Stores information about registered monitoring station.
class MonitoringStation:

    def __init__(self, name, group, location, settings):
        self.name = name
        self.group = group
        self.location = location
        self.readingsLog = []
        self.settings = settings
        self.failed_contacts = 0

    def add_reading(self, reading):
        self.readingsLog.insert(0, reading)

    # check if log of alarms warrants triggering an alarm event
    def should_raise(self):
        result = len(self.readingsLog) >= self.settings.steps
        # check last n alarm event time differences
        for i in range(1, self.settings.steps):
            if i < len(self.readingsLog):
                if self.readingsLog[i-1].date_time-self.readingsLog[i].date_time > self.settings.time:
                    return False
        return result
