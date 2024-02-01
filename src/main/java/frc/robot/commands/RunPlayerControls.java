package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class RunPlayerControls extends Command {
   private XboxController xbox;
   private DriveTrain drive;

   public RunPlayerControls(DriveTrain p_drive){
      //Object definitions for the controllers and the required subsystems
      xbox = new XboxController(Constants.xboxPort);
      drive = p_drive;
      addRequirements(p_drive);
   }

   @Override
   public void execute() {
      switch(Constants.drivingStyle){
         //If drive train mode is set to tank drive; operate tank drive
         case "Tank":
            drive.tankDrive(xbox.getLeftY(), xbox.getRightY());
            break;
         //If drive train mode is set to arcade drive; operate arcade drive
         case "Arcade":
            drive.arcadeDrive(xbox.getLeftY(), xbox.getRightX());
            break;
         //If drive train mode is set to trigger hybrid drive; operate trigger hybrid drive
         case "TriggerHybrid":
            drive.arcadeDrive(xbox.getRightTriggerAxis() * Constants.SystemSpeeds.driveTrain - (xbox.getLeftTriggerAxis() * Constants.SystemSpeeds.driveTrain), xbox.getLeftX() * Constants.SystemSpeeds.driveTrain);
            break;
      }
   }
}
