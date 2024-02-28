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

      public final static int leftLCim = 7;
      public final static int rightLCim = 8;
   }

   public final class PWMAssignments {
      public final static int mainCServo = 0;
   }

   public final class DefaultSystemSpeeds {
      public final static double driveTrain = 1;
      public final static double neos = 1;
      public final static double falcon = 1;
      public final static double cims = 1;
   }
   
   public final class UserControls {
      public final static int xboxPort = 0;
      public final static int joystickPort = 1;
      public final static String defaultDrivingStyle = "TriggerHybrid";
   }

   public final class Other {
      public final static int teleopDuration = 160;
      public final static int autoDuration = 5;
   }
}
