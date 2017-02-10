
package rmi_common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JButton;
import javax.swing.JFrame;


public class Client implements ClientInterface {

    JFrame frm = new JFrame();
    JButton b;
    
    ServerInterface serv;
    ClientInterface stub;
    
    
    @Override
    public void showYourName() throws RemoteException {
        String name = "I'm the Best";
        System.out.println(name);
        frm.setTitle(name);
        b.setText(name);
        
    }
    
    public Client(){
        try{
            stub = (ClientInterface) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry();
            serv = (ServerInterface) Naming.lookup("rmi://localhost:1099/server");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        buildUI();
    }
    
    private void buildUI(){
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        b = new JButton("Click me");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    serv.sayHello();
                    serv.register(stub);
                }catch(RemoteException re){
                    re.printStackTrace();
                }
            }
        });
        
        frm.getContentPane().add(b);
        frm.setSize(300,200);
        frm.setVisible(true);
        
        System.out.println("Client.buildUI()");
    }
    
    public static void main(String[] args){
        Client client = new Client();
    }
    
}
