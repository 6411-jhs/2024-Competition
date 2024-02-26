package frc.robot.util;
import java.util.Map;
import java.util.HashMap;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import frc.robot.RobotContainer;
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

   private RobotContainer robotContainer;
   private ShuffleboardTab mainTab;
   public Writables writableEntries; 

   public DashboardControl(RobotContainer p_robotContainer){
      robotContainer = p_robotContainer;

      mainTab = Shuffleboard.getTab("Main");
      Shuffleboard.selectTab("Main");
      writableEntries = new Writables();

      writableEntries.maxDRTN = mainTab.addPersistent("MAX Drive Train Speed", Constants.DefaultSystemSpeeds.driveTrain)
         .withWidget(BuiltInWidgets.kNumberSlider)
         .withProperties(Map.of("min", 0, "max", 1))
         .getEntry();
      writableEntries.maxFLCN = mainTab.addPersistent("MAX Falcon Speed (Cannon)", Constants.DefaultSystemSpeeds.falcon)
         .withWidget(BuiltInWidgets.kNumberSlider)
         .withProperties(Map.of("min", 0, "max", 1))
         .getEntry();
      writableEntries.maxNEOS = mainTab.addPersistent("MAX Neo Speeds (Cannon)", Constants.DefaultSystemSpeeds.neos)
         .withWidget(BuiltInWidgets.kNumberSlider)
         .withProperties(Map.of("min", 0, "max", 1))
         .getEntry();
      writableEntries.driveMode = mainTab.addPersistent("Drive Mode", Constants.UserControls.defaultDrivingStyle)
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
}
