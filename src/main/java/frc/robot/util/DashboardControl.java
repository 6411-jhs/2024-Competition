package frc.robot.util;
import java.util.Map;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import frc.robot.RobotContainer;

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

      writableEntries.maxDRTN = mainTab.addPersistent("MAX Drive Train Speed", 1)
         .withWidget(BuiltInWidgets.kNumberSlider)
         .withProperties(Map.of("min", -1, "max", 1))
         .getEntry();
      writableEntries.maxFLCN = mainTab.addPersistent("MAX Falcon Speed (Cannon)", 1)
         .withWidget(BuiltInWidgets.kNumberSlider)
         .withProperties(Map.of("min", -1, "max", 1))
         .getEntry();
      writableEntries.maxNEOS = mainTab.addPersistent("MAX Neo Speeds (Cannon)", 1)
         .withWidget(BuiltInWidgets.kNumberSlider)
         .withProperties(Map.of("min", -1, "max", 1))
         .getEntry();
   }
}
