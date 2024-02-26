package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel;

import frc.robot.Constants;

public class Cannon extends SubsystemBase {
   //Motor controller definitions
   private CANSparkMax leftNeo;
   private CANSparkMax rightNeo; 
   private TalonFX falcon;

   public double maxFalconSpeed = Constants.DefaultSystemSpeeds.neos;
   public double maxNeoSpeed = Constants.DefaultSystemSpeeds.neos;
   public Cannon(){
      //Motor controller initializations
      leftNeo = new CANSparkMax(Constants.CANAssignments.leftCNeo, CANSparkLowLevel.MotorType.kBrushless);
      rightNeo = new CANSparkMax(Constants.CANAssignments.rightCNeo, CANSparkLowLevel.MotorType.kBrushless);
      leftNeo.setInverted(true);
      falcon = new TalonFX(Constants.CANAssignments.mainCFalcon);
      falcon.setPosition(0);
      falcon.setNeutralMode(NeutralModeValue.Brake);
   }

   /**
    * Sets the speed of the neo motors to the specified constant
    * @param reverse Whether or not to run the neos in referse; this swaps the sign of the constant
    */
   public void on(boolean reverse){
      double speed = (reverse) ? (-maxNeoSpeed) : (maxNeoSpeed);
      leftNeo.set(speed);
      rightNeo.set(speed);
   }
   /**Sets the neo motor speeds to 0 */
   public void off(){
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
         falcon.set(speed);
      } else falcon.set(speed * maxFalconSpeed);
   }

   /**Resets the falcon encoder value to 0 */
   public void resetFalconEncoder(){
      falcon.setPosition(0);
   }

   public void setMaxNeoSpeed(double speed){
      maxNeoSpeed = speed;
   }

   public void setMaxFalconSpeed(double speed){
      maxFalconSpeed = speed;
   }
}
