import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import java.util.Random;

public class SlowDown implements Behavior{
	private MovePilot mp;
    private EV3UltrasonicSensor us;
    private SampleProvider sp;
    private float[] sample;
    private final static double safeDistance = 50f;
    private final static int slowDownBy = 50;
  
    public SlowDown(MovePilot mp, EV3UltrasonicSensor us) {
      this.mp = mp;
      this.sp = us.getDistanceMode();
    }
    public boolean takeControl(){
      return this.sp.fetchSample(this.sample,0) < safeDistance ; //FIXME
    }
    public void action(){
      this.mp.setLinearSpeed(this.mp.getLinearSpeed() - slowDownBy);
    }

	public void suppress() {}
}
