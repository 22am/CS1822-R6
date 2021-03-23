import java.io.File;
import lejos.hardware.Sound;

public class SampleSound {
	private static File file;
	public SampleSound(File f) {
		SampleSound.file = f;
	}

	@SuppressWarnings("unused")
	private static void playTune() {
		int time = Sound.playSample(file, 20);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
