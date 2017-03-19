package arena_rmi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ArenaStore implements ArenaClientInterface {

    private JFrame frm;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    private JLabel available;
    private JTextField customer;
    private JTextField nbPlaces;

    private JButton bookButton;
    private JButton listerButton;
    ArenaServerInterface serv;
    ArenaClientInterface stub;

    private String nom;

    public ArenaStore() {

        try {
            stub = (ArenaClientInterface) UnicastRemoteObject.exportObject((Remote) this, 0);
            Registry registry = LocateRegistry.getRegistry();
            serv = (ArenaServerInterface) Naming.lookup("rmi://localhost:1099/server");

            System.out.println("Client Start");

            buildUI();
            addListeners();

            frm.setSize(600, 250);
            frm.setVisible(true);

            startListening();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startListening() {

        int nbPlaces;
        try {
            nbPlaces = serv.getCapacity(); //by default
            available.setText("" + nbPlaces);
        } catch (RemoteException ex) {
            Logger.getLogger(ArenaStore.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void addListeners() {

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                nom = customer.getText();
                sendClient();
                sendNbPlaces(Integer.parseInt(nbPlaces.getText()));
                customer.setText("");
                nbPlaces.setText("");
                customer.grabFocus();

                try {
                    available.setText(Integer.toString(serv.getCapacity()));
                } catch (RemoteException ex) {
                    Logger.getLogger(ArenaStore.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        listerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String liste = "";
                try {
                    Vector<ArenaClientInterface> clients = serv.getClients();
                    for (ArenaClientInterface client  : clients) {
                        liste += client.getNom() + " ;";
                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(ArenaStore.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                JOptionPane.showMessageDialog(frm, liste, "Liste des inscrits", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    socket.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                };
                System.exit(0);
            }
        });

    }

    private void buildUI() {
        frm = new JFrame("Reservations");
        frm.getContentPane().setLayout(new BorderLayout());

        JLabel title = new JLabel("Nombre de places restantes : ");
        title.setHorizontalAlignment(JLabel.HORIZONTAL);
        frm.add(title, BorderLayout.NORTH);
        available = new JLabel("unknown");
        available.setFont(new Font("Times", Font.PLAIN, 50));
        available.setHorizontalAlignment(JLabel.HORIZONTAL);
        frm.add(available, BorderLayout.CENTER);

        JPanel booking = new JPanel();
        booking.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        booking.add(new JLabel("Client : "));
        customer = new JTextField();
        customer.setColumns(10);
        booking.add(customer);
        booking.add(new JLabel("Nb places : "));
        nbPlaces = new JTextField();
        nbPlaces.setColumns(2);
        booking.add(nbPlaces);

        bookButton = new JButton("Reserver");
        listerButton = new JButton("Lister");
        booking.add(bookButton);
        booking.add(listerButton);

        frm.add(booking, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        new ArenaStore();
    }

    @Override
    public void sendClient() {
        try {
            serv.registry(this);
        } catch (RemoteException ex) {
            Logger.getLogger(ArenaStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendNbPlaces(int nbPlaces) {
        try {
            serv.setNbPlaces(nbPlaces);
        } catch (RemoteException ex) {
            Logger.getLogger(ArenaStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getNom() throws RemoteException {
        return this.nom;
    }
}
