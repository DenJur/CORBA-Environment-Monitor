# Python stubs generated by omniidl from Environment.idl
# DO NOT EDIT THIS FILE!

import omniORB, _omnipy
from omniORB import CORBA, PortableServer
_0_CORBA = CORBA


_omnipy.checkVersion(4,2, __file__, 1)

try:
    property
except NameError:
    def property(*args):
        return None


#
# Start of module "Environment"
#
__name__ = "Environment"
_0_Environment = omniORB.openModule("Environment", r"Environment.idl")
_0_Environment__POA = omniORB.openModule("Environment__POA", r"Environment.idl")


# struct Reading
_0_Environment.Reading = omniORB.newEmptyClass()
class Reading (omniORB.StructBase):
    _NP_RepositoryId = "IDL:Environment/Reading:1.0"

    def __init__(self, date_time, station_name, status, group, location, alarm_level, reading_value):
        self.date_time = date_time
        self.station_name = station_name
        self.status = status
        self.group = group
        self.location = location
        self.alarm_level = alarm_level
        self.reading_value = reading_value

_0_Environment.Reading = Reading
_0_Environment._d_Reading  = (omniORB.tcInternal.tv_struct, Reading, Reading._NP_RepositoryId, "Reading", "date_time", omniORB.tcInternal.tv_longlong, "station_name", (omniORB.tcInternal.tv_string,0), "status", omniORB.tcInternal.tv_boolean, "group", (omniORB.tcInternal.tv_string,0), "location", (omniORB.tcInternal.tv_string,0), "alarm_level", omniORB.tcInternal.tv_float, "reading_value", omniORB.tcInternal.tv_float)
_0_Environment._tc_Reading = omniORB.tcInternal.createTypeCode(_0_Environment._d_Reading)
omniORB.registerType(Reading._NP_RepositoryId, _0_Environment._d_Reading, _0_Environment._tc_Reading)
del Reading

# interface MonitoringStation
_0_Environment._d_MonitoringStation = (omniORB.tcInternal.tv_objref, "IDL:Environment/MonitoringStation:1.0", "MonitoringStation")
omniORB.typeMapping["IDL:Environment/MonitoringStation:1.0"] = _0_Environment._d_MonitoringStation
_0_Environment.MonitoringStation = omniORB.newEmptyClass()
class MonitoringStation :
    _NP_RepositoryId = _0_Environment._d_MonitoringStation[1]

    def __init__(self, *args, **kw):
        raise RuntimeError("Cannot construct objects of this type.")

    _nil = CORBA.Object._nil


_0_Environment.MonitoringStation = MonitoringStation
_0_Environment._tc_MonitoringStation = omniORB.tcInternal.createTypeCode(_0_Environment._d_MonitoringStation)
omniORB.registerType(MonitoringStation._NP_RepositoryId, _0_Environment._d_MonitoringStation, _0_Environment._tc_MonitoringStation)

# MonitoringStation operations and attributes
MonitoringStation._d__get_station_name = ((),((omniORB.tcInternal.tv_string,0),),None)
MonitoringStation._d__get_group_name = ((),((omniORB.tcInternal.tv_string,0),),None)
MonitoringStation._d__get_location = ((),((omniORB.tcInternal.tv_string,0),),None)
MonitoringStation._d__get_alarming_level = ((),(omniORB.tcInternal.tv_float,),None)
MonitoringStation._d__set_alarming_level = ((omniORB.tcInternal.tv_float,),(),None)
MonitoringStation._d_get_reading = ((), (omniORB.typeMapping["IDL:Environment/Reading:1.0"], ), None)
MonitoringStation._d_turn_on = ((), (), None)
MonitoringStation._d_turn_off = ((), (), None)
MonitoringStation._d_reset = ((), (), None)

