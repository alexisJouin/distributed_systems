package tp_chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ActionListener.FieldListener;
import ActionListener.FieldMouseListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class ChatInterface {

    private ChatClient client;
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

    /**
     * Constructeur par défaut - Intteraction CLIENT / SERVEUR
     */
    public ChatInterface(ChatClient c) {
        this.client = c;
    }

    /**
     * Gestion de l'interface Cr�ation de l'interface
     */
    public void run() {

        frame = new JFrame("Super Chat 2000");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panels
        mainPanel = new JPanel(new BorderLayout());
        northPanel = new JPanel(new BorderLayout());
        southPanel = new JPanel(new BorderLayout());

        // Elements des panels
        msgs = new JTextArea("Liste des messages :");
        listCo = new JList<>(model);
        msgToSend = new JTextField("Entrez votre message ! ");
        sendButton = new JButton("Envoyer");
        nomClient = new JLabel("Toto");

        /**
         * Style des composants
         */
        Font font = new Font("Arial", Font.PLAIN, 20);
        msgs.setFont(font);
        msgs.setEditable(false);

        msgToSend.setFont(font);
        msgToSend.setBackground(Color.lightGray);
        msgToSend.setForeground(Color.BLACK);

        listCo.setFont(font);
        listCo.setBackground(Color.darkGray);
        listCo.setForeground(Color.WHITE);

        sendButton.setFont(font);

        // ActionListener
        msgToSend.addActionListener(new FieldListener(msgToSend));
        msgToSend.addMouseListener(new FieldMouseListener(msgToSend));

        // Si text n'est pas vide on enregistre le message et on envoie au client
        if (msgToSend.getText() != "") {
            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setMsgToSend(msgToSend.getText());
                    msgToSend.setText("");
                    //client.sendMessage(msgToSend.getText());
                }
            });
        }

//        InputMap im = sendButton.getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
//        ActionMap am = sendButton.getActionMap();
//
//        im.put(KeyStroke.getKeyStroke(msgSend).getKeyStroke(KeyEvent.VK_ENTER, 0), "spaced");
//        am.put("enter", new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                setMsgToSend(msgToSend.getText());
//                msgToSend.setText("");
//            }
//        });
        frame.getRootPane().setDefaultButton(sendButton);

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

    }

    public void setMsgToSend(String msg) {
        this.msgSend = msg;
        System.out.println("Msg depuis l'interface : " + msg);
        client.sendMessage(msg); //Envoie pour serveur
    }

    public String getMsgToSend() {
        return this.msgSend;
    }

    public JTextArea getMsgs() {
        return this.msgs;
    }

    public JList getListCo() {
        return this.listCo;
    }

    public DefaultListModel getModel() {
        return this.model;
    }
    
    public void setModel(DefaultListModel m){
        this.model = m ;
    }

    public JLabel getNomClient() {
        return this.nomClient;
    }

}
