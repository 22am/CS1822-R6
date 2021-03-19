import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;


public class Light implements Behavior {
	private float maxSpeed = 720;
	private MovePilot pilot;
	//private double LINEAR_SPEED;
	private EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S1);
	private SampleProvider sp = cs.getRedMode();
	float[] colour = new float[1];
	float[] colour2 = new float[1];
	private EV3ColorSensor cs2;
	private SampleProvider sp2 = cs2.getRedMode();

	
	
	
	Light(MovePilot p, double Speed, EV3ColorSensor cs)	{
		this.pilot = p;
		//this.LINEAR_SPEED = Speed;
		this.cs2 = cs;
		

	}
	
	public void setLinearSpeed(float LINEAR_SPEED)	{
		this.LINEAR_SPEED = LINEAR_SPEED;
	}
	
	public boolean takeControl() {
		//Calibrate calibrate = new Calibrate();
		this.sp.fetchSample(colour , 0);
		
		
		this.sp2.fetchSample(colour2, 0);

		

		return(colour[0] > colour2[0]);
	}
	
	public void action() {
		//setLinearSpeed(maxSpeed);
		
		}
	public void suppress()	{}
	}
	
	


