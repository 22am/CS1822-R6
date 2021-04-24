
import lejos.hardware.Battery;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;

public class BatteryLevel implements Behavior {
	final static int CURRENT_BATTERY_LEVEL = Battery.getVoltageMilliVolt();
	final static int SAFE_BATTERY_LEVEL = 1000; // In milliVolt
	@SuppressWarnings("unused")
	private boolean suppressed = false; 
	
	@Override
	public boolean takeControl() {
		if (CURRENT_BATTERY_LEVEL <= SAFE_BATTERY_LEVEL)
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
		
		// Print LOW BATTERY WARNING on the screen and beeps
		LCD.clear();
		LCD.drawString("BATTER LEVEL IS LOW !", 0, 2);
		
		Sound.beep();
		
	}


}
