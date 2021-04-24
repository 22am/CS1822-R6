import java.awt.Point;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.geometry.Line;
import lejos.robotics.geometry.Rectangle;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.DestinationUnreachableException;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.PathFinder;
import lejos.robotics.pathfinding.ShortestPathFinder;
import lejos.utility.Delay;

public class FollowThePath {
	final static float WHEEL_DIAMETER = 56; // The diameter (mm) of the wheels
	final static float AXLE_LENGTH = 28; // The distance (mm) your two driven wheels
	final static float ANGULAR_SPEED = 100; // How fast around corners (degrees/sec)
	final static float LINEAR_SPEED = 70; // How fast in a straight line (mm/sec)
	final static Point startPoint = new Point(0,0);
	final static Point finalPoint = new Point(250,70);

	public static void main(String[] args) throws DestinationUnreachableException {
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
		
		// Set speeds
		pilot.setLinearSpeed(LINEAR_SPEED);
		pilot.setAngularSpeed(ANGULAR_SPEED);
		
		// Create pose provider
		PoseProvider poseProvider = new OdometryPoseProvider(pilot);
        
		// Print pose
		LCD.drawString(poseProvider.getPose().toString(), 0, 1);
		Delay.msDelay(1000);
		
		// Create navigator
		Navigator navigator = new Navigator(pilot, poseProvider);
		
		
		
		//Creating obstacle lines
		Line [] lines = new Line[8];
		
			//obstacle 1
        lines[0] = new Line(40, 80, 40, 200);
        lines[1] = new Line(40, 200, 130, 200);
        lines[2] = new Line(130, 200, 130, 80);
        lines[3] = new Line(130, 80, 40, 80);
        
        	//obstacle 2
        lines[4] = new Line(130, 40 ,130, 80);
        lines[5] = new Line(130, 80, 210, 80);
        lines[6] = new Line(210, 80, 210, 40);
        lines[7] = new Line(210, 40, 130, 40);
        
        
        //size of the bounding area
        Rectangle boundaries =new Rectangle( 0.0f , 0.0f, 500.0f , 500.0f );
        
        //Creates LineMap with obstacles and the boundaries
        LineMap Map = new LineMap(lines , boundaries);
        
        //Creates a path finder with LineMap
        PathFinder shortestPath = new ShortestPathFinder(Map);
        
        //Finds the best route
        Path path = shortestPath.findRoute (poseProvider.getPose(),
        			new Waypoint ( 400 , 0 ));
        
        
        //The navigation follows the best route
        navigator.followPath(path);
		navigator.waitForStop();
        
		
		
		mL.close();
		mR.close();
	}

}
