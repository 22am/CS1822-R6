import lejos.hardware.Button;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behaviour;

public class emergencyStop implements Behaviour {
  private MovePilot pilot;
  emergencyStop(MovePilot p){
  this.pilot = p;
  }
@Override
public boolean takeControl(){
  return(Button.ENTER.isDown());

}
@Override
public void action(){
pilot.stop();
}
@Override
public void suppress(){}

}
