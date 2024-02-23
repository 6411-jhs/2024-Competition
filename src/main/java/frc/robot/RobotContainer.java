package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.util.PlayerControls;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Cannon;

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
      //Default command routing
   }

   public void periodic(){
      playerControls.run();
   }

   public void onTeleopStart(){
      cannon.resetFalconEncoder();
   }
}
