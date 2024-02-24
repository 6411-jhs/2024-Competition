package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends SubsystemBase {
   //Object definitions for the individual motor controllers on the drive train
   private WPI_VictorSPX frontLeft, frontRight, backLeft, backRight;
   //FRC's drivetrain class for the tank drive provided in the kit of parts (operates the motor controllers in all together)
   private DifferentialDrive drive;

   public DriveTrain() {
      //Initializes motor controllers
      frontLeft = new WPI_VictorSPX(Constants.CANAssignments.frontLeftDT);
      backLeft = new WPI_VictorSPX(Constants.CANAssignments.backLeftDT);
      frontRight = new WPI_VictorSPX(Constants.CANAssignments.frontRightDT);
      backRight = new WPI_VictorSPX(Constants.CANAssignments.backRightDT);

      //Tells the back left motor to use whatever power the front left motor is set to (same is done for the right motors)
      //This essentially groups the motors from 4 different motor controllers to just 2.
      //Makes it easier because there's no need to control one motor individually on the drive train; only one side
      backLeft.follow(frontLeft);
      backRight.follow(frontRight);
      frontRight.setInverted(false);
      backRight.setInverted(false);

      //Initializes the drive train object
      drive = new DifferentialDrive(frontLeft, frontRight);
   }

   /**
    * Operates the robot in tank drive (each side of wheel powered seperately)
    * @param leftSpeed The speed ranging from full power in reverse (-1) to full power forward (1). This controls the left motors on the DT.
    * @param rightSpeed The speed ranging from full power in reverse (-1) to full power forward (1). This controls the right motors on the DT.
    */
   public void tankDrive(double leftSpeed, double rightSpeed) {
      drive.tankDrive(leftSpeed * Constants.MAXSystemSpeeds.driveTrain, rightSpeed * Constants.MAXSystemSpeeds.driveTrain);
   }
   /**
    * Operates the robot in arcade drive (wheel speed is under one value instead of two. Turning value varies the speed on the correlated side to turn)
    * @param speed The speed ranging from full power in reverse (-1) to full power forward (1). This controls overall speed of the DT.
    * @param direction The turning direction ranging from full left (-1) to full right (1). This varries the speed of one side of the DT to turn.
    */
   public void arcadeDrive(double speed, double direction){
      drive.arcadeDrive(speed * Constants.MAXSystemSpeeds.driveTrain, direction * Constants.MAXSystemSpeeds.driveTrain);
   }
   /**
    * Sets the speed of the left wheels (motors) of the DT
    * @param speed Speed of the motors ranging from full power in reverse (-1) to full power forward (1).
    */
   public void driveLeftMotors(double speed){
      frontLeft.set(speed * Constants.MAXSystemSpeeds.driveTrain);
   }
   /**
    * Sets the speed of the right wheels (motors) of the DT
    * @param speed Speed of the motors ranging from full power in reverse (-1) to full power forward (1).
    */
   public void driveRightMotors(double speed){
      frontRight.set(speed * Constants.MAXSystemSpeeds.driveTrain);
   }
}
