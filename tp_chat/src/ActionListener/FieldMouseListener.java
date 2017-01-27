package ActionListener;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FieldMouseListener implements MouseListener {

	private boolean activate = false;
	private JTextField msg;
	
	public FieldMouseListener(JTextField msgToSend) {
		this.msg = msgToSend;
	}
	
	public void setActivate(boolean activate){
        this.activate = activate;
    }

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (activate == false) {
			msg.setText("");
		}
		activate = true;
		msg.setForeground(Color.black);

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
