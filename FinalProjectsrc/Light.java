import java.io.File;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;


public class Light implements Behavior {
	private float maxSpeed = 720;
	private MovePilot pilot;
	private SampleProvider sp;
	private float colour;
  	private File file;
  	//private double LINEAR_SPEED;
	//float[] colour2 = new float[1];
	private EV3ColorSensor cs2;
	private SampleProvider sp2 = cs2.getRedMode();
	private float[] level = new float[1];
	
	private final static float dark = 0.8f;
	
	Light(MovePilot p,/* Double min,*/ EV3ColorSensor cs, File file)	{
		this.pilot = p;
    	this.sp = cs.getRedMode();
//    	this.colour = min.floatValue();
    	this.file = file; 
//   	this.LINEAR_SPEED = Speed;
	}
	
	//public void setLinearSpeed(float LINEAR_SPEED)	{
	//	this.LINEAR_SPEED = LINEAR_SPEED;
	//}
  
  
  	public boolean takeControl() {
		sp.fetchSample(level, 0);
		return(level[0] > dark /*this.sp.fetchSample(colour , 1)*/);
	}
  

  	public void action(){
		
    	//Play a sample sound
//		SampleSound tune = new SampleSound(file);
//		tune.playTune();
		
		//Buzz instead of playing a sample sound
		Sound.buzz();
		
		LCD.clear();
		System.out.println("You have reached a dark area!");
		
		Delay.msDelay(1000);
		
		System. exit(1);
	}
	
  	public void suppress(){}
	
}

