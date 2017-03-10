package chat.ChatServerPackage;


/**
* chat/ChatServerPackage/clientsHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from chat.idl
* vendredi 10 mars 2017 10 h 05 CET
*/

abstract public class clientsHelper
{
  private static String  _id = "IDL:chat/ChatServer/clients:1.0";

  public static void insert (org.omg.CORBA.Any a, chat.ChatClient[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static chat.ChatClient[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = chat.ChatClientHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (chat.ChatServerPackage.clientsHelper.id (), "clients", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static chat.ChatClient[] read (org.omg.CORBA.portable.InputStream istream)
  {
    chat.ChatClient value[] = null;
    int _len0 = istream.read_long ();
    value = new chat.ChatClient[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = chat.ChatClientHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, chat.ChatClient[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      chat.ChatClientHelper.write (ostream, value[_i0]);
  }

}
