package frc.robot.util;
import java.util.Map;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Lifter;
import frc.robot.Constants;

public class DashboardControl {
   //Data Entries; interacts with the dashboard entries
   @SuppressWarnings("unused")
   private class Writables { //Writable entries; the user changes these of the dashboard
      GenericEntry maxDRTN;
      GenericEntry maxFLCN;
      GenericEntry maxNEOS;
      GenericEntry maxCIMS;
      GenericEntry driveMode;
      GenericEntry autoCommand;
   }
   private class Readables { //Readable entries; the user sees data being outputting from these (not changeable)
      GenericEntry cannonAngle;
      GenericEntry liveSpeedDTRNOverall;
      GenericEntry liveSpeedDTRNDirectional;
      GenericEntry liveSpeedFLCN;
      GenericEntry liveSpeedNEOS;
      GenericEntry liveSpeedCIMS;
      GenericEntry servoAngle;
      GenericEntry robotOperationMode;
      GenericEntry matchTimerEntry;
   }

   //Subsystems
   private DriveTrain driveTrain;
   private Cannon cannon;
   private Lifter lifter;
   //Dashboard Utility
   private ShuffleboardTab mainTab;
   private UsbCamera camera;
   public Writables writableEntries; 
   public Readables readableEntries;

   //Match Timer Utility
   private Timer matchTimer;
   private double timerReadout = Constants.Other.teleopDuration;
   private boolean matchTimerActive = false;

   public DashboardControl(DriveTrain p_driveTrain, Cannon p_cannon, Lifter p_lifter){
      //Subsystem and timer definitions
      driveTrain = p_driveTrain;
      cannon = p_cannon;
      lifter = p_lifter;
      matchTimer = new Timer();
      matchTimer.schedule(new TimerTask() {
         @Override
         public void run() {
             if ((float) timerReadout <= (float) 0.1) timerReadout = 0;
             if (matchTimerActive && timerReadout > 0.2) timerReadout -= 0.1;
         }
      }, 0, 100);

      //Dashboard setup and definition
      mainTab = Shuffleboard.getTab("Main");
      Shuffleboard.selectTab("Main");
      writableEntries = new Writables();
      readableEntries = new Readables();
      camera = CameraServer.startAutomaticCapture();
      camera.setResolution(640, 640);
      camera.setFPS(20);

      //Defines writable entries
      writableEntries.maxDRTN = mainTab.addPersistent("MAX Drive Train Speed", Constants.DefaultSystemSpeeds.driveTrain)
         .withWidget(BuiltInWidgets.kNumberSlider)
         .withProperties(Map.of("min", 0, "max", 1))
         .withSize(2,1)
         .withPosition(11, 0)
         .getEntry();
      writableEntries.maxFLCN = mainTab.addPersistent("MAX Falcon Speed (Cannon)", Constants.DefaultSystemSpeeds.falcon)
         .withWidget(BuiltInWidgets.kNumberSlider)
         .withProperties(Map.of("min", 0, "max", 1))
         .withSize(2,1)
         .withPosition(11, 1)
         .getEntry();
      writableEntries.maxNEOS = mainTab.addPersistent("MAX Neo Speeds (Cannon)", Constants.DefaultSystemSpeeds.neos)
         .withWidget(BuiltInWidgets.kNumberSlider)
         .withProperties(Map.of("min", 0, "max", 1))
         .withSize(2,1)
         .withPosition(11, 2)
         .getEntry();
      writableEntries.maxCIMS = mainTab.addPersistent("MAX Cim Speeds (Lifter)", Constants.DefaultSystemSpeeds.cims)
         .withWidget(BuiltInWidgets.kNumberSlider)
         .withProperties(Map.of("min", 0, "max", 1))
         .withSize(2,1)
         .withPosition(11, 3)
         .getEntry();
      writableEntries.driveMode = mainTab.addPersistent("Drive Mode", Constants.UserControls.defaultDrivingStyle)
         .withWidget(BuiltInWidgets.kTextView)
         .withSize(2,1)
         .withPosition(11, 4)
         .getEntry();
      
      //Defines readable entries
      readableEntries.cannonAngle = mainTab.add("Cannon Angle",0)
         .withWidget(BuiltInWidgets.kDial)
         .withProperties(Map.of("min", 0, "max", 180))
         .withPosition(5,1)
         .withSize(2,2)
         .getEntry();
      readableEntries.liveSpeedDTRNOverall = mainTab.add("DT Speed Input (Overall)",0)
         .withWidget(BuiltInWidgets.kDial)
         .withProperties(Map.of("min", -1, "max", 1))
         .withPosition(0,0)
         .withSize(2,2)
         .getEntry();
      readableEntries.liveSpeedDTRNDirectional = mainTab.add("DT Speed Input (Directional)",0)
         .withWidget(BuiltInWidgets.kDial)
         .withProperties(Map.of("min", -1, "max", 1))
         .withPosition(0,2)
         .withSize(2,2)
         .getEntry();
      readableEntries.liveSpeedFLCN = mainTab.add("Falcon Speed Input (Cannon)",0)
         .withWidget(BuiltInWidgets.kNumberBar)
         .withProperties(Map.of("min", 0, "max", 1))
         .withPosition(5,0)
         .withSize(2,1)
         .getEntry();
      readableEntries.liveSpeedNEOS = mainTab.add("Neos Speed Input (Cannon)",0)
         .withWidget(BuiltInWidgets.kGraph)
         .withProperties(Map.of("min", 0, "max", 1))
         .withPosition(2,0)
         .withSize(3,3)
         .getEntry();
      readableEntries.liveSpeedCIMS = mainTab.add("Cim Speed Input (Lifter)",0)
         .withWidget(BuiltInWidgets.kNumberBar)
         .withProperties(Map.of("min", 0, "max", 1))
         .withPosition(2,3)
         .withSize(3,1)
         .getEntry();
      readableEntries.servoAngle = mainTab.add("Servo Angle (Cannon)",0)
         .withWidget(BuiltInWidgets.kGyro)
         .withPosition(5,3)
         .withSize(2,2)
         .getEntry();
      readableEntries.robotOperationMode = mainTab.add("Robot Operation Mode","Null")
         .withWidget(BuiltInWidgets.kTextView)
         .withPosition(9,4)
         .withSize(1,1)
         .getEntry();
      readableEntries.matchTimerEntry = mainTab.add("Match Timer","2:30")
         .withWidget(BuiltInWidgets.kTextView)
         .withPosition(10,4)
         .withSize(1,1)
         .getEntry();

      //* If it doesn't work take a look at this link: https://www.chiefdelphi.com/t/adding-camera-stream-to-shuffleboard-using-code/444532/3
      //Camera entry
      mainTab.addCamera("Front Camera", "Camera1", "http://10.64.11.2:1181/?action=stream")
         .withProperties(Map.of("showControls",false))
         .withPosition(7, 0)
         .withSize(4, 4);
   }

