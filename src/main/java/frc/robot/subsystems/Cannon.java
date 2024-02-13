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
   public Cannon(){
      //Motor controller initializations
      leftNeo = new CANSparkMax(Constants.CANAssignments.leftCNeo, CANSparkLowLevel.MotorType.kBrushless);
      rightNeo = new CANSparkMax(Constants.CANAssignments.rightCNeo, CANSparkLowLevel.MotorType.kBrushless);
      leftNeo.setInverted(true);
      falcon = new TalonFX(Constants.CANAssignments.mainCFalcon);
      
      falcon.setNeutralMode(NeutralModeValue.Coast);
   }

   /**
    * Sets the speed of the neo motors to the specified constant
    * @param reverse Whether or not to run the neos in referse; this swaps the sign of the constant
    */
   public void on(boolean reverse){
      double speed = (reverse) ? (-Constants.SystemSpeeds.neos) : (Constants.SystemSpeeds.neos);
      leftNeo.set(speed);
      rightNeo.set(speed);
   }
   /**Sets the neo motor speeds to 0 */
   public void off(){
      leftNeo.set(0);
      rightNeo.set(0);
   }

   public double getEncoder(){
      System.out.println("Default Pos:    " + falcon.getPosition());
      System.out.println("Rotor Pos:      " + falcon.getRotorPosition());
      return 0;
   }

   /**
    * Sets the raw speed value of the neos
    * @param speed Speed to set. 1 for full forward and -1 for full reverse
    */
   public void setNeos(double speed){
      leftNeo.set(speed * Constants.SystemSpeeds.neos);
      rightNeo.set(speed * Constants.SystemSpeeds.neos);
   }

   /**
    * Sets the raw speed value of the falcon
    * @param speed Speed to set. 1 for full forward and -1 for full reverse
    */
   public void setFalcon(double speed){
      falcon.set(speed * Constants.SystemSpeeds.falcon);
   }
}
