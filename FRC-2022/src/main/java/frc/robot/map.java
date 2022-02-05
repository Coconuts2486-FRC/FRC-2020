package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.Joystick;

public class Map{
   
   // motors
   public static int rotateBL = 8;
   public static int rotateBR = 5;
   public static int rotateFL = 6;
   public static int rotateFR = 4;
   public static int driveBL = 7;
   public static int driveBR = 1;
   public static int driveFL = 3;
   public static int driveFR = 2;

   // joysticks
   public static Joystick driver = new Joystick(0); 
   public static int zeroGyro = 2;

   // encoders
   public static int encoderFL = 20;
   public static int encoderBR = 22;
   public static int encoderFR = 23;
   public static int encoderBL = 21;

   // gyro
   public static TalonSRX gyroTalon = new TalonSRX(30);
   public static PigeonIMU gyro = new PigeonIMU(gyroTalon);
}