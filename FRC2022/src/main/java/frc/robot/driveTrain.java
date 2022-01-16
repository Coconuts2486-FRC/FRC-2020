package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.map;

public class driveTrain {

    //PID tuning values
    public static double kP = 0.25;


    public static void init(){

        map.encoderFL.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
        map.encoderFR.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
        map.encoderBL.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
        map.encoderBR.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);

        map.encoderFL.setSelectedSensorPosition(0);
        map.encoderBR.setSelectedSensorPosition(0);
        map.encoderFR.setSelectedSensorPosition(0);
        map.encoderBL.setSelectedSensorPosition(0);

        map.gyro.setYaw(0);
    }

    public static void telemetry(){

        SmartDashboard.putNumber("encoderFL", map.encoderFL.getSelectedSensorPosition());
        SmartDashboard.putNumber("encoderBR", map.encoderBR.getSelectedSensorPosition());
    }

    public static void strafe(){

        //get ypr
        double[] ypr_deg = new double [3];

        //get robot angle
        map.gyro.getYawPitchRoll(ypr_deg);
        double robotAngle = ypr_deg[0];

        //gets drive power
        double power = Math.sqrt(Math.pow(map.driver.getX(), 2) + Math.pow(map.driver.getY(), 2));

        //gets joystick angle
        double joystickAngle = (Math.atan2(map.driver.getY(), map.driver.getX()) + (Math.PI * 2)) % (Math.PI * 2);
        double targetAngle2 = joystickAngle + Math.PI;

        

        //field centric
        double angle = joystickAngle - (robotAngle * (Math.PI * 2)) / 360;

        //Front left module
        double currentAngleFL = (map.encoderFL.getSelectedSensorPosition() / 4096) * (Math.PI * 2);
        double target = 0;
        double errorFL = 0;


        if (Math.abs(joystickAngle - currentAngleFL) < Math.abs(targetAngle2 - currentAngleFL)){

            target = joystickAngle - (robotAngle * (Math.PI * 2)) / 360;
            errorFL = -target - currentAngleFL;
        }
        else{

            target = targetAngle2 - (robotAngle * (Math.PI * 2)) / 360;
            errorFL = -target + currentAngleFL;

        }

        
        double strafeAngleFL = kP * errorFL;
        double rotateSetpointFL = (3 * Math.PI) / 4;
        double rotateErrorFL = rotateSetpointFL - currentAngleFL;
        double rotateAngleFL = kP * rotateErrorFL;

        //Back right module
        double currentAngleBR = (map.encoderBR.getSelectedSensorPosition() / 4096) * (Math.PI * 2);
        double errorBR = -angle - currentAngleBR;
        double strafeAngleBR = kP * errorBR;
        double rotateSetpointBR = (3 * Math.PI) / 4;
        double rotateErrorBR = rotateSetpointBR - currentAngleBR;
        double rotateAngleBR = kP * rotateErrorBR;

        //Back left module
        double currentAngleBL = (map.encoderBL.getSelectedSensorPosition() / 4096) * (Math.PI * 2);
        double errorBL = -angle - currentAngleBL;
        double strafeAngleBL = kP * errorBL;
        double rotateSetpointBL = (Math.PI) / 4;
        double rotateErrorBL = rotateSetpointBL - currentAngleBL;
        double rotateAngleBL = kP * rotateErrorBL;

        //Front right module
        double currentAngleFR = (map.encoderFR.getSelectedSensorPosition() / 4096) * (Math.PI * 2);
        double errorFR = -angle - currentAngleFR;
        double strafeAngleFR = kP * errorFR;
        double rotateSetpointFR = (Math.PI) / 4;
        double rotateErrorFR = rotateSetpointFR - currentAngleFR;
        double rotateAngleFR = kP * rotateErrorFR;
        
        //closest angle
        



        SmartDashboard.putNumber("angleFL", currentAngleFL);
        SmartDashboard.putNumber("errorFL", errorFL);
        SmartDashboard.putNumber("angleBR", currentAngleBR);
        SmartDashboard.putNumber("errorBR", errorBR);
        SmartDashboard.putNumber("angleFR", currentAngleFR);
        SmartDashboard.putNumber("errorFR", errorFR);
        SmartDashboard.putNumber("angleBL", currentAngleBL);
        SmartDashboard.putNumber("errorBL", errorBL);
        SmartDashboard.putNumber("robot angle", robotAngle);
        SmartDashboard.putNumber("Joystick Angle", joystickAngle);
        SmartDashboard.putNumber("target", target);
        SmartDashboard.putNumber("targetAngle2", targetAngle2);
        

        //rotate
        double rotateFL = 0;
        double rotateBR = 0;
        double rotateBL = 0;
        double rotateFR = 0;

        double strafeFL = 0;
        double strafeBR = 0;
        double strafeBL = 0;
        double strafeFR = 0;

        if (map.driver.getTwist() < -0.1 || map.driver.getTwist() > 0.1){

            rotateFL = rotateAngleFL;
            rotateBR = rotateAngleBR;
            rotateBL = rotateAngleBL;
            rotateFR = rotateAngleFR;

            strafeFL = 0;
            strafeFR = 0;
            strafeBL = 0;
            strafeBR = 0;
        }

        else {

            rotateFL = 0;
            rotateBR = 0;
            rotateBL = 0;
            rotateFR = 0;

            strafeFL = strafeAngleFL;
        }

        SmartDashboard.putNumber("rotate output", rotateFL);
        SmartDashboard.putNumber("strafe output", strafeFL);

        //driver power
        //map.driveBL.set(ControlMode.PercentOutput, power * 0.2 - map.driver.getTwist());
        //map.driveBR.set(ControlMode.PercentOutput, power * 0.2 - map.driver.getTwist());
        //map.driveFL.set(ControlMode.PercentOutput, power * 0.2 + map.driver.getTwist());
        //map.driveFR.set(ControlMode.PercentOutput, power * 0.2 + map.driver.getTwist());

        //strafe angle
        map.rotateFL.set(ControlMode.PercentOutput, Math.sqrt((strafeFL * strafeFL) + (rotateFL * rotateFL)));
        //map.rotateBR.set(ControlMode.PercentOutput, Math.sqrt(Math.pow(strafeAngleBR, 2) + Math.pow(rotateBR, 2)));
        //map.rotateFR.set(ControlMode.PercentOutput, Math.sqrt(Math.pow(strafeAngleFR, 2) + Math.pow(rotateFR, 2)));
        //map.rotateBL.set(ControlMode.PercentOutput, Math.sqrt(Math.pow(strafeAngleBL, 2) + Math.pow(rotateBL, 2)));

        SmartDashboard.putNumber("final", Math.sqrt(Math.pow(strafeAngleFL, 2) + Math.pow(rotateFL, 2)));
        
    }

    public static void rotate(){

        
    }
    
}
