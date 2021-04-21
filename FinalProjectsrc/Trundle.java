import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Trundle implements Behavior {
	private MovePilot pilot;
	Trundle(MovePilot p) {
		this.pilot  = p;
	}
	public boolean takeControl() {
		return true;
	}
	public void action() {
		pilot.travel(-10000);
	}
	public void suppress() {}

}
