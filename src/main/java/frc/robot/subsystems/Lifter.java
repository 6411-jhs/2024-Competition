package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Lifter extends SubsystemBase {
   private WPI_VictorSPX leftMotor;
   private WPI_VictorSPX rightMotor;
   public Lifter(){
      leftMotor = new WPI_VictorSPX(Constants.CANAssignments.leftLCim);
      rightMotor = new WPI_VictorSPX(Constants.CANAssignments.rightLCim);
   }

   public void on(){
      leftMotor.set(Constants.MAXSystemSpeeds.cims);
      rightMotor.set(Constants.MAXSystemSpeeds.cims);
   }

   public void off(){
      leftMotor.set(0);
      rightMotor.set(0);
   }
}
