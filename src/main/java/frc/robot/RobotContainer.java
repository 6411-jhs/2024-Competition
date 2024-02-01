package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.commands.RunPlayerControls;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Outtake;

public class RobotContainer {
   //Subsystem definitions
   private DriveTrain driveTrain;
   private Outtake outtake;
   //Command definitions
   private RunPlayerControls runPlayerControls;

   public RobotContainer() {
      //Class initializations
      driveTrain = new DriveTrain();
      outtake = new Outtake();
      runPlayerControls = new RunPlayerControls(driveTrain, outtake);
      //Default command routing
      CommandScheduler.getInstance().setDefaultCommand(driveTrain, runPlayerControls);
   }
}
