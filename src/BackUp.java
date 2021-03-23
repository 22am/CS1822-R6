import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import java.util.Random;

public class BackUp implements Behavior{
	private EV3TouchSensor sensor;
	private MovePilot pilot;
	private SampleProvider sp;
	private float[] sample = new float[1];
	
	BackUp(MovePilot pilot,EV3TouchSensor sensor){
		this.sensor = sensor;
		this.sp = sensor.getTouchMode();
		this.pilot = pilot;
	}
	
	public boolean takeControl() {
		sp.fetchSample(sample,0);
		return sample[0] < 0.5f;
	}


	public void action() {
		Random rand = new Random();
		this.pilot.backward();
		Delay.msDelay(1500); // 1.5s
		
		if (rand.nextBoolean()) {
			this.pilot.rotate(-510);
		}
		else {
			this.pilot.rotate(510);
		}
	}


	public void suppress() {}

}

