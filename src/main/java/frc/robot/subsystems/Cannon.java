package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel;
import edu.wpi.first.wpilibj.Servo;

import frc.robot.Constants;

public class Cannon extends SubsystemBase {
   //Motor controller definitions
   private CANSparkMax leftNeo;
   private CANSparkMax rightNeo; 
   private TalonFX falcon;
   private Servo servo;

   public double maxFalconSpeed = Constants.DefaultSystemSpeeds.neos;
   public double maxNeoSpeed = Constants.DefaultSystemSpeeds.neos;
   private double currentFalconSpeed = 0;
   private double currentNeoSpeed = 0;
   private double currentServoAngle = 0;
   public Cannon(){
      //Motor controller initializations
      leftNeo = new CANSparkMax(Constants.CANAssignments.leftCNeo, CANSparkLowLevel.MotorType.kBrushless);
      rightNeo = new CANSparkMax(Constants.CANAssignments.rightCNeo, CANSparkLowLevel.MotorType.kBrushless);
      leftNeo.setInverted(true);
      falcon = new TalonFX(Constants.CANAssignments.mainCFalcon);
      falcon.setPosition(0);
      falcon.setNeutralMode(NeutralModeValue.Brake);
      servo = new Servo(Constants.PWMAssignments.mainCServo);
   }

   /**
    * Sets the speed of the neo motors to the specified constant
    * @param reverse Whether or not to run the neos in referse; this swaps the sign of the constant
    */
   public void on(boolean reverse){
      double speed = (reverse) ? (-maxNeoSpeed) : (maxNeoSpeed);
      currentNeoSpeed = speed;
      leftNeo.set(speed);
      rightNeo.set(speed);
   }
   /**Sets the neo motor speeds to 0 */
   public void off(){
      currentNeoSpeed = 0;
      leftNeo.set(0);
      rightNeo.set(0);
   }

   /**
    * Gets the raw encoder value of the falcon motor. Rotations are counted
    * @return The raw position of the motor.
    */
   public double getEncoder(){
      return falcon.getPosition().getValue();
   }

   /**
    * Sets the raw speed value of the neos
    * @param speed Speed to set. 1 for full forward and -1 for full reverse
    */
   public void setNeos(double speed){
      currentNeoSpeed = speed * maxNeoSpeed;
      leftNeo.set(speed * maxNeoSpeed);
      rightNeo.set(speed * maxNeoSpeed);
   }

   /**
    * Sets the raw speed value of the falcon
    * @param speed Speed to set. 1 for full forward and -1 for full reverse
    * @param ignoreMaxSpeed Whether or not to ignore the max speed of the falcon set my the constants (Constants.MAXSystemSpeeds.falcon)
    */
   public void setFalcon(double speed, boolean ignoreMaxSpeed){
      if (ignoreMaxSpeed){
         currentFalconSpeed = speed;
         falcon.set(speed);
      } else {
         currentFalconSpeed = speed * maxFalconSpeed;
         falcon.set(speed * maxFalconSpeed);
      }
   }

   /**Resets the falcon encoder value to 0 */
   public void resetFalconEncoder(){
      falcon.setPosition(0);
   }

   /**
    * Sets the maximum speed for the neos on the cannon
    * @param speed Anywhere from 0 to 1
    */
   public void setMaxNeoSpeed(double speed){
      maxNeoSpeed = speed;
   }

   /**
    * Sets the maximum speed the falcon on the cannon can go
    * @param speed Anywhere from 0 to 1
    */
   public void setMaxFalconSpeed(double speed){
      maxFalconSpeed = speed;
   }

   /**
    * Gets the current falcon speed
    * @return
    */
   public double getCurrentFalconSpeed(){
      return currentFalconSpeed;
   }

   /**
    * Gets the current neo speed
    * @return
    */
   public double getCurrentNeoSpeed(){
      return currentNeoSpeed;
   }

   /**
    * Sets the servo to a specified degree
    * @param degree The degree to set the servo to; anywhere from  0-180
    */
   public void setServo(double degree){
      currentServoAngle = degree;
      // double calcDegree = (147.0 / 180.0) * degree;
      servo.setAngle(degree);
   }

   /**
    * Gets the current degree angle the servo is set to
    * @return The angle from 0-180 degrees
    */
   public double getCurrentServoAngle(){
      return currentServoAngle;
   }
}
