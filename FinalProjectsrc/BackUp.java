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
	private File file;
	BackUp(MovePilot pilot,EV3TouchSensor sensor,File file){
		this.sensor = sensor;
		this.sp = sensor.getTouchMode();
		this.pilot = pilot;
	}
	
	public void playTune() {
    		int time = Sound.playSample(this.file, 20);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e){
			e.printStackTrace();
    		}
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
