import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import java.util.Random;

public class BackUp implements Behavior{
	private EV3UltrasonicSensor sensor;
	private MovePilot pilot;
	private SampleProvider sp;
	private float[] sample = new float[1];
	
	BackUp(MovePilot pilot,EV3UltrasonicSensor sensor){
		this.sensor = sensor;
		this.sp = sensor.getDistanceMode();
		this.pilot = pilot;
	}
	
	public boolean takeControl() {
		sp.fetchSample(sample,0);
		return sample[0] < 2.00f;
	}
	
	public void action() {
		Random rand = new Random();
		this.pilot.backward();
		Delay.msDelay(400);
		
		if (rand.nextBoolean()) {
			this.pilot.rotate(-510);
		}
		else {
			this.pilot.rotate(510);
		}
	}
	
	public void suppress() {}

}

