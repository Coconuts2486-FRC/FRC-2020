package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.Joystick;

public class map{

   //motors
   public static TalonFX rotateBL = new TalonFX(8);
   public static TalonFX rotateBR = new TalonFX(5);
   public static TalonFX rotateFL = new TalonFX(6);
   public static TalonFX rotateFR = new TalonFX(4);
   public static TalonFX driveBL = new TalonFX(7);
   public static TalonFX driveBR = new TalonFX(1);
   public static TalonFX driveFL = new TalonFX(3);
   public static TalonFX driveFR = new TalonFX(2);

   //joysticks
   //public static Joystick joystickL = new Joystick(0);
   //public static Joystick joystickR = new Joystick(1);
   public static Joystick driver = new Joystick(0); 

   //encoders
   public static TalonSRX encoderFL = new TalonSRX(20);
   public static TalonSRX encoderBR = new TalonSRX(22);
   public static TalonSRX encoderFR = new TalonSRX(23);
   public static TalonSRX encoderBL = new TalonSRX(21);

   //gyro
   public static TalonSRX gyroTalon = new TalonSRX(30);
   public static PigeonIMU gyro = new PigeonIMU(gyroTalon);


   public static void driveTrainInit(){

      map.rotateBL.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
      map.encoderFL.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
      map.encoderFR.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
      map.encoderBL.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
      map.encoderBR.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
      //map.encoderFL.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
   }

    
}