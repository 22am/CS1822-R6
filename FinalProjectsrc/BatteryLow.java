
import lejos.hardware.Battery;

import lejos.robotics.subsumption.Behavior;

public class BatteryLow implements Behavior {
	final static int CURRENT_BATTERY_LEVEL = Battery.getVoltageMilliVolt();
	final static int SAFE_BATTERY_LEVEL = 800; // In milliVolt

	BatteryLow() {
	}
	
	@Override
	public boolean takeControl() {
		if (CURRENT_BATTERY_LEVEL <= SAFE_BATTERY_LEVEL)
			return true;
		return false;
	}

	@Override
	public void suppress() {
	}

	@Override
	public void action() {
		// Shuts down the whole system
		System.exit(1);
		
	}


}
