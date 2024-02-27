package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Servo;

public class Lifter extends SubsystemBase {
   private WPI_VictorSPX leftMotor;
   private WPI_VictorSPX rightMotor;
   private Servo servo;
   public Lifter(){
      leftMotor = new WPI_VictorSPX(Constants.CANAssignments.leftLCim);
      rightMotor = new WPI_VictorSPX(Constants.CANAssignments.rightLCim);
      servo = new Servo(0);
   }

   public void on(){
      leftMotor.set(Constants.DefaultSystemSpeeds.cims);
      rightMotor.set(Constants.DefaultSystemSpeeds.cims);
   }

   public void off(){
      leftMotor.set(0);
      rightMotor.set(0);
   }

   /**
    * Sets the servo to a specific degree measurment
    * @param degree Any degree ranging from 0-180 (0 being start pos, 180 being 180 degrees)
    */
   public void setServo(double degree){
      double calcDegree = (150 / 180) * degree;
      servo.setAngle(30 + calcDegree);
   }
}
