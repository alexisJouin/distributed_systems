package tp_chat;

import java.net.Socket;

public class Client {
    private String name;
    private int numero;
    private Socket socket;
    
    public Client(){
        this.name = "default_name";
        this.numero = 0;
        this.socket = null;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setSocket(Socket s){
        this.socket = s;
    }
    
    public void setNumero(int num){
        this.numero = num;
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getNumero(){
        return this.numero;
    }
    
    public Socket getSocket(){
        return this.socket;
    }
    
    
}
