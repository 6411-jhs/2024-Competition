package frc.robot;

import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.RunPlayerControls;

public class RobotContainer {
   //Subsystem definitions
   private DriveTrain driveTrain;
   //Command definitions
   private RunPlayerControls runPlayerControls;

   public RobotContainer() {
      //Class initializations
      driveTrain = new DriveTrain();
      runPlayerControls = new RunPlayerControls(driveTrain);
      //Default command routing
      CommandScheduler.getInstance().setDefaultCommand(driveTrain, runPlayerControls);
   }
}
