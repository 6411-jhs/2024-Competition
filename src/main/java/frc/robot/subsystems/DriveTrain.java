package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANAssignments;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends SubsystemBase {
   private WPI_VictorSPX frontLeft, frontRight, backLeft, backRight;
   private DifferentialDrive drive;

   public DriveTrain() {
      frontLeft = new WPI_VictorSPX(CANAssignments.frontLeftDT);
      backLeft = new WPI_VictorSPX(CANAssignments.backLeftDT);
      frontRight = new WPI_VictorSPX(CANAssignments.frontRightDT);
      backRight = new WPI_VictorSPX(CANAssignments.backRightDT);

      backLeft.follow(frontLeft);
      backRight.follow(frontRight);

      drive = new DifferentialDrive(frontLeft, frontRight);
   }

   public void tankDrive(double leftSpeed, double rightSpeed) {
      drive.tankDrive(leftSpeed, rightSpeed);
   }
   public void arcadeDrive(double speed, double direction){
      drive.arcadeDrive(speed, direction);
   }
   public void driveLeftMotors(double speed){
      frontLeft.set(speed);
   }
   public void driveRightMotors(double speed){
      frontRight.set(speed);
   }
}
