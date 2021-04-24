
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Driver {
	final static int WHEEL_DIAMETER = 56; // The diameter (mm) of the wheels
	final static int AXLE_LENGTH = 28; // The distance (mm) your two driven wheels
	//Creates a chassis for bot
	private static MovePilot getPilot(lejos.hardware.port.Port a, lejos.hardware.port.Port b, int diam, int offset) {
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(a);
		Wheel wL = WheeledChassis.modelWheel(mL, diam).offset(-1 * offset);
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(b);
		Wheel wR = WheeledChassis.modelWheel(mR, diam).offset(offset);
		Wheel[] wheels = new Wheel[] {wR, wL};
		Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
		return new MovePilot(chassis);
	}

	public static void main(String[] args) throws InterruptedException {
		MovePilot pilot = getPilot(MotorPort.A, MotorPort.B, WHEEL_DIAMETER, AXLE_LENGTH);
		pilot.setLinearSpeed(180);
		
//		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S4);
		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S2);
//		EV3TouchSensor ts = new EV3TouchSensor(SensorPort.S3);
		
		/*
		Create instance of calibrate class,
		then create to calibration classes to calibrate sensors,
		Ultrasonic doesn't need to be calibrated.
		*/
		StartUp calibrate = new StartUp(/*ts,*/cs);
		calibrate.start();
	
		Trundle trundle = new Trundle(pilot);
		BackUp backUp = new BackUp(pilot/*, ts, new File("Warning-sound.wav")*/);
		EmergencyStop emergencyStop = new EmergencyStop();
		Light light = new Light(pilot,/* pilot.getLinearSpeed(),*/ cs/*, new File("Warning-sound.wav")*/);
//		SlowDown slowdown = new SlowDown(pilot, us);
//		BatteryLevel bLevel = new BatteryLevel(/*new File("Warning-sound.wav")*/); 
//		BatteryLow bLow = new BatteryLow();
		/*
		Check if Emergency stop is hit first.
		If not continue trundling.
		If Ultrasonic notices object infront, slow down.
		If touchsensor hits something, trigger backup( back up,
								turn around,
								play a sound,
								then move in a random direction).
		If robot enters dark area, trigger light behaviour, stopping robot and plays a distress tone.
		*/
		
		Behavior [] bArray = { trundle, backUp, light, emergencyStop /* slowdown, bLevel, bLow, */};
		Arbitrator ab = new Arbitrator(bArray);
		
		//Arbitrator ab = new Arbitrator(new Behavior[] {bLow, emergencyStop, bLevel, trundle, slowdown, backUp, light});		
		ab.go(); // This never returns! It is a blocking call.
	}
}
