import java.io.File;
import lejos.hardware.Sound;

public class SampleSound {
	private static File file;
	final static int soundVolume = 20;
	public SampleSound(File file) {
		SampleSound.file = file;
	}

	public static void playTune() {
		int time = Sound.playSample(file, soundVolume);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
