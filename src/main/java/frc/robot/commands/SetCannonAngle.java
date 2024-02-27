package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Cannon;

public class SetCannonAngle extends Command {
   //Target parameters and cannon subsystem
   private class TargetParams {
      public double angle = 0;
      public double encoderValue = 0;
      public double gearedEncoderValue = 0;
   }
   private Cannon cannon;
   public TargetParams target;
   //*Local parameters
   //What the minimum speed of the falcon should be for minimum movement
   private double minimumSpeed = 0.1;
   //The decimal percentage of when the falcon should stop using full speed to move to angle
   //At what percent of the angle should the falcon stop using full power
   private double fullPowerThreshold = 0.66;
   //The encoder value range in which the command is considered done and it in target
   //When the motor position reaches (E-target ± acceptanceRange) command ends
   private double acceptanceRange = 0.2;
   //Rest of these values are calculated and do not need to be set
   private boolean powerIsNegative = false;
   private double powerStepdown = 0;
   private double fullPowerThresholdAdjusted = 0;
   private double[] fullPowerEncoderThreshold = {0,0};

   public SetCannonAngle(Cannon p_cannon, double p_angle){
      //Saves the cannon subsystem
      cannon = p_cannon;
      //Defines the target parameter object and saves/calculates the target encoder values
      target = new TargetParams();
      target.angle = p_angle;
      target.gearedEncoderValue = (target.angle / 360);
      target.encoderValue = target.gearedEncoderValue * 100;

      //Calculates the encoder value of the provieded threshold parameter (When the encoder reaches what value should it stop using full power)
      fullPowerThresholdAdjusted = Math.abs(fullPowerThreshold - 1);
      double thresholdDeviation = target.gearedEncoderValue * fullPowerThresholdAdjusted;
      fullPowerEncoderThreshold[0] = (target.gearedEncoderValue - thresholdDeviation) * 100;
      fullPowerEncoderThreshold[1] = (target.gearedEncoderValue + thresholdDeviation) * 100;
      
      //Creates the slope of the power curve when the falcon stops using full power (this is a negative linear power curve)
      powerStepdown = (1 - minimumSpeed) / thresholdDeviation;
      addRequirements(p_cannon);
   }

   @Override
   public void execute() {
      //Decides whether or not the falcon needs to go in reverse
      powerIsNegative = (cannon.getEncoder() > target.encoderValue);

      //Calculates what speed the falcon should be moving at based on its current position
      double calcSpeed = 0;
      if (cannon.getEncoder() < fullPowerEncoderThreshold[0] || cannon.getEncoder() > fullPowerEncoderThreshold[1]){
         //If it's in full power zone
         calcSpeed = 1;
      } else {
         //If it's not in full power zone; calculate based on predecided power curve
         calcSpeed = powerStepdown * Math.abs((cannon.getEncoder() / 100) - target.gearedEncoderValue) + minimumSpeed;
      }
      //Set the falcon speed
      setFalconExtended(calcSpeed);
   }

   @Override
   public boolean isFinished() {
      //Checks to see if the encoder value is within the acceptance range; if it is return true and end the command
      double[] range = {this.target.encoderValue - acceptanceRange, this.target.encoderValue + acceptanceRange};
      if (cannon.getEncoder() >= range[0] && cannon.getEncoder() <= range[1]){
         System.out.println("Command Finished!");
         return true;
      }
      else return false;
   }

   @Override
   public void end(boolean interrupted) {
      //When the command ends set the motor speed to 0
      cannon.setFalcon(0, true);
   }

   /**
    * Sets the falcon speed with a few safety and logic measures in place
    * @param speed The desired speed to set the falcon (max speed constant not in condideration)
    */
   private void setFalconExtended(double speed){
      if (speed > cannon.maxFalconSpeed){
         //If the specified speed is above the max speed constant, use the max speed constant as its full power value
         cannon.setFalcon((powerIsNegative)
            ? -cannon.maxFalconSpeed
            : cannon.maxFalconSpeed
         , true);
      } else {
         //If the specified speed is below or equal to the max speed constant, use the specified speed
         //(This means that the falcon position is out of the full power threshold zone)
         cannon.setFalcon((powerIsNegative)
            ? -speed
            : speed
         , true);
      }
   }
}
