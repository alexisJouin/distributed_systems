package ActionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class FieldListener implements ActionListener {

	private JTextField msg;

	public FieldListener(JTextField msg) {
		this.msg = msg;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(this.msg.getText());
	}

}
