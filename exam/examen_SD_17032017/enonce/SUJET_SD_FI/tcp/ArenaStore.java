package tcp;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ArenaStore {

	private JFrame frm ;
	
	private Socket socket ;
	private PrintWriter writer ;
	private BufferedReader reader ;

	private JLabel available;
	private JTextField customer;
	private JTextField nbPlaces;

	private JButton bookButton;

	public ArenaStore() {
		try{
			socket = new Socket("localhost", ArenaServer.PORT) ;		
			writer = new PrintWriter(socket.getOutputStream()) ;
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
						
			buildUI() ;
			addListeners() ;
			
			frm.setSize(600, 250);
			frm.setVisible(true) ;
			
			startListening() ;
		}catch(Exception ex){
			ex.printStackTrace() ;
		}
	}
	
	private void startListening(){
		try {
			String text ;
			while( (text = reader.readLine()) != null){
				int nbPlaces = Integer.parseInt(text) ;
				available.setText(""+nbPlaces);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addListeners(){

		bookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				writer.println(customer.getText()); writer.flush();
				writer.println(nbPlaces.getText()); writer.flush();
				customer.setText("");
				nbPlaces.setText("");
				customer.grabFocus();
			}
			
		});
		
		frm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try { socket.close() ; } catch(Exception ex){ ex.printStackTrace() ; };
				System.exit(0) ;
			}
		}) ;
		
	}
	
	private void buildUI(){
		frm = new JFrame("Reservations") ;
		frm.getContentPane().setLayout(new BorderLayout()) ;
				
		JLabel title = new JLabel("Nombre de places restantes : ") ;
		title.setHorizontalAlignment(JLabel.HORIZONTAL) ;
		frm.add(title, BorderLayout.NORTH) ;
		available = new JLabel("unknown") ;
		available.setFont(new Font("Times", Font.PLAIN, 50));
		available.setHorizontalAlignment(JLabel.HORIZONTAL);
		frm .add(available, BorderLayout.CENTER) ;
		
		JPanel booking = new JPanel() ;
		booking.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		booking.add(new JLabel("Client : ")) ;
		customer = new JTextField() ;
		customer.setColumns(10);
		booking.add(customer) ;
		booking.add(new JLabel("Nb places : ")) ;
		nbPlaces = new JTextField() ;
		nbPlaces.setColumns(2);
		booking.add(nbPlaces) ;
		
		bookButton = new JButton("Reserver") ;
		booking.add(bookButton);
		
		frm.add(booking, BorderLayout.SOUTH) ;
		
		
	}
	
	public static void main(String[] args) {
		new ArenaStore() ;
	}
}
