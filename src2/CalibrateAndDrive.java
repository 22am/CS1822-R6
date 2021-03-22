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

public class CalibrateAndDrive {
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
		
		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S1);
		SampleProvider cs_sp = cs.getRedMode();
		EV3TouchSensor ts = new EV3TouchSensor(SensorPort.S2);
		SampleProvider ts_sp = ts.getTouchMode();
		
		Calibrate cal_cs = new AbstractCalibrationFilter(cs_sp);
		Calibrate cal_ts = new AbstractCalibrationFilter(ts_sp);
		
		cal_cs.startCalibration();
		//sleep
		((Thread) cal_cs).sleep(2000);
		cal_cs.stopCalibration();
		//min and max = cal_cs.max/min
		//repeat for ts
		
		pilot.setLinearSpeed(200);
		
		Trundle trundle = new Trundle(pilot);
		//BackUp backUp = new BackUp(pilot, null);
		//BatteryLevel batteryLevel = new BatteryLevel();
		EmergencyStop emergencyStop = new EmergencyStop();
		//Light light = new Light(pilot, pilot.getLinearSpeed(), cs);
		
		Arbitrator ab = new Arbitrator(new Behavior[] {trundle, emergencyStop});
		
		Thread watcher1= new DistanceWatcher(pilot);
		watcher1.setDaemon(true);
		watcher1.start();
		
		Thread watcher2= new TouchWatcher(pilot);
		watcher2.setDaemon(true);
		watcher2.start();
		
		Thread watcher3= new LightWatcher(pilot);
		watcher3.setDaemon(true);
		watcher3.start();
		
		ab.go(); // This never returns! It is a blocking call.
	}
}
public class DistanceWatcher extends Thread {
	private MovePilot mp;
	public DistanceWatcher(MovePilot _m) { mp = _m; }
	public void run() {
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);
		SampleProvider sp = us.getDistanceMode();
		float[] samples = new float[1];
		while (true) {
			sp.fetchSample(samples, 0);
			if (samples[0] > 0.2) {
				mp.setLinearSpeed(100);
			}
			//m.setSpeed(sample[0] > 0.5 ? 1 : 720);
			Thread.yield();
		}
	}
}
public class TouchWatcher extends Thread {
	private MovePilot mp;
	public TouchWatcher(MovePilot _m) { mp = _m; }
	public void run() {
		EV3TouchSensor ts = new EV3TouchSensor(SensorPort.S2);
		SampleProvider sp = ts.getTouchMode();
		float[] samples = new float[1];
		while (true) {
			sp.fetchSample(samples, 0);
			if (samples[0] > 0.5) {
				mp.stop();
				playTune("sound.wav");
			}
			mp.travel(-200);
			//Random rand = new Random();
			mp.rotate(120);
			Thread.yield();
		}
	}
}
public class LightWatcher extends Thread {
	private MovePilot mp;
	public LightWatcher(MovePilot _m) { mp = _m; }
	public void run() {
		EV3TouchSensor ts = new EV3TouchSensor(SensorPort.S3);
		SampleProvider sp = ts.getTouchMode();
		float[] samples = new float[1];
		while (true) {
			sp.fetchSample(samples, 0);
			if (samples[0] > 0.8) {
				System.exit(1);
			}
			Thread.yield();
		}
	}
}
public class playTunee extends Thread {
	public static void playTune(File file) {
		int time = Sound.playSample(file, 20);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
