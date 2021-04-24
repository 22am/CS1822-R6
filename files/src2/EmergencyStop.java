import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;

public class EmergencyStop implements Behavior {
	EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);
	SampleProvider sp = touch.getTouchMode();
	
	// returns true if the button (ESCAPE) is pressed or the touch sensor is triggered
	public boolean takeControl() {
		if (Button.ESCAPE.isDown() || sp.equals(touch)) {
			return true;
		} else {
			return false;
		}
	}
	public void suppress() {
		// set a field to true 
	}
	
	@Override
	public void action() {
		System.exit(1);
	}
	
}
