package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveDrive {

    public static double kP = 0.25;

    public static void drive (double x1, double y1, double x2) {

        //get ypr
        double[] ypr_deg = new double [3];

        //get robot angle
        map.gyro.getYawPitchRoll(ypr_deg);
        double robotAngle = ypr_deg[0] * Math.PI / 180;


        double L = 17.5;
        double W = 17.5;

        double r = Math.sqrt ((L * L) + (W * W));
        //y1 *= -1;

        double x3 = y1 * Math.cos(robotAngle) + x1 * Math.sin(robotAngle);
        x1 = -y1 * Math.sin(robotAngle) + x1 * Math.cos(robotAngle);
        y1 = x3;
    
        double a = x1 - x2 * (L / r);
        double b = x1 + x2 * (L / r);
        double c = y1 - x2 * (W / r);
        double d = y1 + x2 * (W / r);

        double backRightSpeed = Math.sqrt ((a * a) + (d * d));
        double backLeftSpeed = Math.sqrt ((a * a) + (c * c));
        double frontRightSpeed = Math.sqrt ((b * b) + (d * d));
        double frontLeftSpeed = Math.sqrt ((b * b) + (c * c));

        double backRightAngle = Math.atan2 (a, d);
        double backLeftAngle = Math.atan2 (a, c);
        double frontRightAngle = Math.atan2 (b, d);
        double frontLeftAngle = Math.atan2 (b, c);

        double targetAngle2 = backRightAngle + Math.PI;
        double targetAngle3 = backRightAngle - Math.PI;
        double setpointBR = 0;
        double currentAngleBR = (map.encoderBR.getSelectedSensorPosition() / 4096) * (Math.PI * 2);
        

        if (Math.abs(backRightAngle - currentAngleBR) > Math.abs(targetAngle2 - currentAngleBR) || Math.abs(backRightAngle - currentAngleBR) > Math.abs(targetAngle3 - currentAngleBR)){
            if (Math.abs(backRightAngle - currentAngleBR) > Math.abs(targetAngle2 - currentAngleBR)){
                setpointBR = targetAngle2;
            }

            else if (Math.abs(backRightAngle - currentAngleBR) > Math.abs(targetAngle3- currentAngleBR)){
                setpointBR = targetAngle3;
            }
        } else if (Math.abs(backRightAngle - currentAngleBR) == Math.abs(targetAngle2 - currentAngleBR) || Math.abs(backRightAngle - currentAngleBR) == Math.abs(targetAngle3 - currentAngleBR)){
            setpointBR = backRightAngle;
            //backRightSpeed = backRightSpeed * -1;
        }
        else {
            setpointBR = backRightAngle;
        }

        double errorBR = setpointBR - currentAngleBR;
        double strafeAngleBR = kP * errorBR;

        double currentAngleBL = (map.encoderBL.getSelectedSensorPosition() / 4096) * (Math.PI * 2);
        double targetAngle4 = backLeftAngle + Math.PI;
        double targetAngle5 = backLeftAngle - Math.PI;
        double setpointBL = 0;
        

        if (Math.abs(backLeftAngle - currentAngleBL) > Math.abs(targetAngle4 - currentAngleBL) || Math.abs(backLeftAngle - currentAngleBL) > Math.abs(targetAngle5 - currentAngleBL)){
            if (Math.abs(backLeftAngle - currentAngleBL) > Math.abs(targetAngle4 - currentAngleBL)){
                setpointBL = targetAngle4;
            }

            else if (Math.abs(backLeftAngle - currentAngleBL) > Math.abs(targetAngle5- currentAngleBL)){
                setpointBL = targetAngle5;
            }
        } else if (Math.abs(backLeftAngle - currentAngleBL) == Math.abs(targetAngle4 - currentAngleBL) || Math.abs(backLeftAngle - currentAngleBL) == Math.abs(targetAngle5 - currentAngleBL)){
            setpointBL = backLeftAngle;
            //backLeftSpeed = backLeftSpeed * -1;
        }
        else {
            setpointBL = backLeftAngle;
        }
        double errorBL = backLeftAngle - currentAngleBL;
        double strafeAngleBL = kP * errorBL;

        double currentAngleFR = (map.encoderFR.getSelectedSensorPosition() / 4096) * (Math.PI * 2);
        double targetAngle6 = frontRightAngle + Math.PI;
        double targetAngle7 = frontRightAngle - Math.PI;
        double setpointFR = 0;

        if (Math.abs(frontRightAngle - currentAngleFR) > Math.abs(targetAngle6 - currentAngleFR) || Math.abs(frontRightAngle - currentAngleFR) > Math.abs(targetAngle7 - currentAngleFR)){
            if (Math.abs(frontRightAngle - currentAngleFR) > Math.abs(targetAngle6 - currentAngleFR)){
                setpointFR = targetAngle6;
            }

            else if (Math.abs(frontRightAngle - currentAngleFR) > Math.abs(targetAngle7- currentAngleFR)){
                setpointFR = targetAngle7;
            }
        } else if (Math.abs(frontRightAngle - currentAngleFR) == Math.abs(targetAngle6 - currentAngleFR) || Math.abs(frontRightAngle - currentAngleFR) == Math.abs(targetAngle7 - currentAngleFR)){
            setpointFR = frontRightAngle;
            //frontRightSpeed = frontRightSpeed * -1;
        }
        else {
            setpointFR = frontRightAngle;
        }
        double errorFR = frontRightAngle - currentAngleFR;
        double strafeAngleFR = kP * errorFR;

        double currentAngleFL = (map.encoderFL.getSelectedSensorPosition() / 4096) * (Math.PI * 2);
        double targetAngle8 = frontLeftAngle + Math.PI;
        double targetAngle9 = frontLeftAngle - Math.PI;
        double setpointFL = 0;

        if (Math.abs(frontLeftAngle - currentAngleFL) > Math.abs(targetAngle8 - currentAngleFL) || Math.abs(frontLeftAngle - currentAngleFL) > Math.abs(targetAngle9 - currentAngleFL)){
            if (Math.abs(frontLeftAngle - currentAngleFL) > Math.abs(targetAngle8 - currentAngleFL)){
                setpointFL = targetAngle8;
            }

            else if (Math.abs(frontLeftAngle - currentAngleFL) > Math.abs(targetAngle9- currentAngleFL)){
                setpointFL = targetAngle9;
            }
        } else if (Math.abs(frontLeftAngle - currentAngleFL) == Math.abs(targetAngle8 - currentAngleFL) || Math.abs(frontLeftAngle - currentAngleFL) == Math.abs(targetAngle9 - currentAngleFL)){
            setpointFL = frontLeftAngle;
            //frontLeftSpeed = frontLeftSpeed * -1;
        }
        else {
            setpointFL = frontLeftAngle;
        }
        double errorFL = frontLeftAngle - currentAngleFL;
        double strafeAngleFL = kP * errorFL;

        //map.driveBR.set(ControlMode.PercentOutput, backRightSpeed * 0.2);
        //map.driveBL.set(ControlMode.PercentOutput, backLeftSpeed * 0.2);
        //map.driveFR.set(ControlMode.PercentOutput, frontRightSpeed * 0.2);
        //map.driveFL.set(ControlMode.PercentOutput, frontLeftSpeed * 0.2);

        map.rotateBR.set(ControlMode.PercentOutput, strafeAngleBR);
        //map.rotateBL.set(ControlMode.PercentOutput, strafeAngleBL);
        //map.rotateFR.set(ControlMode.PercentOutput, strafeAngleFR);
        //map.rotateFL.set(ControlMode.PercentOutput, strafeAngleFL);

        SmartDashboard.putNumber("gyro angle", robotAngle);
        SmartDashboard.putNumber("x1", x1);
        SmartDashboard.putNumber("y1", y1);
        SmartDashboard.putNumber("x2", x2);
        SmartDashboard.putNumber("backRightSpeed", backRightSpeed);
        SmartDashboard.putNumber("backLeftSpeed", backLeftSpeed);
        SmartDashboard.putNumber("frontRightSpeed", frontRightSpeed);
        SmartDashboard.putNumber("frontLeftSpeed", frontLeftSpeed);
        SmartDashboard.putNumber("frontLeftAngle", frontLeftAngle);
        SmartDashboard.putNumber("frontRightAngle", frontRightAngle);
        SmartDashboard.putNumber("backLeftAngle", backLeftAngle);
        SmartDashboard.putNumber("backRightAngle", backRightAngle);
        SmartDashboard.putNumber("a", a);
        SmartDashboard.putNumber("b", b);
        SmartDashboard.putNumber("c", c);
        SmartDashboard.putNumber("d", d);
        SmartDashboard.putNumber("currentAngleBR", currentAngleBR);
        SmartDashboard.putNumber("currentAngleBL", currentAngleBL);
        SmartDashboard.putNumber("currentAngleFR", currentAngleFR);
        SmartDashboard.putNumber("currentAngleFL", currentAngleFL);
        SmartDashboard.putNumber("errorFL", errorFL);
        SmartDashboard.putNumber("errorFR", errorFR);
        SmartDashboard.putNumber("errorBL", errorBL);
        SmartDashboard.putNumber("errorBR", errorBR);
        SmartDashboard.putNumber("targetAngle2", targetAngle2);
        SmartDashboard.putNumber("targetAngle3", targetAngle3);
        
    }

    public static void drive(double speed, double angle){
        

    }
    
}
