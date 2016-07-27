package src.main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private BtnAction listener;
	private final JButton btnSend;
	private Messages ms;

	private Client client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI frame = new ClientGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 828, 507);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		client = new Client();

		JPanel panel = new JPanel();
		panel.setBounds(12, 12, 802, 443);
		contentPane.add(panel);
		panel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(12, 24, 596, 35);
		panel.add(textField);
		textField.setColumns(10);

		textArea = new JTextArea();
		textArea.setBounds(22, 71, 756, 328);
		textArea.setEditable(Boolean.FALSE);
		panel.add(textArea);

		listener = new BtnAction();

		btnSend = new JButton("Send");
		btnSend.addActionListener(listener);

		btnSend.setBounds(661, 29, 117, 25);
		panel.add(btnSend);
		
		ms = new Messages();
		client.handleMessages(ms);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				client.sendMessage("disconnected");
				client.disconnect();
				
				textField.setEditable(Boolean.FALSE);
				btnSend.setEnabled(Boolean.FALSE);
				
			}
		});
		btnDisconnect.setBounds(344, 406, 117, 25);
		panel.add(btnDisconnect);
		
	}
	
	private class Messages implements IPacketReceived	{

		@Override
		public void received(String message) {
			textArea.append(message + "\n");
		}
	}
	
	private class BtnAction implements ActionListener	{

		@Override
		public void actionPerformed(ActionEvent e) {
			String send_message = textField.getText();
			
			client.sendMessage(send_message);
			textField.setText("");

			textArea.append("Me says: " + send_message + "\n");
		}

	}
}
