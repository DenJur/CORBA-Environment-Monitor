package Environment;

/**
* Environment/RegionalCentreHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Environment.idl
* Friday, April 6, 2018 at 1:44:03 AM British Summer Time
*/

public final class RegionalCentreHolder implements org.omg.CORBA.portable.Streamable
{
  public Environment.RegionalCentre value = null;

  public RegionalCentreHolder ()
  {
  }

  public RegionalCentreHolder (Environment.RegionalCentre initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Environment.RegionalCentreHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Environment.RegionalCentreHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Environment.RegionalCentreHelper.type ();
  }

}
