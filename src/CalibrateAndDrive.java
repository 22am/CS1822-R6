
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class CalibrateAndDrive {
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
		MovePilot pilot = getPilot(MotorPort.A, MotorPort.B, 60, 29);
		pilot.setLinearSpeed(200);
		
		
		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S1);
		Calibrate cal = AbstractCalibrationFiler(new SampleProvider = cs.getRedMode());
		cal.startCalibration();
		cal.stopCalibration();
		//max/min for colour values is cal.max/min
		EV3TouchSensor ts = new EV3TouchSensor(SensorPort.S2);
		//repeat lines 31 to 33 for any sensor that need to be calibrated
		
		
		Trundle trundle = new Trundle(pilot);
		BackUp backUp = new BackUp(pilot, null);
		BatteryLevel batteryLevel = new BatteryLevel();
		EmergencyStop emergencyStop = new EmergencyStop();
		Light light = new Light(pilot, pilot.getLinearSpeed(), cs);
		//BluetoothConnection bluetoothConnection = new BluetoothConnection(); not needed...

		Arbitrator ab = new Arbitrator(new Behavior[] {trundle, backUp, batteryLevel, emergencyStop, light});
		ab.go(); // This never returns! It is a blocking call.
	}
}
		
		

	
	



