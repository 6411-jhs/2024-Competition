package frc.robot.util;
import java.util.Map;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.DriveTrain;
import frc.robot.Constants;

public class DashboardControl {
   @SuppressWarnings("unused")
   private class Writables {
      GenericEntry maxDRTN;
      GenericEntry maxFLCN;
      GenericEntry maxNEOS;
      GenericEntry driveMode;
      GenericEntry autoCommand;
   }
   @SuppressWarnings("unused")
   private class Readables {
      GenericEntry cannonAngle;
      GenericEntry liveSpeedDTRNOverall;
      GenericEntry liveSpeedDTRNDirectional;
      GenericEntry liveSpeedFLCN;
      GenericEntry liveSpeedNEOS;
      GenericEntry servoAngle;
      GenericEntry robotOperationMode;
      GenericEntry matchTimerEntry;
   }

   private DriveTrain driveTrain;
   private Cannon cannon;
   private ShuffleboardTab mainTab;
   public Writables writableEntries; 
   public Readables readableEntries;

   private Timer matchTimer;

   public DashboardControl(DriveTrain p_driveTrain, Cannon p_cannon){
      driveTrain = p_driveTrain;
      cannon = p_cannon;

      mainTab = Shuffleboard.getTab("Main");
      Shuffleboard.selectTab("Main");
      writableEntries = new Writables();
      readableEntries = new Readables();

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
      writableEntries.driveMode = mainTab.addPersistent("Drive Mode", Constants.UserControls.defaultDrivingStyle)
         .withWidget(BuiltInWidgets.kTextView)
         .withSize(2,1)
         .withPosition(11, 3)
         .getEntry();
      
      //Defines readable entries
      readableEntries.cannonAngle = mainTab.add("Cannon Angle",0)
         .withWidget(BuiltInWidgets.kDial)
         .withProperties(Map.of("min", 0, "max", 180))
         .getEntry();
      readableEntries.servoAngle = mainTab.add("Servo Angle",0)
         .withWidget(BuiltInWidgets.kGyro)
         .withProperties(Map.of("min", 0, "max", 180))
         .getEntry();
      readableEntries.liveSpeedDTRNOverall = mainTab.add("DT Speed Input (Overall)",0)
         .withWidget(BuiltInWidgets.kDial)
         .withProperties(Map.of("min", -1, "max", 1))
         .getEntry();
      readableEntries.liveSpeedDTRNDirectional = mainTab.add("DT Speed Input (Directional)",0)
         .withWidget(BuiltInWidgets.kDial)
         .withProperties(Map.of("min", -1, "max", 1))
         .getEntry();
      readableEntries.liveSpeedFLCN = mainTab.add("Falcon Speed Input (Cannon)",0)
         .withWidget(BuiltInWidgets.kNumberBar)
         .withProperties(Map.of("min", 0, "max", 1))
         .getEntry();
      readableEntries.liveSpeedNEOS = mainTab.add("Neos Speed Input (Cannon)",0)
         .withWidget(BuiltInWidgets.kGraph)
         .withProperties(Map.of("min", 0, "max", 1))
         .getEntry();
      readableEntries.robotOperationMode = mainTab.add("Robot Operation Mode","Null")
         .withWidget(BuiltInWidgets.kTextView)
         .getEntry();
      readableEntries.matchTimerEntry = mainTab.add("Match Timer","2:30")
         .withWidget(BuiltInWidgets.kTextView)
         .getEntry();
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
      HashMap<String, Double> data = new HashMap<String, Double>();
      
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

      data.put("driveTrainMax",writableEntries.maxDRTN.getDouble(1));
      data.put("falconMax",writableEntries.maxFLCN.getDouble(1));
      data.put("neosMax",writableEntries.maxNEOS.getDouble(1));
      data.put("driveMode",driveModeTranslated);
      return data;
   }

   public void updateReadableData(String operationMode){
      double[] driveTrainSpeed = driveTrain.getCurrentSpeed();
      readableEntries.liveSpeedDTRNOverall.setDouble(-driveTrainSpeed[0]);
      readableEntries.liveSpeedDTRNDirectional.setDouble(driveTrainSpeed[1]);
      readableEntries.liveSpeedFLCN.setDouble(cannon.getCurrentFalconSpeed());
      readableEntries.liveSpeedNEOS.setDouble(cannon.getCurrentNeoSpeed());
      double cannonAngle = (cannon.getEncoder() / 100) * 360;
      readableEntries.cannonAngle.setDouble(cannonAngle);
      readableEntries.robotOperationMode.setString(operationMode);
   }

   public void startMatchTimer(){//!
   }
}
