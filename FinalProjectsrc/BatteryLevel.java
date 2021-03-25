
import java.io.File;

import lejos.hardware.Battery;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;

public class BatteryLevel implements Behavior {
	final static int CURRENT_BATTERY_LEVEL = Battery.getVoltageMilliVolt();
	final static int SAFE_BATTERY_LEVEL = 1000; // In milliVolt
	private File file;
	BatteryLevel(File file) {
		this.file = file;
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
		// Print LOW BATTERY WARNING on the screen and beeps
		LCD.clear();
		LCD.drawString("BATTER LEVEL IS LOW !", 0, 2);
		
		// Creates a SampleSound object with the .wav file passed into the behaviour
		//SampleSound tune = new SampleSound(file);
		//tune.playTune();
		
		//Beeps instead of playing a sample sound
		Sound.beep();
		
	}


}