# MonitoringStation object reference
class _objref_MonitoringStation (CORBA.Object):
    _NP_RepositoryId = MonitoringStation._NP_RepositoryId

    def __init__(self, obj):
        CORBA.Object.__init__(self, obj)

    def _get_station_name(self, *args):
        return self._obj.invoke("_get_station_name", _0_Environment.MonitoringStation._d__get_station_name, args)

    station_name = property(_get_station_name)


    def _get_group_name(self, *args):
        return self._obj.invoke("_get_group_name", _0_Environment.MonitoringStation._d__get_group_name, args)

    group_name = property(_get_group_name)


    def _get_location(self, *args):
        return self._obj.invoke("_get_location", _0_Environment.MonitoringStation._d__get_location, args)

    location = property(_get_location)


    def _get_alarming_level(self, *args):
        return self._obj.invoke("_get_alarming_level", _0_Environment.MonitoringStation._d__get_alarming_level, args)

    def _set_alarming_level(self, *args):
        return self._obj.invoke("_set_alarming_level", _0_Environment.MonitoringStation._d__set_alarming_level, args)

    alarming_level = property(_get_alarming_level, _set_alarming_level)


    def get_reading(self, *args):
        return self._obj.invoke("get_reading", _0_Environment.MonitoringStation._d_get_reading, args)

    def turn_on(self, *args):
        return self._obj.invoke("turn_on", _0_Environment.MonitoringStation._d_turn_on, args)

    def turn_off(self, *args):
        return self._obj.invoke("turn_off", _0_Environment.MonitoringStation._d_turn_off, args)

    def reset(self, *args):
        return self._obj.invoke("reset", _0_Environment.MonitoringStation._d_reset, args)

omniORB.registerObjref(MonitoringStation._NP_RepositoryId, _objref_MonitoringStation)
_0_Environment._objref_MonitoringStation = _objref_MonitoringStation
del MonitoringStation, _objref_MonitoringStation

# MonitoringStation skeleton
__name__ = "Environment__POA"
class MonitoringStation (PortableServer.Servant):
    _NP_RepositoryId = _0_Environment.MonitoringStation._NP_RepositoryId


    _omni_op_d = {"_get_station_name": _0_Environment.MonitoringStation._d__get_station_name, "_get_group_name": _0_Environment.MonitoringStation._d__get_group_name, "_get_location": _0_Environment.MonitoringStation._d__get_location, "_get_alarming_level": _0_Environment.MonitoringStation._d__get_alarming_level, "_set_alarming_level": _0_Environment.MonitoringStation._d__set_alarming_level, "get_reading": _0_Environment.MonitoringStation._d_get_reading, "turn_on": _0_Environment.MonitoringStation._d_turn_on, "turn_off": _0_Environment.MonitoringStation._d_turn_off, "reset": _0_Environment.MonitoringStation._d_reset}

MonitoringStation._omni_skeleton = MonitoringStation
_0_Environment__POA.MonitoringStation = MonitoringStation
omniORB.registerSkeleton(MonitoringStation._NP_RepositoryId, MonitoringStation)
del MonitoringStation
__name__ = "Environment"

# typedef ... Log_of_alarm_readings
class Log_of_alarm_readings:
    _NP_RepositoryId = "IDL:Environment/Log_of_alarm_readings:1.0"
    def __init__(self, *args, **kw):
        raise RuntimeError("Cannot construct objects of this type.")
_0_Environment.Log_of_alarm_readings = Log_of_alarm_readings
_0_Environment._d_Log_of_alarm_readings  = (omniORB.tcInternal.tv_sequence, omniORB.typeMapping["IDL:Environment/Reading:1.0"], 0)
_0_Environment._ad_Log_of_alarm_readings = (omniORB.tcInternal.tv_alias, Log_of_alarm_readings._NP_RepositoryId, "Log_of_alarm_readings", (omniORB.tcInternal.tv_sequence, omniORB.typeMapping["IDL:Environment/Reading:1.0"], 0))
_0_Environment._tc_Log_of_alarm_readings = omniORB.tcInternal.createTypeCode(_0_Environment._ad_Log_of_alarm_readings)
omniORB.registerType(Log_of_alarm_readings._NP_RepositoryId, _0_Environment._ad_Log_of_alarm_readings, _0_Environment._tc_Log_of_alarm_readings)
del Log_of_alarm_readings

# typedef ... Set_of_readings
class Set_of_readings:
    _NP_RepositoryId = "IDL:Environment/Set_of_readings:1.0"
    def __init__(self, *args, **kw):
        raise RuntimeError("Cannot construct objects of this type.")
