import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class SocketTest {

	static String dstAddress = null;
	static int dstPort = 8080;
	static String input = null;
	static String output = null;
	static int serverPort = 60300;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		ServerSocket listener = new ServerSocket(serverPort);
		DataInputStream dataInputStream = null;
		DataOutputStream dataOutputStream = null;
		int i = 0;
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file;
		JFrame frame = new JFrame("Socket Test");
		JPanel panel = new JPanel();
		JMenuItem open = new JMenuItem("Open");
		JMenuItem exit = new JMenuItem("Exit");
		JLabel label = new JLabel();
		JLabel label1 = new JLabel();
		
		
//		file = new JMenu("File");
//		menuBar.add(file);
//		file.add(open);
//		file.add(exit);
//		frame.add(panel);
//		panel.add(label);
//		panel.add(label1);
//		frame.setJMenuBar(menuBar);
//		frame.setVisible(true);
//		frame.setSize(640, 480);
//		panel.setLayout(null);
//		
//		label.setText("one");
//		label.setBounds(20,-20,100,100);	
//		label1.setText("Current Data");
//		label1.setBounds(20,0,100,100);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
				
		Swingmain s = new Swingmain();
		s.file = new JMenu("File");
		s.frame = new JFrame("Socket Test");
		s.menuBar.add(s.file);
		s.file.add(s.open);
		s.file.add(s.exit);
		s.frame.add(s.panel);
		s.panel.add(s.label);
		s.panel.add(s.label1);
		s.panel.add(s.label2);
		s.panel.add(s.label3);
		s.panel.setLayout(null);
		s.frame.setJMenuBar(s.menuBar);
		s.frame.setVisible(true);
		s.frame.setSize(320, 240);
		s.label.setText("Data RX:");
		s.label.setBounds(20,-20,100,100);
		s.label1.setText("Null");
		s.label1.setBounds(80,-20,300,100);
		s.label2.setText("Data TX:");
		s.label2.setBounds(20,0,300,100);
		s.label3.setText("Null");
		s.label3.setBounds(80,0,300,100);
		s.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			while(true) {
				Socket socket = listener.accept();
				try {
					dataInputStream = new DataInputStream(socket.getInputStream());
					dataOutputStream = new DataOutputStream(socket.getOutputStream());
					input = dataInputStream.readUTF();
					System.out.println(input);
					s.label1.setText(input);
					
					if(input.equals("Door Opened")) {
						output = "Door Opened";
						System.out.println("inside if");
					}
					
					if(input.equals("sendMeAllParameters")) {
						output = String.valueOf(i);
						s.label3.setText(output);
						i++;
					}
					
					if (output != null) {
						dataOutputStream.writeUTF(output);
					}
					
					output = null;
					
				}
				catch (IOException e){
					System.out.println(e+"what");
				}
				finally {
					socket.close();
				}
			}
		}
		finally {
			listener.close();
		}
		
	}
	
}
