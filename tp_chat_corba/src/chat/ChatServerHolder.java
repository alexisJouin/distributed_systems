package chat;

/**
* chat/ChatServerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from chat.idl
* vendredi 10 mars 2017 10 h 05 CET
*/

public final class ChatServerHolder implements org.omg.CORBA.portable.Streamable
{
  public chat.ChatServer value = null;

  public ChatServerHolder ()
  {
  }

  public ChatServerHolder (chat.ChatServer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = chat.ChatServerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    chat.ChatServerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return chat.ChatServerHelper.type ();
  }

}
