package Environment;


/**
* Environment/Reading.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Environment.idl
* Friday, April 6, 2018 at 1:44:02 AM British Summer Time
*/

public final class Reading implements org.omg.CORBA.portable.IDLEntity
{
  public long date_time = (long)0;
  public String station_name = null;
  public boolean status = false;
  public String location = null;
  public String group = null;
  public float alarm_level = (float)0;
  public float reading_value = (float)0;

  public Reading ()
  {
  } // ctor

  public Reading (long _date_time, String _station_name, boolean _status, String _location, String _group, float _alarm_level, float _reading_value)
  {
    date_time = _date_time;
    station_name = _station_name;
    status = _status;
    location = _location;
    group = _group;
    alarm_level = _alarm_level;
    reading_value = _reading_value;
  } // ctor

} // class Reading
