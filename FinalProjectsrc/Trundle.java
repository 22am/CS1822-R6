import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Trundle implements Behavior {
	private MovePilot pilot;
//	private boolean suppressed = false;
	Trundle(MovePilot p) {
		this.pilot  = p;
	}
	public boolean takeControl() {
		return !this.pilot.isMoving();
	}
	public void action() {
//		suppressed=false;
		pilot.forward();
	}
	public void suppress() {/*suppressed = true;*/}

}
