import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.Calibrate;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractCalibrationFilter;
import lejos.utility.Delay;

public class StartUp {
	private SampleProvider cs;
//	private SampleProvider ts;
	public StartUp(/*EV3TouchSensor ts,*/ EV3ColorSensor cs){
		this.cs = cs.getRedMode();
//		this.ts = ts.getTouchMode();
	}
	public void start() throws InterruptedException{
		LCD.drawString("Welcome. CS1822-R6", 0, 1);
		LCD.drawString("Members:", 0, 2);
		LCD.drawString("Damien, Amir", 0, 3);
		LCD.drawString("Mahdi, Liam", 0, 4);
		
		Thread.sleep(3000);
		Calibrate cal_cs = new AbstractCalibrationFilter(this.cs) {};
		cal_cs.startCalibration();
	    Thread.sleep(2000);
	    cal_cs.stopCalibration();
	    
/*	    Calibrate cal_ts = new AbstractCalibrationFilter(this.ts) {};
	    cal_ts.startCalibration();
	    Thread.sleep(2000);
	    cal_ts.stopCalibration();
*/	  
	    Sound.beepSequenceUp();
		
		
		
	}
  
}
