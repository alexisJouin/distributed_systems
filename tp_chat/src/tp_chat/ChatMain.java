package tp_chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ActionListener.FieldListener;
import ActionListener.FieldMouseListener;

public class ChatMain {

	private JFrame frame;

	private JPanel mainPanel;
	private JPanel northPanel;
	private JPanel southPanel;
	private JButton sendButton;
	private JTextArea msgs;
	private JTextArea listCo;
	private JTextField msgToSend;
	private JLabel titreMsg;

	/**
	 * Constructeur par défaut - Intteraction CLIENT / SERVEUR
	 */
	public ChatMain() {

	}

	/**
	 * Gestion de l'interface Création de l'interface
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
		listCo = new JTextArea("Liste des personnes connectés :");
		msgToSend = new JTextField("Entrez votre message ! ");
		sendButton = new JButton("Envoyer");
		titreMsg = new JLabel("Liste des Messages :");

		/**
		 * Style des composants
		 */
		Font font = new Font("Arial", Font.PLAIN, 20);
		msgs.setFont(font);
		
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

		// Assemblage des éléments
		// Nord
		northPanel.add(msgs, BorderLayout.CENTER);
		northPanel.add(listCo, BorderLayout.EAST);
		// Sud
		southPanel.add(msgToSend, BorderLayout.CENTER);
		southPanel.add(sendButton, BorderLayout.EAST);
		// Fenêtre globale
		mainPanel.add(northPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);

		frame.getContentPane().add(mainPanel);
		frame.setSize(640, 480);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		ChatMain chat = new ChatMain();
		chat.run();
		new ChatClient();
	}

}
