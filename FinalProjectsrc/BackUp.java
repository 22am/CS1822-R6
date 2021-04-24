import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
//import java.io.File;
import java.util.Random;

public class BackUp implements Behavior{
//	private EV3TouchSensor sensor;
	EV3TouchSensor ts = new EV3TouchSensor(SensorPort.S3);
	
	private MovePilot pilot;
	private SampleProvider sp = ts.getTouchMode();
	private float[] sample = new float[1];
//	private File file; 
	BackUp(MovePilot mp/*,EV3TouchSensor sensor,File file*/){
//		this.sensor = sensor;
//		BackUp.sp = this.sensor.getTouchMode();
		pilot = mp;
//		this.file = file;
		
		
	}
	
/*	public static boolean TouchIsPressed() {
	  float[] sample = new float[1];
	  sp.fetchSample(sample, 0);
	  return sample[0] != 0.0f;
	}
*/	
	
	public boolean takeControl() {
		sp.fetchSample(sample, 0);
		if (sample[0] == 1) {
			return true;
		}
		return false;
	}


	public void action() {
	    Random rand = new Random(); //instance of random class
	    int upperbound = 16;
	      //generate random values from 0-24
	    int int_random = rand.nextInt(upperbound);
	    int rotateValue = (int_random+15)*10;
		
		
		/*SampleSound tune = new SampleSound(file);
		tune.playTune();*/
		Sound.twoBeeps();
		
//		Delay.msDelay(1500); // 1.5s
		
		pilot.travel(-250);
		
		if (rand.nextBoolean()) {
			pilot.rotate(-rotateValue);
			Sound.beep();
		}
		else {
			pilot.rotate(rotateValue);
			Sound.beep();
		}
	}


	public void suppress() {}

}
