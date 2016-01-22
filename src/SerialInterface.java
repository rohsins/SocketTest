import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialInterface implements SerialPortEventListener {

	static SerialPort serialPort;
	String[] temp;
	@Override
	public void serialEvent(SerialPortEvent arg0) {
		if (arg0.isRXCHAR()) {
			if(arg0.getEventValue() == 10) {
				try {
					byte buffer[] = serialPort.readBytes(10);
					temp = new String(buffer).split("\n");
				} catch (SerialPortException e) {
					System.out.println(e);
				}
			}
		} else if (arg0.isCTS()) {
			if(arg0.getEventValue() == 1) {
				System.out.println("CTS_ON");
			} else {
				System.out.println("CTS_OFF");
			}
		} else if (arg0.isDSR()) {
			if(arg0.getEventValue() == 1) {
				System.out.println("DSR_ON");
			} else {
				System.out.println("DSR_OFF");
			}
		}
	}
	
	public String getSerialValue() {
		return temp[1];
	}
	
}
