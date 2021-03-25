import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

import java.io.File;
import java.util.Random;

public class BackUp implements Behavior{
	private EV3TouchSensor sensor;
	private MovePilot pilot;
	private SampleProvider sp;
	private float[] sample = new float[1];
	private File file;
	private final static int DelayForTwoSec = 2000;
	private final static int PilotBackwardDistance = 50;
	BackUp(MovePilot pilot,EV3TouchSensor sensor,File file){
		this.sensor = sensor;
		this.sp = this.sensor.getTouchMode();
		this.pilot = pilot;
	}
	
	
	public boolean takeControl() {
		sp.fetchSample(sample,0);
		return sample[0] < 0.5f;
	}


	public void action() {
		SampleSound tune = new SampleSound(file);
		tune.playTune();
		
		Random rand = new Random();
		this.pilot.backward(PilotBackwardDistance);
		
		Delay.msDelay(DelayForTwoSec); // 2s
		
		// Rotates in a random direction //FIXME
		if (rand.nextBoolean()) {
			this.pilot.rotate(-510 /*rand*/);
			Sound.beep();
		}
		else {
			this.pilot.rotate(510);
			Sound.beep();
		}
	}


	public void suppress() {}

}
