import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;


public class Light implements Behavior {
	private float maxSpeed = 720;
	private MovePilot pilot;
	private EV3ColorSensor cs;
	private SampleProvider sp;
	float[] colour;
  private SampleProvider sp2 = cs2.getRedMode();
  private File file;
  //private double LINEAR_SPEED;
	//float[] colour2 = new float[1];
	//private EV3ColorSensor cs2;
	
	Light(MovePilot p,float[] min, EV3ColorSensor cs, File file)	{
		this.pilot = p;
		this.cs = cs;
    this.sp = cs.getRedMode();
    this.color = min;
    this.file = file; 
    //this.LINEAR_SPEED = Speed;
	}
	
	//public void setLinearSpeed(float LINEAR_SPEED)	{
	//	this.LINEAR_SPEED = LINEAR_SPEED;
	//}
  
  
  public boolean takeControl() {
		//is.sp2.fetchSample(colour2, 0);
		return(this.color[0] > this.sp.fetchSample(color , 0));
	}
  
  public void playTune() {
    int time = Sound.playSample(this.file, 20);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
    }
  }
	

  public void action() {
		this.pilot.setLinearSpeed(0);
    play tune()
	}
	
  public void suppress()	{}
	
}

