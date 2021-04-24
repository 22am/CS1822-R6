


import lejos.robotics.Calibrate;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3ColorSensor;

public class calibrate implements Behavior {
	private int r = 0;
	private EV3TouchSensor touch;
	private EV3ColorSensor light;
	private final static int DELAY_20_SECONDS = 20000;
	
	
	public calibrate(EV3TouchSensor TouchSensor, EV3ColorSensor LightSensor) {
		this.touch = TouchSensor;
		this.light = LightSensor;
	}
	

	
	
	public boolean takeControl() {
		if (r == 0) {
			r = 1;
			return true; // unless calibration ha already run
		} else {
			return false;
		}
	}
	
	public void suppress() {
		// set a field to true 
	}
	
	public void action() {
		
		//Calibrate touch
		((Calibrate) this.touch).startCalibration();
		Delay.msDelay(DELAY_20_SECONDS);
		((Calibrate) this.touch).stopCalibration();
		
		//calibrate light
		((Calibrate) this.light).startCalibration();
		Delay.msDelay(DELAY_20_SECONDS);
		((Calibrate) this.light).stopCalibration();
	}
	
}
