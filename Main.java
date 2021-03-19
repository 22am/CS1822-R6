
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
//import lejos.hardware.Button;
import lejos.hardware.Sound;
//import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;


public class Main {
	final static float WHEEL_DIAMETER = 56; // The diameter (mm) of the wheels
	final static float AXLE_LENGTH = 28; // The distance (mm) your two driven wheels
	final static float ANGULAR_SPEED = 100; // How fast around corners (degrees/sec)
	final static float LINEAR_SPEED = 70; // How fast in a straight line (mm/sec)
	
	public static void main(String[] args) throws Exception {
		float[] samples = new float[1]; // Updated with the distances.
		// Create Navigator (nav), Path (route) as usual.
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A);
		// Create a ”Wheel” with Diameter 56mm and offset 22mm left of centre.
		Wheel wLeft = WheeledChassis.modelWheel(mL, WHEEL_DIAMETER).offset(-AXLE_LENGTH / 2);
		
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.D);
		// Create a ”Wheel” with Diameter 56mm and offset 22mm right of centre.
		Wheel wRight = WheeledChassis.modelWheel(mR, WHEEL_DIAMETER).offset(AXLE_LENGTH / 2);
		
		// Create a ”Chassis” with two wheels on it.
		Chassis chassis = new WheeledChassis((new Wheel[] {wRight, wLeft}),
		WheeledChassis.TYPE_DIFFERENTIAL);
		
		// Finally create a pilot which can drive using this chassis.
		MovePilot pilot = new MovePilot(chassis);
		
		//FIXME

		//Wheel[] wheels = new Wheel[] {wRight, wLeft};
		
		PoseProvider poseP = new OdometryPoseProvider(pilot);
		Navigator nav = new Navigator(pilot, poseP);
		Path route = new Path();
		route.add(new Waypoint(100,0)); route.add(new Waypoint(100,100));
		route.add(new Waypoint(0,100)); route.add(new Waypoint(0,0));
		nav.followPath(route); // followPath returns immediately, so
		nav.waitForStop(); // we wait for the Navigator to stop!
		
		Thread t = new Watcher(nav, samples);
		t.start();
		nav.followPath(route);
		nav.waitForStop(); // The read ahead paradigm again
		while (!nav.pathCompleted()) {
			while (samples[0] < 0.25f) { // Start again when obstacle > 25cm
				Sound.beep();
				Delay.msDelay(500);
			}
			nav.followPath();
			nav.waitForStop();
		}
	}
}
class Watcher extends Thread {
	private Navigator nav;
	private float[] dist;
	
	public Watcher(Navigator _nav, float[] _s) {
		nav = _nav;
		dist = _s;
		}
	
	public void run() {
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);
		SampleProvider sp = us.getDistanceMode();
		while (true) {
			sp.fetchSample(dist, 0); // Fetch the distance into the shared samples array every time around this loop.
			if (dist[0] < 0.10f && nav.isMoving()) {
				nav.stop();
			} // Obstacle!
		us.close();
		}
	}

}
