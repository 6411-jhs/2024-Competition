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

   public double maxSpeed = Constants.DefaultSystemSpeeds.driveTrain;
   public String driveMode = Constants.UserControls.defaultDrivingStyle;
   private double currentSpeedOverall = 0;
   private double currentSpeedDirectional = 0;
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
      frontLeft.setInverted(true);
      backLeft.setInverted(true);

      //Initializes the drive train object
      drive = new DifferentialDrive(frontLeft, frontRight);
   }

   /**
    * Operates the robot in tank drive (each side of wheel powered seperately)
    * @param leftSpeed The speed ranging from full power in reverse (-1) to full power forward (1). This controls the left motors on the DT.
    * @param rightSpeed The speed ranging from full power in reverse (-1) to full power forward (1). This controls the right motors on the DT.
    */
   public void tankDrive(double leftSpeed, double rightSpeed) {
      currentSpeedOverall = ((leftSpeed * maxSpeed) + rightSpeed * maxSpeed) / 2;
      currentSpeedDirectional = (leftSpeed * maxSpeed) - (rightSpeed * maxSpeed);
      drive.tankDrive(leftSpeed * maxSpeed, rightSpeed * maxSpeed);
   }
   /**
    * Operates the robot in arcade drive (wheel speed is under one value instead of two. Turning value varies the speed on the correlated side to turn)
    * @param speed The speed ranging from full power in reverse (-1) to full power forward (1). This controls overall speed of the DT.
    * @param direction The turning direction ranging from full left (-1) to full right (1). This varries the speed of one side of the DT to turn.
    */
   public void arcadeDrive(double speed, double direction){
      currentSpeedOverall = speed * maxSpeed;
      currentSpeedDirectional = direction * maxSpeed;
      drive.arcadeDrive(speed * maxSpeed, direction * maxSpeed);
   }
   /**
    * Sets the speed of the left wheels (motors) of the DT
    * @param speed Speed of the motors ranging from full power in reverse (-1) to full power forward (1).
    */
   public void driveLeftMotors(double speed){
      frontLeft.set(speed * maxSpeed);
   }
   /**
    * Sets the speed of the right wheels (motors) of the DT
    * @param speed Speed of the motors ranging from full power in reverse (-1) to full power forward (1).
    */
   public void driveRightMotors(double speed){
      frontRight.set(speed * maxSpeed);
   }

   /**
    * Sets the maximum speed for the drive train (both directional and overall)
    * @param speed The speed to set the drive train; anywhere between -1 and 1
    */
   public void setMaxSpeed(double speed){
      maxSpeed = speed;
   }

   /**
    * Sets the drive mode for the drive train
    * @param p_driveMode Drive mode being either "Tank", "Arcade", or "TriggerHybrid"
    */
   public void setDriveMode(String p_driveMode){
      driveMode = p_driveMode;
   }

   /**
    * Gets the current speed that's being inputted into the drive train.
    * Please note that the speed calculation for tank drive isn't very accurate. Arcade works best
    * @return An array of two speed values; one ([0]) for overall speed and one ([1]) for directional speed
    */
   public double[] getCurrentSpeed(){
      double[] speed = {
         currentSpeedOverall,
         currentSpeedDirectional
      };
      return speed;
   }
}
