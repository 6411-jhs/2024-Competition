package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Cannon;

import frc.robot.Constants.MAXSystemSpeeds;

public class SetCannonAngle extends Command {
   private class TargetParams {
      public double angle = 0;
      public double encoderValue = 0;
      public double gearedEncoderValue = 0;
   }
   private Cannon cannon;
   public TargetParams target;
   
   private double minimumSpeed = 0.1;
   private double fullPowerThreshold = 2 / 3; //Should be a fraction of the target angle
   private boolean powerIsNegative = false;
   private double powerStepdown = 0;
   private double[] fullPowerEncoderThreshold = {0,0};

   public SetCannonAngle(Cannon p_cannon, double p_angle){
      cannon = p_cannon;
      target = new TargetParams();
      target.angle = p_angle;
      target.gearedEncoderValue = (target.angle / 360);
      target.encoderValue = target.gearedEncoderValue * 100;

      double thresholdDeviation = target.gearedEncoderValue * fullPowerThreshold;
      fullPowerEncoderThreshold[0] = target.gearedEncoderValue - thresholdDeviation;
      fullPowerEncoderThreshold[1] = target.gearedEncoderValue + thresholdDeviation;
      
      powerStepdown = 0.9 / thresholdDeviation;
      addRequirements(p_cannon);
   }

   @Override
   public void execute() {
      powerIsNegative = (cannon.getEncoder() > target.encoderValue);

      double calcSpeed = 0;
      if (cannon.getEncoder() < fullPowerEncoderThreshold[0] || cannon.getEncoder() > fullPowerEncoderThreshold[1]){
         calcSpeed = 1;
      } else {
         calcSpeed = powerStepdown * Math.abs((cannon.getEncoder() / 100) - target.gearedEncoderValue) + minimumSpeed;
      }

      System.out.println("Speed:  " + calcSpeed + ",  Neg:  " + powerIsNegative);
      setFalconExtended(calcSpeed);
   }

   @Override
   public boolean isFinished() {
      double threshold = 0.2;
      double[] range = {this.target.encoderValue - threshold, this.target.encoderValue + threshold};
      if (cannon.getEncoder() >= range[0] && cannon.getEncoder() <= range[1]){
         System.out.println("Command Finished!");
         return true;
      }
      else return false;
   }

   private void setFalconExtended(double speed){
      if (speed > MAXSystemSpeeds.falcon){
         cannon.setFalcon((powerIsNegative)
            ? -MAXSystemSpeeds.falcon
            : MAXSystemSpeeds.falcon
         , true);
      } else {
         cannon.setFalcon((powerIsNegative)
            ? -speed
            : speed
         , true);
      }
   }
}
