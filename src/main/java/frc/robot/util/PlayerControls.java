package frc.robot.util;
import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Lifter;
import frc.robot.commands.SetCannonAngle;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;

public class PlayerControls {
   //Subsystems
   private DriveTrain drive;
   private Cannon cannon;
   private Lifter lifter;
   //Other
   private CommandXboxController xbox;
   private CommandJoystick joystick;
   private Timer lifterTimer;
   private SetCannonAngle setAngle90;
   //Local Parameters
   private boolean reverseCannon = false;
   private boolean overrideFalconControls = false;
   private double drivetrainSpeedSet = 1;
   private double lifterTimeRaiseAmount = 0.4;
   private double lifterTimeCounter = 0;
   private boolean lifterActive = false;

   public PlayerControls(DriveTrain p_drive, Cannon p_cannon, Lifter p_lifter){
      //Subsystem Saving
      drive = p_drive;
      cannon = p_cannon;
      lifter = p_lifter;
      //Controller definitions
      xbox = new CommandXboxController(Constants.UserControls.xboxPort);
      joystick = new CommandJoystick(Constants.UserControls.joystickPort);

      lifterTimer = new Timer();
      lifterTimer.schedule(new TimerTask() {
         @Override
         public void run() {
             if (lifterActive){
               if (lifterTimeCounter < lifterTimeRaiseAmount){
                  lifter.on();
                  lifterTimeCounter += 0.01;
               } else {
                  lifter.off();
               }
             } else {
               if (lifterTimeCounter > 0){
                  lifter.reverse();
                  lifterTimeCounter -= 0.01;
               } else {
                  lifter.off();
               }
             }
         }
      }, 0, 10);
      
      //Command definitions
      setAngle90 = new SetCannonAngle(cannon, 90);

      //Xbox button routing; adds the speed changing controls; adds the lifter controls
      xbox.a().onTrue(Commands.runOnce(() -> {
         drivetrainSpeedSet = 0.5;
      }));
      xbox.b().onTrue(Commands.runOnce(() -> {
         drivetrainSpeedSet = 0.75;
      }));
      xbox.y().onTrue(Commands.runOnce(() -> {
         drivetrainSpeedSet = 1;
      }));
      xbox.start()
         .onTrue(Commands.runOnce(() -> {
            lifterActive = !lifterActive;
         }));
      
      xbox.x()
         .onTrue(Commands.runOnce(() -> {
            lifter.test(0.4);
         }))
         .onFalse(Commands.runOnce(() -> {
            lifter.test(0);
         }));

      //Joystick button routing; adds the cannon controls
      joystick.trigger()
         .onTrue(Commands.runOnce(() -> {
            cannon.on(reverseCannon);
         }))
         .onFalse(Commands.runOnce(() -> {
            cannon.off();
         }));
      joystick.button(2).onTrue(Commands.runOnce(() -> {
         reverseCannon = !reverseCannon;
      }));
      joystick.button(11).onTrue(createFalconCommand(setAngle90));
      joystick.button(3)
         .onTrue(Commands.runOnce(() -> {
            cannon.setServo(160);
         }))
         .onFalse(Commands.runOnce(() -> {
            cannon.setServo(100);
         }));
   }

   /**Runs the live input controls for the robot */
   public void run(){
      driveTrainControls();
      cannonControls();
   }

   /**Takes the xbox controller joystick axis values and operates the drive train based on chosen mode */
   private void driveTrainControls(){
      switch(drive.driveMode){
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
            drive.arcadeDrive((xbox.getRightTriggerAxis() - xbox.getLeftTriggerAxis()) * -drivetrainSpeedSet, xbox.getLeftX() * drivetrainSpeedSet);
            break;
      }
   }

   /**Takes the joystick axis values and operates the falcon with it*/
   private void cannonControls(){
      if (!overrideFalconControls){
         cannon.setFalcon(joystick.getY(), false);
      }
   }

   /**
    * Adds a protection measure for the falcon to prevent race conditions between the commands and player input
    * The angle setting commands are supposed to be passed to this function so that it can disable the control
    * authority of the player. Essentially when a command is active this will disable the player's control over
    * the falcon
    * @param cmd The command that is going to control the falcon
    * @return A command sequence that disables player controls, executed the command, then re-enabled the player controls
    */
   private Command createFalconCommand(Command cmd){
      return Commands.sequence(
         Commands.runOnce(() -> {overrideFalconControls = true;}),
         cmd,
         Commands.runOnce(() -> {overrideFalconControls = false;})
      );
   }
}
