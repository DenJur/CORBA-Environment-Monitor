package Environment;

/**
* Environment/EnvironmentalCentreHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Environment.idl
* Friday, April 6, 2018 at 1:44:03 AM British Summer Time
*/

public final class EnvironmentalCentreHolder implements org.omg.CORBA.portable.Streamable
{
  public Environment.EnvironmentalCentre value = null;

  public EnvironmentalCentreHolder ()
  {
  }

  public EnvironmentalCentreHolder (Environment.EnvironmentalCentre initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Environment.EnvironmentalCentreHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Environment.EnvironmentalCentreHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Environment.EnvironmentalCentreHelper.type ();
  }

}