_0_Environment.Set_of_readings = Set_of_readings
_0_Environment._d_Set_of_readings  = (omniORB.tcInternal.tv_sequence, omniORB.typeMapping["IDL:Environment/Reading:1.0"], 0)
_0_Environment._ad_Set_of_readings = (omniORB.tcInternal.tv_alias, Set_of_readings._NP_RepositoryId, "Set_of_readings", (omniORB.tcInternal.tv_sequence, omniORB.typeMapping["IDL:Environment/Reading:1.0"], 0))
_0_Environment._tc_Set_of_readings = omniORB.tcInternal.createTypeCode(_0_Environment._ad_Set_of_readings)
omniORB.registerType(Set_of_readings._NP_RepositoryId, _0_Environment._ad_Set_of_readings, _0_Environment._tc_Set_of_readings)
del Set_of_readings

# interface RegionalCentre
_0_Environment._d_RegionalCentre = (omniORB.tcInternal.tv_objref, "IDL:Environment/RegionalCentre:1.0", "RegionalCentre")
omniORB.typeMapping["IDL:Environment/RegionalCentre:1.0"] = _0_Environment._d_RegionalCentre
_0_Environment.RegionalCentre = omniORB.newEmptyClass()
class RegionalCentre :
    _NP_RepositoryId = _0_Environment._d_RegionalCentre[1]

    def __init__(self, *args, **kw):
        raise RuntimeError("Cannot construct objects of this type.")

    _nil = CORBA.Object._nil


_0_Environment.RegionalCentre = RegionalCentre
_0_Environment._tc_RegionalCentre = omniORB.tcInternal.createTypeCode(_0_Environment._d_RegionalCentre)
omniORB.registerType(RegionalCentre._NP_RepositoryId, _0_Environment._d_RegionalCentre, _0_Environment._tc_RegionalCentre)

# RegionalCentre operations and attributes
RegionalCentre._d__get_name = ((),((omniORB.tcInternal.tv_string,0),),None)
RegionalCentre._d__get_log = ((),(omniORB.typeMapping["IDL:Environment/Log_of_alarm_readings:1.0"],),None)
RegionalCentre._d_raise_alarm = ((omniORB.typeMapping["IDL:Environment/Reading:1.0"], ), (), None)
RegionalCentre._d_take_readings = ((), (omniORB.typeMapping["IDL:Environment/Set_of_readings:1.0"], ), None)
RegionalCentre._d_add_monitoring_station = (((omniORB.tcInternal.tv_string,0), (omniORB.tcInternal.tv_string,0), (omniORB.tcInternal.tv_string,0)), (), None)

# RegionalCentre object reference
class _objref_RegionalCentre (CORBA.Object):
    _NP_RepositoryId = RegionalCentre._NP_RepositoryId

    def __init__(self, obj):
        CORBA.Object.__init__(self, obj)

    def _get_name(self, *args):
        return self._obj.invoke("_get_name", _0_Environment.RegionalCentre._d__get_name, args)

    name = property(_get_name)


    def _get_log(self, *args):
        return self._obj.invoke("_get_log", _0_Environment.RegionalCentre._d__get_log, args)

    log = property(_get_log)


    def raise_alarm(self, *args):
        return self._obj.invoke("raise_alarm", _0_Environment.RegionalCentre._d_raise_alarm, args)

    def take_readings(self, *args):
        return self._obj.invoke("take_readings", _0_Environment.RegionalCentre._d_take_readings, args)

    def add_monitoring_station(self, *args):
        return self._obj.invoke("add_monitoring_station", _0_Environment.RegionalCentre._d_add_monitoring_station, args)

omniORB.registerObjref(RegionalCentre._NP_RepositoryId, _objref_RegionalCentre)
_0_Environment._objref_RegionalCentre = _objref_RegionalCentre
del RegionalCentre, _objref_RegionalCentre

# RegionalCentre skeleton
__name__ = "Environment__POA"
class RegionalCentre (PortableServer.Servant):
    _NP_RepositoryId = _0_Environment.RegionalCentre._NP_RepositoryId


    _omni_op_d = {"_get_name": _0_Environment.RegionalCentre._d__get_name, "_get_log": _0_Environment.RegionalCentre._d__get_log, "raise_alarm": _0_Environment.RegionalCentre._d_raise_alarm, "take_readings": _0_Environment.RegionalCentre._d_take_readings, "add_monitoring_station": _0_Environment.RegionalCentre._d_add_monitoring_station}

