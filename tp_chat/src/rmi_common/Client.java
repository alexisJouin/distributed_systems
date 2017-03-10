package rmi_common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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

public final class Client implements ClientInterface {

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
    private String name;

    ServerInterface serv;
    ClientInterface stub;

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void showYourName() throws RemoteException {
        System.out.println(name);
        frame.setTitle(name);
    }

    public Client() {
        try {
            stub = (ClientInterface) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry();
            serv = (ServerInterface) Naming.lookup("rmi://localhost:1099/server");
        } catch (Exception e) {
            e.printStackTrace();
        }
        buildUI();
    }

    /**
     * Gestion de l'interface Cr�ation de l'interface
     */
    public void buildUI() {

        frame = new JFrame("Super Chat 2000");
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
        // Fen�tre globale
        mainPanel.add(northPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setSize(640, 480);
        frame.setVisible(true);

        //Saisie du nom
        this.name = JOptionPane.showInputDialog("Saisir votre Pseudo : ");
        nomClient.setText(this.name + ": ");

        //Envoie du mail au server
        try {
            this.serv.register(this.stub);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * ** ActionListener ***
         */
        if (msgToSend.getText() != "") { //Test qui ne sert à rien !!

            //Quand on tape ENTRER
            msgToSend.addActionListener((ActionEvent e) -> {
                System.out.println("Key Enter : " + msgToSend.getText());
                sendMessage(name, msgToSend.getText());
            });

            //Quand on click sur le bouton
            sendButton.addActionListener((ActionEvent e) -> {
                System.out.println("Button Send : " + msgToSend.getText());
                sendMessage(name, msgToSend.getText());
            });
        }

    }

    @Override
    public void sendMessage(String name, String msgToSend) {
        try {
            this.serv.sendToAll(name, msgToSend);
            this.msgToSend.setText("");

        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setListCo(ArrayList<String> listClient) throws RemoteException {
       // System.out.println("Test convers :" + conversation);
       //ArrayList<ClientInterface> tabClients = conversation.getTabClientsInterface();
        //frame.setName("Conversation n°" + conversation.getId() + " : " + tabClients);
        this.model.clear();
        for (String clientCo : listClient) {
            this.model.addElement(clientCo);
        }
    }

    @Override
    public void setMessage(String client, String message) throws RemoteException {
        this.msgs.append("\n[" + client + "] => " + message + " [TO ALL]");
    }
    
    @Override
    public void sendConversation(Conversation conversation) throws RemoteException {
        msgs.removeAll();
        
        //On remets tous les messages stockés de la conversation
        for(String msg : conversation.getTabMessages()){
            msgs.append(msg);
        }
    }
    
    public static void main(String[] args) {
        Client client = new Client();
    }

   
}
