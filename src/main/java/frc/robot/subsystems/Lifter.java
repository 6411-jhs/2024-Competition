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
   }

   double speed1 = 0.5;
   double speed2 = 0.5;
   public void onLeft(){
      currentSpeed = maxSpeed;
      leftMotor.set(speed1);
   }
   public void reverseLeft(){
      currentSpeed = maxSpeed;
      leftMotor.set(-speed1);
   }

   public void onRight(){
      currentSpeed = maxSpeed;
      rightMotor.set(speed2);
   }
   public void reverseRight(){
      currentSpeed = maxSpeed;
      rightMotor.set(-speed2);
   }

   public void offLeft(){
      currentSpeed = 0;
      leftMotor.set(0);
   }

   public void offRight(){
      rightMotor.set(0);
   }

   public void setMaxSpeed(double speed){
      maxSpeed = speed;
   }

   public double getCurrentSpeed(){
      return currentSpeed;
   }
}
