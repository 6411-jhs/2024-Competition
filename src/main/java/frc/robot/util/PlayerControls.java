package frc.robot.util;

import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Cannon;
import frc.robot.commands.SetCannonAngle;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;

public class PlayerControls {
   //Subsystems
   private DriveTrain drive;
   private Cannon cannon;
   //Other
   private CommandXboxController xbox;
   private CommandJoystick joystick;
   private SetCannonAngle setAngle90;
   //Local Parameters
   private boolean reverseCannon = false;
   private double drivetrainSpeedSet = 1;

   public PlayerControls(DriveTrain p_drive, Cannon p_cannon){
      drive = p_drive;
      cannon = p_cannon;
      xbox = new CommandXboxController(Constants.UserControls.xboxPort);
      joystick = new CommandJoystick(Constants.UserControls.joystickPort);
      setAngle90 = new SetCannonAngle(cannon, 90);

      xbox.a().onTrue(Commands.run(() -> {
         drivetrainSpeedSet = 0.5;
      }));
      xbox.b().onTrue(Commands.run(() -> {
         drivetrainSpeedSet = 0.75;
      }));
      xbox.y().onTrue(Commands.run(() -> {
         drivetrainSpeedSet = 1;
      }));

      joystick.trigger()
         .whileTrue(Commands.run(() -> {
            cannon.on(reverseCannon);
         }))
         .onFalse(Commands.run(() -> {
            cannon.off();
         }));
      joystick.button(2).onTrue(Commands.run(() -> {
         reverseCannon = !reverseCannon;
      }));
      joystick.button(11).onTrue(setAngle90);
   }

   public void run(){
      driveTrainControls();
      cannonControls();
   }

   private void driveTrainControls(){
      switch(Constants.UserControls.drivingStyle){
         //If drive train mode is set to tank drive; operate tank drive
         case "Tank":
            drive.tankDrive(xbox.getLeftY() * drivetrainSpeedSet, xbox.getRightY() * drivetrainSpeedSet);
            break;
         //If drive train mode is set to arcade drive; operate arcade drive
         case "Arcade":
            drive.arcadeDrive(xbox.getLeftY() * drivetrainSpeedSet, xbox.getRightX() * drivetrainSpeedSet);
            break;
         //If drive train mode is set to trigger hybrid drive; operate trigger hybrid drive
         case "TriggerHybrid":
            drive.arcadeDrive((xbox.getRightTriggerAxis() - xbox.getLeftTriggerAxis()) * drivetrainSpeedSet, xbox.getLeftX() * drivetrainSpeedSet);
            break;
      }
   }

   private void cannonControls(){
      cannon.setFalcon(joystick.getY(), false);
   }
}
