package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Cannon;

import frc.robot.commands.SetCannonAngle;

@SuppressWarnings("unused")
public class RunPlayerControls extends Command {
   private XboxController xbox;
   private Joystick joystick;
   private DriveTrain drive;
   private Cannon cannon;

   private SetCannonAngle setCannonAngle90;

   public RunPlayerControls(DriveTrain p_drive, Cannon p_cannon){
      //Object definitions for the controllers and the required subsystems
      xbox = new XboxController(Constants.UserControls.xboxPort);
      joystick = new Joystick(Constants.UserControls.joystickPort);
      drive = p_drive;
      cannon = p_cannon;
      addRequirements(p_drive, p_cannon);

      setCannonAngle90 = new SetCannonAngle(cannon, 90);
   }

   @Override
   public void execute() {
      tankdriveControls();
      cannonControls();
      otherControls();
   }

   private void tankdriveControls(){
      switch(Constants.UserControls.drivingStyle){
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
            drive.arcadeDrive(xbox.getRightTriggerAxis() - xbox.getLeftTriggerAxis(), xbox.getLeftX());
            break;
      }
   }

   private void cannonControls(){
      cannon.setFalcon(joystick.getY());
   }

   //Handles the controls that are outside of the drive train
   private void otherControls(){
      if (xbox.getAButton()){
         cannon.on(false);
      } else cannon.off();

      if (joystick.getRawButtonPressed(11)){
         CommandScheduler.getInstance().schedule(setCannonAngle90);
      }
   }
}
