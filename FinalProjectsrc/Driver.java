import lejos.hardware.Sound;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Calibrate;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.filter.AbstractCalibrationFilter;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import java.io.File;
import java.util.Random;

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

	public static void main(String[] args) {
		MovePilot pilot = getPilot(MotorPort.A, MotorPort.B, WHEEL_DIAMETER, AXLE_LENGTH);
		
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);
		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S2);
		EV3TouchSensor ts = new EV3TouchSensor(SensorPort.S3);
		
		/*
		Create instace of calibrate class,
		then create to calibration classes to calibrate sensors,
		Ultrasonic dosent need to be calibrated.
		*/
		StartUp startUp = new StartUp(ts,cs);
		startUp.start();
	
		Trundle trundle = new Trundle(pilot);
		BackUp backUp = new BackUp(pilot, ts, null); // needs a sound (.wav) file (currently set to null)
		EmergencyStop emergencyStop = new EmergencyStop(pilot);
		Light light = new Light(pilot, pilot.getLinearSpeed(), cs, new File("Warning-sound.wav")); // needs a sound (.wav) file (currently set to null)
		SlowDown slowdown = new SlowDown(pilot, us);
		BatteryLevel bLevel = new BatteryLevel(null); // needs a sound (.wav) file (currently set to null)
		BatteryLow bLow = new BatteryLow();
		/*
		Check if Emergencey stop is hit first.
		If not cintinue trundleing.
		If Ultrasonice notices object infront, slow down.
		If touchsensor hits something, trigger backup( back up,
								turn around,
								play a sound,
								then move in a random direction).
		If robot enters dark area, trigger light behaviour, stopping robot and plays a distress tone.
		*/
		Arbitrator ab = new Arbitrator(new Behavior[] {bLow, emergencyStop, bLevel, trundle, slowdown, backUp, light});		
		ab.go(); // This never returns! It is a blocking call.
	}
}
