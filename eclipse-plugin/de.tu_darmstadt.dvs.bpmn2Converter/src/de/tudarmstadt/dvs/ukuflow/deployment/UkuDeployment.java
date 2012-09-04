package de.tudarmstadt.dvs.ukuflow.deployment;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * use for deploying a workflow
 * 
 * @author Hien Quoc Dang
 * 
 */
public class UkuDeployment {
	public static final int TIME_OUT = 30000;
	private String file;
	private String port;
	private int timeout = TIME_OUT;
	
	public UkuDeployment(String fileName, String port, int timeout) {
		this.file = fileName;
		this.port = port;
		this.timeout = timeout;
	}

	public UkuDeployment(String fileName, String port) {
		this.file = fileName;
		this.port = port;
	}
	public void deploy(){
		
	}
	
	private void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(),
					2000);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

				InputStream in = serialPort.getInputStream();
				OutputStream out = serialPort.getOutputStream();

				(new Thread(new SerialReader(in))).start();
				(new Thread(new SerialWriter(out))).start();

			} else {
				System.out
						.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	/** */
	public static class SerialReader implements Runnable {
		InputStream in;

		public SerialReader(InputStream in) {
			this.in = in;
		}

		public void run() {
			byte[] buffer = new byte[1024];
			int len = -1;
			try {
				while ((len = this.in.read(buffer)) > -1) {
					System.out.print(new String(buffer, 0, len));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** */
	public static class SerialWriter implements Runnable {
		OutputStream out;
		InputStream in;
		String s = "MCAxIDIgMyA0";

		public SerialWriter(OutputStream out) {
			this.out = out;
			try {
				in = new FileInputStream(
						new File(
								"D:\\Ukuflow\\ukuflow\\eclipse-plugin\\de.tu_darmstadt.dvs.bpmn2Converter\\base64.txt"));

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void run() {

			try {
				Thread.sleep(3000);
				int c = 0;
				int last1 = 0, last2 = 0;
				while ((c = in.read()) > -1) {
					this.out.write(c);
					last2 = last1;
					last1 = c;

				}
				{
					out.flush();
					System.out.println("flushed");
				}
				Thread.sleep(1000);
				if (last2 != 13 & last1 != 10) {
					System.out.println("force end 13 10");
					this.out.write(13);
					this.out.write(10);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
