package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.math.controller.PIDController;

public class SwerveDrive {

    public static double kP = 0.25;
    public static PIDController pid = new PIDController(kP, 0, 0);

    public static void init() {

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

    public static double closestAngle(double currentAngle, double targetAngle) {
        // get direction
        double direction = (targetAngle % (2 * Math.PI)) - (currentAngle % (2 * Math.PI));

        // convert from -2pi to 2pi to -pi to pi
        if (Math.abs(direction) > Math.PI) {
            direction = -(Math.signum(direction) * (2 * Math.PI)) + direction;
        }
        return direction;

    }

    public static void drive(double x1, double y1, double x2) {

        pid.enableContinuousInput(-Math.PI, Math.PI);

        // get ypr
        double[] ypr_deg = new double[3];

        // get robot angle
        map.gyro.getYawPitchRoll(ypr_deg);
        double robotAngle = ypr_deg[0] * Math.PI / 180;

        double L = 17.5;
        double W = 17.5;
        double r = Math.sqrt((L * L) + (W * W));

        double x3 = y1 * Math.cos(robotAngle) + x1 * Math.sin(robotAngle);
        x1 = -y1 * Math.sin(robotAngle) + x1 * Math.cos(robotAngle);
        y1 = x3;

        double a = x1 - x2 * (L / r);
        double b = x1 + x2 * (L / r);
        double c = y1 - x2 * (W / r);
        double d = y1 + x2 * (W / r);

        double backRightSpeed = Math.sqrt((a * a) + (d * d));
        double backLeftSpeed = Math.sqrt((a * a) + (c * c));
        double frontRightSpeed = Math.sqrt((b * b) + (d * d));
        double frontLeftSpeed = Math.sqrt((b * b) + (c * c));

        double backRightAngle = Math.atan2(a, d);
        double backLeftAngle = Math.atan2(a, c);
        double frontRightAngle = Math.atan2(b, d);
        double frontLeftAngle = Math.atan2(b, c);

        double currentAngleBR = (map.encoderBR.getSelectedSensorPosition() / 4096) * (Math.PI * 2);
        double setpointBR = 0;

        double setpointAngleBR = closestAngle(currentAngleBR, backRightAngle);
        double setpointAngleFlippedBR = closestAngle(currentAngleBR, backRightAngle + Math.PI);

        if (Math.abs(setpointAngleBR) <= Math.abs(setpointAngleFlippedBR)) {
            setpointBR = currentAngleBR + setpointAngleBR;
        } else {
            setpointBR = currentAngleBR + setpointAngleFlippedBR;
            backRightSpeed *= -1;
        }

        double currentAngleBL = (map.encoderBL.getSelectedSensorPosition() / 4096) * (Math.PI * 2);

        double setpointBL = 0;

        double setpointAngleBL = closestAngle(currentAngleBL, backLeftAngle);
        double setpointAngleFlippedBL = closestAngle(currentAngleBL, backLeftAngle + Math.PI);

        if (Math.abs(setpointAngleBL) <= Math.abs(setpointAngleFlippedBL)) {
            setpointBL = currentAngleBL + setpointAngleBL;
        } else {
            setpointBL = currentAngleBL + setpointAngleFlippedBL;
            backLeftSpeed *= -1;
        }

        double currentAngleFR = (map.encoderFR.getSelectedSensorPosition() / 4096) * (Math.PI * 2);

        double setpointFR = 0;

        double setpointAngleFR = closestAngle(currentAngleFR, frontRightAngle);
        double setpointAngleFlippedFR = closestAngle(currentAngleFR, frontRightAngle + Math.PI);

        if (Math.abs(setpointAngleFR) <= Math.abs(setpointAngleFlippedFR)) {
            setpointFR = currentAngleFR + setpointAngleFR;
        } else {
            setpointFR = currentAngleFR + setpointAngleFlippedFR;
            frontRightSpeed *= -1;
        }

        double currentAngleFL = (map.encoderFL.getSelectedSensorPosition() / 4096) * (Math.PI * 2);
        double setpointFL = 0;

        double setpointAngleFL = closestAngle(currentAngleFL, frontLeftAngle);
        double setpointAngleFlippedFL = closestAngle(currentAngleFL, frontLeftAngle + Math.PI);

        if (Math.abs(setpointAngleFL) <= Math.abs(setpointAngleFlippedFL)) {
            setpointFL = currentAngleFL + setpointAngleFL;
        } else {
            setpointFL = currentAngleFL + setpointAngleFlippedFL;
            frontLeftSpeed *= -1;
        }

        map.driveBR.set(ControlMode.PercentOutput, backRightSpeed * 1);
        map.driveBL.set(ControlMode.PercentOutput, backLeftSpeed * 1);
        map.driveFR.set(ControlMode.PercentOutput, frontRightSpeed * 1);
        map.driveFL.set(ControlMode.PercentOutput, frontLeftSpeed * 1);

        map.rotateBR.set(ControlMode.PercentOutput, pid.calculate(currentAngleBR, setpointBR));
        map.rotateBL.set(ControlMode.PercentOutput, pid.calculate(currentAngleBL, setpointBL));
        map.rotateFR.set(ControlMode.PercentOutput, pid.calculate(currentAngleFR, setpointFR));
        map.rotateFL.set(ControlMode.PercentOutput, pid.calculate(currentAngleFL, setpointFL));
    }

}
