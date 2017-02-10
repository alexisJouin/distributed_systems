/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.parrot.common;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

/**
 *
 * @author Alexis
 */
public class Parrotimpl implements Parrot {

    @Override
    public void repete(String msg) throws RemoteException {
        System.out.println("Je repete " + msg);
    }

    @Override
    public Date quelleHeureEstIl() throws RemoteException {
        Date d = new Date();
        System.out.println("J'envoie la date : " + d);
        return d;
    }

    public Parrotimpl() {
        try {
            String name = "Jacquot";
            Parrot stub = (Parrot) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(name, stub);

            System.out.print("Parrot à l'écoute");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Parrot parrot = new Parrotimpl();
    }

}
