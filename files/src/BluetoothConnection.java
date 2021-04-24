import lejos.robotics.subsumption.Behavior;
import java.io.InputStream;
import java.util.Scanner;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;

public class BluetoothConnection implements Behavior {
	String message = "";
	Scanner s;
	
	@SuppressWarnings("unused")
	private boolean suppressed = false;
	
	
	@Override
	public boolean takeControl() {
		// Access Bluetooth input stream
		BTConnector connector = new BTConnector();
	    NXTConnection conn = connector.waitForConnection(0, NXTConnection.RAW);
	    InputStream is = conn.openInputStream();

	    //Create a scanner to read from the input stream 
	    s = new Scanner(is).useDelimiter("\\A");
	    
	    //Check for data in the input stream
	    if (s.hasNext())
	    	return true;
	    
		return false;
	}

	@Override
	public void suppress() {
		suppressed = true;	
	}

	@Override
	public void action() {
		suppressed = false;
		
		//Add the data in input stream to message
		message = s.hasNext() ? s.next() : "";
		
		
		
	}


}
