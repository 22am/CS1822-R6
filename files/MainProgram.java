import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.*;  
import java.net.*;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class MainProgram {
	private static String IPaddress = "192.168.44.1";
	public static int port = 1234;
	public static Socket connection = new Socket();
	public static DataInputStream dis;
	public static DataOutputStream dos;
	private static int MAX_READ = 30;
	private static BufferedInputStream in = null;
	private static OutputStream out = null;

	public static void main(String[] args) throws IOException{
		byte[] buffer = new byte[MAX_READ];

		LCD.drawString("Waiting", 0, 0);
		SocketAddress sa = new InetSocketAddress(IPaddress, port);
		try {
			connection.connect(sa, 1500);
		} catch (Exception ex) {
			LCD.drawString(ex.getMessage(), 0, 0);
			connection = null;
		}
		if (connection != null) {
			in = new BufferedInputStream(connection.getInputStream());
			out = connection.getOutputStream();
			LCD.drawString("Connected", 0, 0);
		}
		
		LCD.drawString("Waiting", 0, 1);
		while (!Button.ESCAPE.isDown()) {
			if (connection != null) {
				LCD.drawString("Chars read:", 0, 2);
				LCD.drawInt(in.available(), 12, 2);
				int read = in.read(buffer, 0, MAX_READ);
				LCD.drawString("---", 3, 3);
				for (int index = 0 ; index<read ; index++) {
					LCD.drawChar((char) buffer[index], index+4, 3);
				}
				LCD.drawString("---", read+4, 3);
				out.write("Reply:".getBytes(), 0, 6);
				out.write(buffer, 0, read);
			}
		}
	}
}
