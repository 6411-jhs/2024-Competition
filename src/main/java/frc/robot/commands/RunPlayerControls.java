package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class RunPlayerControls extends Command {
   private XboxController xbox;
   private DriveTrain drive;

   public RunPlayerControls(DriveTrain p_drive){
      xbox = new XboxController(Constants.xboxPort);
      drive = p_drive;
      addRequirements(p_drive);
   }

   @Override
   public void execute() {
      switch(Constants.drivingStyle){
         case "Tank":
            drive.tankDrive(xbox.getLeftY(), xbox.getRightY());
            break;
         case "Arcade":
            drive.arcadeDrive(xbox.getLeftY(), xbox.getRightX());
            break;
      }
   }
}
