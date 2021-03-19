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
		MovePilot pilot = getPilot(MotorPort.A, MotorPort.B, 60, 29);
		
		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S1);
		SamlpeProvider cs_sp = cs.getRedMode();
		EV3TouchSensor ts = new EV3TouchSensor(SensorPort.S2);
		SampleProvider ts_sp = ts.getTouchMode();
		
		Calibrate cal_cs = new AbstractCalibrationFilter(cs_sp);
		Calibrate cal_ts = new AbstractCalibrationFilter(ts_sp);
		
		cal_cs.startCalibration();
		//sleep
		cal_cs.stopCalibration();
		//min and max = cal_cs.max/min
		//repeat for ts
		
		pilot.setLinearSpeed(200);
		
		Trundle trundle = new Trundle(pilot);
		BackUp backUp = new BackUp(pilot, null);
		BatteryLevel batteryLevel = new BatteryLevel();
		EmergencyStop emergencyStop = new EmergencyStop();
		Light light = new Light(pilot, pilot.getLinearSpeed(), cs);
		
		Arbitrator ab = new Arbitrator(new Behavior[] {trundle, backUp, batteryLevel, emergencyStop, light});
		ab.go(); // This never returns! It is a blocking call.
	}
}

