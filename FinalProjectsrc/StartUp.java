import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.Calibrate;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractCalibrationFilter;

public class StartUp {
	private SampleProvider cs;
	private SampleProvider ts;
	private final static int DelayTwoSec = 2000;
	public StartUp(EV3Ultrasonicsensor us, EV3ColorSensor cs){
		this.ts = us; //FIXME
		this.cs = cs.getRedMode();
	}
	public void start() throws InterruptedException{
		LCD.drawString("Welcome. CS1822-R6.\nMembers: Damien, Amir\nMahdi, Liam", 0, 1);
		Thread.sleep(DelayTwoSec);
		Calibrate cal_cs = new AbstractCalibrationFilter(this.cs) {};
		cal_cs.startCalibration();
	    Thread.sleep(DelayTwoSec);
	    cal_cs.stopCalibration();
	    
	    Calibrate cal_ts = new AbstractCalibrationFilter(this.ts) {};
	    cal_ts.startCalibration();
	    Thread.sleep(DelayTwoSec);
	    cal_ts.stopCalibration();
	  
	    Sound.beepSequenceUp();
	}
  
}
