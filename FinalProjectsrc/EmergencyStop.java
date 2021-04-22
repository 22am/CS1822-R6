import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class EmergencyStop implements Behavior {  
//  private MovePilot pilot;
//  private boolean suppressed = false;
  EmergencyStop(){}
  @Override
  public boolean takeControl(){
    return(Button.ENTER.isDown());
  }
  @Override
  public void action(){
//    suppressed= false;
    Sound.beepSequence();
    Sound.beepSequence();
//    Delay.msDelay(1000);
    System.exit(1);
  }
  @Override
  public void suppress(){/*suppressed = true;*/}
}
