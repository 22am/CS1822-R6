//Damien's code

//import java.io.File;
//
//import lejos.hardware.Sound;
//
//public class SampleSound {
//
//	public static void main(String[] args) {
//		Sound.playSample(new File("Warning-sound.wav"), 20);
//
//	}
//
//}
  


//Dave's code

import java.io.File;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class SampleSound extends Thread {
	public static void main(String[] args) {
		int count = 1;
		while (true) {
			LCD.drawInt(count++, 0, 6);
			playTune(); 
		}
	}
	private static void playTune() {
		int time = Sound.playSample(new File("Warning-sound-effect.wav"), 20);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
