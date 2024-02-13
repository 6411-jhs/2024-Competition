package frc.robot;

public final class Constants {
   public final class CANAssignments {
      public final static int frontLeftDT = 0;
      public final static int backLeftDT = 1;
      public final static int frontRightDT = 2;
      public final static int backRightDT = 3;

      public final static int leftCNeo = 4;
      public final static int rightCNeo = 5;

      public final static int mainCFalcon = 6;
   }

   public final class SystemSpeeds {
      public final static double driveTrain = 0.5;
      public final static double neos = 0.5;
      public final static double falcon = 0.5;
   }
   
   public final static int xboxPort = 0;
   public final static int joystickPort = 1;
   public final static String drivingStyle = "TriggerHybrid"; //Don't change (outtake testing)
}
