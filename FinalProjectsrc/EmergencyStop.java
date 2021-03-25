import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class EmergencyStop implements Behavior {  
  private MovePilot pilot;
  EmergencyStop(MovePilot p){
    this.pilot = p;
  }
  @Override
  public boolean takeControl(){
    return(Button.ENTER.isDown());
  }
  @Override
  public void action(){
    pilot.stop();
    Sound.beepSequence();
  }
  @Override
  public void suppress(){}
}
