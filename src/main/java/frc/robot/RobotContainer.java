package frc.robot;
import java.util.HashMap;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Lifter;
import frc.robot.util.PlayerControls;
import frc.robot.util.DashboardControl;

public class RobotContainer {
   //Subsystem definitions
   private DriveTrain driveTrain;
   private Cannon cannon;
   private Lifter lifter;
   //Other definitions
   private PlayerControls playerControls;
   private DashboardControl dashboard;

   public RobotContainer() {
      //Class initializations
      driveTrain = new DriveTrain();
      cannon = new Cannon();
      lifter = new Lifter();
      playerControls = new PlayerControls(driveTrain, cannon, lifter);
      dashboard = new DashboardControl(driveTrain, cannon);
   }

   //Runs every 20ms, is tied to normal robot periodic
   public void periodic(){
      playerControls.run();
      dashboard.updateReadableData("Periodic");//!
   }

   //When teleop starts...
   public void onTeleopStart(){
      HashMap<String, Double> configData = dashboard.getWrittenData();
      System.out.println(configData.toString());
      driveTrain.setMaxSpeed(configData.get("driveTrainMax"));
      cannon.setMaxFalconSpeed(configData.get("falconMax"));
      cannon.setMaxNeoSpeed(configData.get("neosMax"));

      double driveMode = configData.get("driveMode");
      if (driveMode == 1){
         driveTrain.setDriveMode("Tank");
      } else if (driveMode == 2){
         driveTrain.setDriveMode("Arcade");
      } else if (driveMode == 3){
         driveTrain.setDriveMode("TriggerHybrid");
      }

      cannon.resetFalconEncoder();
   }
}