RegionalCentre._omni_skeleton = RegionalCentre
_0_Environment__POA.RegionalCentre = RegionalCentre
omniORB.registerSkeleton(RegionalCentre._NP_RepositoryId, RegionalCentre)
del RegionalCentre
__name__ = "Environment"

# interface EnvironmentalCentre
_0_Environment._d_EnvironmentalCentre = (omniORB.tcInternal.tv_objref, "IDL:Environment/EnvironmentalCentre:1.0", "EnvironmentalCentre")
omniORB.typeMapping["IDL:Environment/EnvironmentalCentre:1.0"] = _0_Environment._d_EnvironmentalCentre
_0_Environment.EnvironmentalCentre = omniORB.newEmptyClass()
class EnvironmentalCentre :
    _NP_RepositoryId = _0_Environment._d_EnvironmentalCentre[1]

    def __init__(self, *args, **kw):
        raise RuntimeError("Cannot construct objects of this type.")

    _nil = CORBA.Object._nil


_0_Environment.EnvironmentalCentre = EnvironmentalCentre
_0_Environment._tc_EnvironmentalCentre = omniORB.tcInternal.createTypeCode(_0_Environment._d_EnvironmentalCentre)
omniORB.registerType(EnvironmentalCentre._NP_RepositoryId, _0_Environment._d_EnvironmentalCentre, _0_Environment._tc_EnvironmentalCentre)

# EnvironmentalCentre operations and attributes
EnvironmentalCentre._d_raise_alarm = (((omniORB.tcInternal.tv_string,0), omniORB.typeMapping["IDL:Environment/Set_of_readings:1.0"]), (), None)
EnvironmentalCentre._d_register_agency = (((omniORB.tcInternal.tv_string,0), (omniORB.tcInternal.tv_string,0), (omniORB.tcInternal.tv_string,0)), (), None)
EnvironmentalCentre._d_register_regional_centre = (((omniORB.tcInternal.tv_string,0), ), (), None)

# EnvironmentalCentre object reference
class _objref_EnvironmentalCentre (CORBA.Object):
    _NP_RepositoryId = EnvironmentalCentre._NP_RepositoryId

    def __init__(self, obj):
        CORBA.Object.__init__(self, obj)

    def raise_alarm(self, *args):
        return self._obj.invoke("raise_alarm", _0_Environment.EnvironmentalCentre._d_raise_alarm, args)

    def register_agency(self, *args):
        return self._obj.invoke("register_agency", _0_Environment.EnvironmentalCentre._d_register_agency, args)

    def register_regional_centre(self, *args):
        return self._obj.invoke("register_regional_centre", _0_Environment.EnvironmentalCentre._d_register_regional_centre, args)

omniORB.registerObjref(EnvironmentalCentre._NP_RepositoryId, _objref_EnvironmentalCentre)
_0_Environment._objref_EnvironmentalCentre = _objref_EnvironmentalCentre
del EnvironmentalCentre, _objref_EnvironmentalCentre

# EnvironmentalCentre skeleton
__name__ = "Environment__POA"
class EnvironmentalCentre (PortableServer.Servant):
    _NP_RepositoryId = _0_Environment.EnvironmentalCentre._NP_RepositoryId


    _omni_op_d = {"raise_alarm": _0_Environment.EnvironmentalCentre._d_raise_alarm, "register_agency": _0_Environment.EnvironmentalCentre._d_register_agency, "register_regional_centre": _0_Environment.EnvironmentalCentre._d_register_regional_centre}

EnvironmentalCentre._omni_skeleton = EnvironmentalCentre
_0_Environment__POA.EnvironmentalCentre = EnvironmentalCentre
omniORB.registerSkeleton(EnvironmentalCentre._NP_RepositoryId, EnvironmentalCentre)
del EnvironmentalCentre
__name__ = "Environment"

#
# End of module "Environment"
#
__name__ = "Environment_idl"

_exported_modules = ( "Environment", )

# The end.
