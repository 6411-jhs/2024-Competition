package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Outtake;

public class RunPlayerControls extends Command {
   private XboxController xbox;
   private DriveTrain drive;
   private Outtake outtake;

   public RunPlayerControls(DriveTrain p_drive, Outtake p_outtake){
      //Object definitions for the controllers and the required subsystems
      xbox = new XboxController(Constants.xboxPort);
      drive = p_drive;
      outtake = p_outtake;
      addRequirements(p_drive, p_outtake);
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
      otherControls();
   }

   //Handles the controls that are outside of the drive train
   private void otherControls(){
      if (xbox.getAButton()){
         outtake.on();
      } else outtake.off();
   }
}
