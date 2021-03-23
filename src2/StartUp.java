public class StartUp{
  Private SmapleProvider cs;
  Private SampleProvider ts;
  public StartUp(EV3TouchSensor ts, EV3ColorSensor cs){
    this.cs = cs.getRedMode();
    this.ts = ts.getTouchMode();
  }
  
  public void start(){
    Calibrate cal_cs = new AbstractCalibrationFilter(this.cs) {};
    cal_cs.startCalibration();
		Thread.sleep(2000);
		cal_cs.stopCalibration();
    
		Calibrate cal_ts = new AbstractCalibrationFilter(this.ts) {};
    cal_ts.startCalibration();
		Thread.sleep(2000);
		cal_ts.stopCalibration();
    
  }
  
}
