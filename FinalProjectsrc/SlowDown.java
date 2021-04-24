import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
//import lejos.utility.Delay;
//import java.util.Random;

public class SlowDown implements Behavior{
	private MovePilot mp;
//    private EV3UltrasonicSensor us;
    private SampleProvider sp;
    private float[] sample = new float[1];
  
    public SlowDown(MovePilot mp, EV3UltrasonicSensor us) {
    	this.mp = mp;
    	this.sp = us.getDistanceMode();
    }
    public boolean takeControl(){
    	sp.fetchSample(this.sample,0);
    	return sample[0] < 0.15f && this.mp.isMoving() ; 
    }
    public void action(){
    	this.mp.setLinearSpeed(this.mp.getLinearSpeed() *1/2);
    }

	public void suppress() {}
}
