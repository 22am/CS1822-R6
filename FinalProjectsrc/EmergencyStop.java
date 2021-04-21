import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

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
    
    Sound.beepSequence();
    Delay.msDelay(500);
    System.exit(1);
  }
  @Override
  public void suppress(){}
}
