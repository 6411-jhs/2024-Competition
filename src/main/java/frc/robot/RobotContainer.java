package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.commands.RunPlayerControls;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Cannon;

public class RobotContainer {
   //Subsystem definitions
   private DriveTrain driveTrain;
   private Cannon cannon;
   //Command definitions
   private RunPlayerControls runPlayerControls;

   public RobotContainer() {
      //Class initializations
      driveTrain = new DriveTrain();
      cannon = new Cannon();
      runPlayerControls = new RunPlayerControls(driveTrain, cannon);
      //Default command routing
      CommandScheduler.getInstance().setDefaultCommand(driveTrain, runPlayerControls);
   }
}
