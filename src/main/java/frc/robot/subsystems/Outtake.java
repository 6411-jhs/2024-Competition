package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel;

import frc.robot.Constants;

public class Outtake extends SubsystemBase {
   //Motor controller definitions
   private CANSparkMax leftMotor; 
   private CANSparkMax rightMotor; 

   public Outtake(){
      //Motor controller initializations
      leftMotor = new CANSparkMax(Constants.CANAssignments.leftOT, CANSparkLowLevel.MotorType.kBrushless);
      rightMotor = new CANSparkMax(Constants.CANAssignments.rightOT, CANSparkLowLevel.MotorType.kBrushless);
   }

   /**Sets the outtake motor speeds to the set constant speed */
   public void on(){
      leftMotor.set(Constants.SystemSpeeds.outtake);
      rightMotor.set(Constants.SystemSpeeds.outtake);
   }
   /**Sets the outtake motor speeds to 0 */
   public void off(){
      leftMotor.set(0);
      rightMotor.set(0);
   }
   /**Sets the outake motor speeds to the negative of the set constant speed */
   public void reverse(){
      leftMotor.set(-Constants.SystemSpeeds.outtake);
      rightMotor.set(-Constants.SystemSpeeds.outtake);
   }
   /**
    * Sets the outtake motors to a specific speed
    * @param speed The speed to set the motors. Has to be between 1 (Forwards) and -1 (Reverse)
    */
   public void set(double speed){
      leftMotor.set(speed);
      rightMotor.set(speed);
   }
}
