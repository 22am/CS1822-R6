import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Trundle implements Behavior {
	private MovePilot pilot;
	private final static int TenMeters = 10000;
	Trundle(MovePilot p) {
		this.pilot  = p;
	}
	public boolean takeControl() {
		return true;
	}
	public void action() {
		pilot.travel(TenMeters);
	}
	public void suppress() {}

}
