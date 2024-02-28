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

   private String currentOperationMode = "Periodic";

   public RobotContainer() {
      //System initializations
      driveTrain = new DriveTrain();
      cannon = new Cannon();
      lifter = new Lifter();
      playerControls = new PlayerControls(driveTrain, cannon, lifter);
      dashboard = new DashboardControl(driveTrain, cannon, lifter);
   }

   //Runs every 20ms, is tied to normal robot periodic
   public void periodic(){
      playerControls.run();
      dashboard.updateReadableData(currentOperationMode);
   }

   //When teleop starts...
   public void onTeleopStart(){
      //Sets the current modes for miscellenous operation
      currentOperationMode = "Teleop";
      dashboard.resetMatchTimer(currentOperationMode);
      dashboard.startMatchTimer(currentOperationMode);

      //Gets the config data from the dashboard entries and writes them to the subsystems accordingly
      HashMap<String, Double> configData = dashboard.getWrittenData();
      driveTrain.setMaxSpeed(configData.get("driveTrainMax"));
      cannon.setMaxFalconSpeed(configData.get("falconMax"));
      cannon.setMaxNeoSpeed(configData.get("neosMax"));
      lifter.setMaxSpeed(configData.get("cimsMax"));
      double driveMode = configData.get("driveMode");
      if (driveMode == 1){
         driveTrain.setDriveMode("Tank");
      } else if (driveMode == 2){
         driveTrain.setDriveMode("Arcade");
      } else if (driveMode == 3){
         driveTrain.setDriveMode("TriggerHybrid");
      }

      //Resets the falcon encoder
      cannon.resetFalconEncoder();
   }

   /**When auto starts... */
   public void onAutoStart(){
      //Sets the current modes for miscellenous operation
      currentOperationMode = "Auto";
      dashboard.resetMatchTimer(currentOperationMode);
      dashboard.startMatchTimer(currentOperationMode);
   }

   /**When test starts... */
   public void onTestStart(){
      //Sets the current modes for miscellenous operation
      currentOperationMode = "Test";
      dashboard.resetMatchTimer(currentOperationMode);
      dashboard.startMatchTimer(currentOperationMode);
   }

   /**When bot is disabled */
   public void onDisable(){
      //Reset the match timer
      // dashboard.resetMatchTimer(currentOperationMode);
   }
}
