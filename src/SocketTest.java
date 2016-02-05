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

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SocketTest {

	static String dstAddress = null;
	static int dstPort = 8080;
	static String input = null;
	static String output = null;
	static int serverPort = 60200;
	
	
	static SerialPort serialPort = new SerialPort("COM1");
	static Swingmain s = new Swingmain();
	static StringBuilder stringBuilder = new StringBuilder();
	
	static class SerialInterfaceO implements SerialPortEventListener {
		
		static byte[] buffer;
		String[] temp;
		int flag = 0;
		String tem;

		@Override
		public void serialEvent(SerialPortEvent event) {

			if (event.isRXCHAR()) {
//				if(event.getEventValue() == 1) {
					try {
						while (event.isRXCHAR()) {
							buffer = serialPort.readBytes(1);
							temp = new String(buffer).split("\n");
	//						for (int k = 0; k < tempi.length; k++ ) {
								stringBuilder.append(temp[0]);
	//						}
							if(temp[0].equals("n")) {
	//							stringBuilder.append("");
								flag++;
								if(flag == 15) {
									stringBuilder.delete(0, 1000);
									flag = 0;
									stringBuilder.append("\n"+"n");
								}
	//							stringBuilder = new StringBuilder("");
							}
							s.label5.setText(stringBuilder.toString());
						}					
					} catch (Exception e) {
						e.printStackTrace();
					}
//				}
			} else if (event.isCTS()) {
				if(event.getEventValue() == 1) {
					System.out.println("CTS_ON");
				} else {
					System.out.println("CTS_OFF");
				}
			} else if (event.isDSR()) {
				if(event.getEventValue() == 1) {
					System.out.println("DSR_ON");
				} else {
					System.out.println("DSR_OFF");
				}
			}
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException, IOException, SerialPortException {
		
		ServerSocket listener = new ServerSocket(serverPort);
		DataInputStream dataInputStream = null;
		DataOutputStream dataOutputStream = null;
//		int i = 0;
				
//		JMenuBar menuBar = new JMenuBar();
//		JMenu file;
//		JFrame frame = new JFrame("Socket Test");
//		JPanel panel = new JPanel();
//		JMenuItem open = new JMenuItem("Open");
//		JMenuItem exit = new JMenuItem("Exit");
//		JLabel label = new JLabel();
//		JLabel label1 = new JLabel();
				
//		Swingmain s = new Swingmain();
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
		s.panel.add(s.label4);
		s.panel.add(s.label5);
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
		s.label4.setText("Serial:");
		s.label4.setBounds(20,20,300,100);
		s.label5.setText("Null");
		s.label5.setBounds(80,20,700,100);
		s.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		try {
////			serialPort.openPort();
////			serialPort.setParams(9600, 8, 1, 0);
////			int mask = SerialPort.MASK_RXCHAR;
////			serialPort.setEventsMask(mask);
////			serialPort.addEventListener(new SerialInterfaceO());
////			String tempi[] = null;
////			int flag = 0;
////			while (true) {
////				buffer = serialPort.readBytes(1);
////				tempi = new String(buffer).split("\n");
//////				for (int k = 0; k < tempi.length; k++ ) {
////					stringBuilder1.append(tempi[0]);
//////				}
////				if(tempi[0].equals("\r")) {
////					stringBuilder1.append("  ");
////					flag++;
////					if(flag == 5) {
////						stringBuilder1.delete(0, 100);
////						flag = 0;
////					}
//////					stringBuilder1 = new StringBuilder("");
////				}
////				s.label5.setText(stringBuilde1r.toString());
//////				stringBuilder = new StringBuilder1("");
//////				s.label5.setText(stringBuilder1.toString());
//////				s.label5.setText(new String(tempi[0]));
////			}
////			serialPort.closePort();
//		} catch (SerialPortException e) {
//			System.out.println(e);
//		}
		
		
				
		try {
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0);
			int mask = SerialPort.MASK_RXCHAR;
			serialPort.setEventsMask(mask);
			serialPort.addEventListener(new SerialInterfaceO());
			String tempi[] = null;
			int flag = 0;
			
			while(true) {
				Socket socket = listener.accept();
				try {
					dataInputStream = new DataInputStream(socket.getInputStream());
					dataOutputStream = new DataOutputStream(socket.getOutputStream());
					input = dataInputStream.readUTF();
//					System.out.println(input);
					s.label1.setText(input);
					
					if(input.equals("Door Opened")) {
						output = "Door Opened";
						System.out.println("inside if");
					}
					
					if(input.equals("0xFD")) {
//						output = String.valueOf(i);
						output = "0xA0-@="+stringBuilder.toString();
						s.label3.setText(output);
//						i++;
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
