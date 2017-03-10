package chat;


/**
* chat/ChatClientOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from chat.idl
* vendredi 10 mars 2017 10 h 05 CET
*/

public interface ChatClientOperations 
{
  int id ();
  void id (int newId);
  String nom ();
  void nom (String newNom);
  void sendMessage (chat.ChatClient client, String msg);
  void setMessage (String nom, String msg);
  void setListCo (chat.ChatClient[] clients);
  int getId ();
  String getName ();
} // interface ChatClientOperations