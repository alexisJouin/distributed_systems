package chat_impl;

import chat.ChatClient;
import chat.ChatClientHelper;
import chat.ChatClientPOA;
import chat.ChatServer;
import chat.ChatServerHelper;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

public class ChatClientImpl extends ChatClientPOA {

    //Attribut pour info client
    private int id;
    private String nom;

    private ORB orb;
    private ChatServer server;
    private ChatClient cref;

    //Attribtuts pour l'interface
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JButton sendButton;
    private JTextArea msgs;
    private JTextField msgToSend;
    private JLabel nomClient;
    private String msgSend;
    private JScrollPane scrollMsgs;
    private JScrollPane scrollListCo;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> listCo;

    public void buildUI() {

        frame = new JFrame("Messenger");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panels
        mainPanel = new JPanel(new BorderLayout());
        northPanel = new JPanel(new BorderLayout());
        southPanel = new JPanel(new BorderLayout());

        // Elements des panels
        msgs = new JTextArea("Liste des messages :");
        listCo = new JList<>(model);
        msgToSend = new JTextField();
        sendButton = new JButton("Envoyer");
        nomClient = new JLabel("");

        /**
         * Style des composants
         */
        Font font = new Font("Arial", Font.PLAIN, 20);
        msgs.setFont(font);
        msgs.setBackground(Color.RED);
        msgs.setEditable(false);

        msgToSend.setFont(font);
        msgToSend.setBackground(Color.GREEN);
        msgToSend.setForeground(Color.BLACK);

        listCo.setFont(font);
        listCo.setBackground(Color.CYAN);
        listCo.setForeground(Color.WHITE);

        sendButton.setFont(font);

        //ScrollBar
        scrollMsgs = new JScrollPane(msgs);
        scrollListCo = new JScrollPane(listCo);

        // Assemblage des éléments
        // Nord
        northPanel.add(scrollMsgs, BorderLayout.CENTER);
        northPanel.add(scrollListCo, BorderLayout.EAST);
        // Sud
        southPanel.add(nomClient, BorderLayout.WEST);
        southPanel.add(msgToSend, BorderLayout.CENTER);
        southPanel.add(sendButton, BorderLayout.EAST);
        // Fenetre globale
        mainPanel.add(northPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setSize(640, 480);
        frame.setVisible(true);

        //Saisie du nom
        this.nom(JOptionPane.showInputDialog("Saisir votre Pseudo : "));
        nomClient.setText(this.nom + ": ");

        /**
         * ** ActionListener ***
         */
        if (msgToSend.getText() != "") { //Test qui ne sert à rien !!

            //Quand on tape ENTRER
            msgToSend.addActionListener((ActionEvent e) -> {
                System.out.println("Key Enter : " + msgToSend.getText());
                sendMessage(cref, msgToSend.getText());
            });

            //Quand on click sur le bouton
            sendButton.addActionListener((ActionEvent e) -> {
                System.out.println("Button Send : " + msgToSend.getText());
                sendMessage(cref, msgToSend.getText());
            });
        }
        //Quand on ferme l'interface on déconnecte !
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Etes vous sur de vouloir vous déconnecter ?", "Quitter le SUPER CHAT 2000 ?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    deconnecte();
                    System.exit(0);
                } else {
                    deconnecte();
                    System.exit(0);
                }
            }
        });

    }

    public ChatClientImpl(String[] args) {
        try {
            //Initialisation et démarrage de l'ORB : 
            //l'appel à orb.run() étant bloquant, on l'éxecute dans un thread
            orb = ORB.init(args, null);
            Thread orbThread = new Thread(orb::run);
            orbThread.start();

            //Création de la référence CORBA sur l'implémentation du client
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(this);
            cref = ChatClientHelper.narrow(ref);

            //Lecture de l'IOR du serveur
            BufferedReader br = new BufferedReader(new FileReader("server_ior.txt"));
            String ior = br.readLine();
            br.close();

            //Récupération de la référence sur le serveur (à partir de son IOR)
            org.omg.CORBA.Object o = orb.string_to_object(ior);
            server = ChatServerHelper.narrow(o);

            System.out.println("Client Start !");
            this.buildUI();

            System.out.println("Client : " + this.cref.nom() + " connecté");

            server.register(cref);

        } catch (InvalidName ex) {
            Logger.getLogger(ChatClientImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AdapterInactive ex) {
            Logger.getLogger(ChatClientImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServantNotActive ex) {
            Logger.getLogger(ChatClientImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WrongPolicy ex) {
            Logger.getLogger(ChatClientImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChatClientImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        new ChatClientImpl(args);
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public void id(int newId) {
        this.id = newId;
    }

    @Override
    public String nom() {
        return this.nom;
    }

    @Override
    public void nom(String newNom) {
        this.nom = newNom;
    }

    @Override
    public void sendMessage(ChatClient client, String msg) {
        this.server.sendToAll(client, msg);
    }

    @Override
    public void setMessage(String nom, String msg) {
        this.msgToSend.setText("");
        this.msgs.append("\n[" + nom + "] => " + msg + " [TO ALL]");
    }

    @Override
    public void setListCo(ChatClient[] clients) {
        this.model.clear();
        for (ChatClient clientCo : clients) {
            this.model.addElement(clientCo.nom());
        }
    }

    @Override
    public void deconnecte() {
        this.server.supprimerClient(this.cref);
    }

}
