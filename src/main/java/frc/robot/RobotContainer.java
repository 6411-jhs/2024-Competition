package frc.robot;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Cannon;
import frc.robot.util.PlayerControls;

public class RobotContainer {
   //Subsystem definitions
   private DriveTrain driveTrain;
   private Cannon cannon;
   //Other definitions
   private PlayerControls playerControls;

   public RobotContainer() {
      //Class initializations
      driveTrain = new DriveTrain();
      cannon = new Cannon();
      playerControls = new PlayerControls(driveTrain, cannon);
   }

   //Runs every 20ms, is tied to normal robot periodic
   public void periodic(){
      playerControls.run();
   }

   //When teleop starts...
   public void onTeleopStart(){
      cannon.resetFalconEncoder();
   }
}