   /**
    * Gets the inputted config data from the shuffle dashboard. Here is an index of all the keys that should be in the map:
    *    - "driveTrainMax" Max speed for drive train
    *    - "falconMax" Max speed for falcon
    *    - "neosMax" Max speed for neos
    *    - "driveMode" Driving mode that is specified; this ranges from values 1-3 depending on chosen mode:
    *       1: "Tank"
    *       2: "Arcade"
    *       3: "TriggerHybrid"
    * @return A map of all the given key and value pairs for the config
    */
   public HashMap<String, Double> getWrittenData(){
      //Creates variable to store all the data in
      HashMap<String, Double> data = new HashMap<String, Double>();
      
      //Assigns the drive train modes to numbers (to by type compatible)
      double driveModeTranslated = 0;
      switch (writableEntries.driveMode.getString("TriggerHybrid")){
         case "Tank":
            driveModeTranslated = 1;
            break;
         case "Arcade":
            driveModeTranslated = 2;
            break;
         case "TriggerHybrid":
            driveModeTranslated = 3;
            break;
      }

      //Gets the assigned data from the config entries
      data.put("driveTrainMax",writableEntries.maxDRTN.getDouble(Constants.DefaultSystemSpeeds.driveTrain));
      data.put("falconMax",writableEntries.maxFLCN.getDouble(Constants.DefaultSystemSpeeds.falcon));
      data.put("neosMax",writableEntries.maxNEOS.getDouble(Constants.DefaultSystemSpeeds.neos));
      data.put("cimsMax",writableEntries.maxCIMS.getDouble(Constants.DefaultSystemSpeeds.cims));
      data.put("driveMode",driveModeTranslated);
      return data;
   }

   /**
    * Gets all the necessary data from the subsystems and uploads them to the dashboard
    * @param operationMode What to update the operation mode entry on the dashboard to
    */
   public void updateReadableData(String operationMode){
      //Drive Train Speed
      double[] driveTrainSpeed = driveTrain.getCurrentSpeed();
      readableEntries.liveSpeedDTRNOverall.setDouble(-driveTrainSpeed[0]);
      readableEntries.liveSpeedDTRNDirectional.setDouble(driveTrainSpeed[1]);

      //Cannon Speeds and angle (along with servo)
      readableEntries.liveSpeedFLCN.setDouble(cannon.getCurrentFalconSpeed());
      readableEntries.liveSpeedNEOS.setDouble(cannon.getCurrentNeoSpeed());
      double cannonAngle = (cannon.getEncoder() / 100) * 360;
      readableEntries.cannonAngle.setDouble(cannonAngle);
      readableEntries.servoAngle.setDouble(cannon.getCurrentServoAngle());

      //Lifter speeds
      readableEntries.liveSpeedCIMS.setDouble(lifter.getCurrentSpeed());

      //Match/Operation Details
      readableEntries.robotOperationMode.setString(operationMode);
      int minute = (int) Math.floor(timerReadout / 60);
      float second =  (float) (timerReadout - (minute * 60));
      readableEntries.matchTimerEntry.setString(minute + ":" + second);
   }

   /**
    * Starts the match timer counter
    * @param operationMode Which time amount to start at (see Constants.Other for further info)
    */
   public void startMatchTimer(String operationMode){
      matchTimerActive = true;
   }
   /**Pauses the timer */
   public void pauseMatchTimer(){
      matchTimerActive = false;
   }
   /**
    * Resets the timer to its original state before it was started
    * @param operationMode What time to reset to
    */
   public void resetMatchTimer(String operationMode){
      pauseMatchTimer();
      if (operationMode == "Auto"){
         timerReadout = Constants.Other.autoDuration;
      } else {
         timerReadout = Constants.Other.teleopDuration;
      }
   }
}
