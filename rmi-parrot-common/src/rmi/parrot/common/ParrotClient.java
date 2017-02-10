
package rmi.parrot.common;

import java.rmi.Naming;


public class ParrotClient {
    
    public static void main(String[] args) {
        try{
            Parrot p = (Parrot) Naming.lookup("rmi://localhost/Jacquot");
            // aussi : LocateRegistry.getRegistry().lookup("rmi://localhost/Jacquot");
            // En cas de changement de numéro de port : Naming.lookup("rmi://localhost:1099/Jacquot");
            
            p.repete("Salut ! ");
            System.out.println("Chez mon perroquet il est : " + p.quelleHeureEstIl());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
