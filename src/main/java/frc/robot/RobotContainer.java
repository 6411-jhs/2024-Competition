package frc.robot;

import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.RunPlayerControls;

public class RobotContainer {
   private DriveTrain driveTrain;
   private RunPlayerControls runPlayerControls;

   public RobotContainer() {
      driveTrain = new DriveTrain();
      runPlayerControls = new RunPlayerControls(driveTrain);
      CommandScheduler.getInstance().setDefaultCommand(driveTrain, runPlayerControls);
   }
}
