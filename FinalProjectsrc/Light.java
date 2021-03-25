import java.io.File;

import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;


public class Light implements Behavior {
	private float maxSpeed = 720;
	private MovePilot pilot;
	private SampleProvider sp;
	private float[] colour;
  	private File file;
  	//private double LINEAR_SPEED;
	//float[] colour2 = new float[1];
	private EV3ColorSensor cs2;
	private SampleProvider sp2 = cs2.getRedMode();
	
	private final static float dark = 0.8f;
	
	Light(MovePilot p, float[] min, EV3ColorSensor cs, File file)	{
		this.pilot = p;
    	this.sp = cs.getRedMode();
    	this.colour = min;
    	this.file = file; 
    	//this.LINEAR_SPEED = Speed;
	}
	
    //FIXME
  
  	public boolean takeControl() {
		//is.sp2.fetchSample(colour2, 0);
		return(this.colour[0] > dark /*this.sp.fetchSample(colour , 1)*/);
	}
  

  	public void action(){
		this.pilot.setLinearSpeed(0);
    	//Play a sample sound
		//SampleSound tune = new SampleSound(file);
		//tune.playTune();
		//Buzz instead of playing a sample sound
		Sound.buzz();
	}
	
  	public void suppress(){}
	
}

