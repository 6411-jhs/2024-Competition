package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Cannon;

public class SetCannonAngle extends Command {
   private class TargetParams {
      public double angle = 0;
      public double encoderValue = 0;
      public double gearedEncoderValue = 0;
   }
   private Cannon cannon;
   public TargetParams target;

   public SetCannonAngle(Cannon p_cannon, double p_angle){
      this.cannon = p_cannon;
      this.target = new TargetParams();
      this.target.angle = p_angle;
      this.target.gearedEncoderValue = (this.target.angle / 360);
      this.target.encoderValue = this.target.gearedEncoderValue * 100;
      addRequirements(p_cannon);
   }

   @Override
   public void execute() {
      double speed = (cannon.getEncoder() / 100) - this.target.gearedEncoderValue;
      cannon.setFalcon(speed);
   }

   @Override
   public boolean isFinished() {
      double threshold = 0.2;
      double[] range = {this.target.encoderValue - threshold, this.target.encoderValue + threshold};
      if (cannon.getEncoder() >= range[0] && cannon.getEncoder() <= range[1]) return true;
      else return false;
   }
}
