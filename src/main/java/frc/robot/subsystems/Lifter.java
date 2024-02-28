package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Lifter extends SubsystemBase {
   private WPI_VictorSPX leftMotor;
   private WPI_VictorSPX rightMotor;

   private double maxSpeed = Constants.DefaultSystemSpeeds.cims;
   private double currentSpeed = 0;
   public Lifter(){
      leftMotor = new WPI_VictorSPX(Constants.CANAssignments.leftLCim);
      rightMotor = new WPI_VictorSPX(Constants.CANAssignments.rightLCim);
      leftMotor.setInverted(true);
   }

   public void on(){
      currentSpeed = maxSpeed;
      leftMotor.set(maxSpeed);
      rightMotor.set(maxSpeed);
   }

   public void off(){
      currentSpeed = 0;
      leftMotor.set(0);
      rightMotor.set(0);
   }

   public void reverse(){
      currentSpeed = -maxSpeed;
      leftMotor.set(-maxSpeed);
      rightMotor.set(-maxSpeed);
   }

   public void setMaxSpeed(double speed){
      maxSpeed = speed;
   }

   public double getCurrentSpeed(){
      return currentSpeed;
   }

   public void test(double speed){
      rightMotor.set(speed);
   }
}
