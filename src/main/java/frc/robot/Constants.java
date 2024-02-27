package frc.robot;

public final class Constants {
   public final class CANAssignments {
      public final static int frontLeftDT = 2;
      public final static int backLeftDT = 3;
      public final static int frontRightDT = 1;
      public final static int backRightDT = 0;

      public final static int leftCNeo = 4;
      public final static int rightCNeo = 5;

      public final static int mainCFalcon = 6;
   }

   public final class DefaultSystemSpeeds {
      public final static double driveTrain = 1;
      public final static double neos = 1;
      public final static double falcon = 0.5;
   }
   
   public final class UserControls {
      public final static int xboxPort = 0;
      public final static int joystickPort = 1;
      public final static String defaultDrivingStyle = "TriggerHybrid";
   }
}
