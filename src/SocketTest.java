import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JMenu;

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
				
		Swingmain s = new Swingmain();
		s.file = new JMenu("File");
		s.frame = new JFrame("OK");
		s.menuBar.add(s.file);
		s.file.add(s.open);
		s.file.add(s.exit);
		s.frame.add(s.panel);
		s.panel.add(s.label);
		s.panel.add(s.label1);
		s.frame.setJMenuBar(s.menuBar);
		s.frame.setVisible(true);
		s.frame.setSize(640,480);
		s.label.setText("one");
		s.label1.setText("Current Data");
		s.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			while(true) {
				Socket socket = listener.accept();
				try {
					dataInputStream = new DataInputStream(socket.getInputStream());
					dataOutputStream = new DataOutputStream(socket.getOutputStream());
					input = dataInputStream.readUTF();
					System.out.println(input);
					s.label.setText(input);
					
					if(input.equals("Door Opened")) {
						output = "Door Opened";
						System.out.println("inside if");
					}
					
					if(input.equals("sendMeAllParameters")) {
						output = String.valueOf(i);
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
