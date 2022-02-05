package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.controller.PIDController;

public class Module {

    private TalonFX directionMotor;
    private TalonFX driveMotor;
    private TalonSRX encoder;
    private PIDController angleController;
    private double pi = Math.PI;

    public Module(int directionMotor, int driveMotor, int encoder){

        this.directionMotor = new TalonFX(directionMotor);
        this.driveMotor = new TalonFX(driveMotor);
        this.encoder = new TalonSRX(encoder);
        this.angleController = new PIDController(0.4, 0, 0);
    }

    // sets encoders to zero and sets motors to brake
    public void init() {

        encoder.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
        encoder.setSelectedSensorPosition(0);
        directionMotor.setNeutralMode(NeutralMode.Brake);
        driveMotor.setNeutralMode(NeutralMode.Brake);
    }

    // set motors to coast while disabled
    public void disable(){
      
        directionMotor.setNeutralMode(NeutralMode.Coast);
        driveMotor.setNeutralMode(NeutralMode.Coast);
    }

    // calculates nearest angle
    public double nearestAngle(double currentAngle, double targetAngle) {
      
        // get direction
        double direction = (targetAngle % (2 * pi)) - (currentAngle % (2 * pi));

        // convert from -2pi to 2pi to -pi to pi
        if (Math.abs(direction) > pi) {
            direction = -(Math.signum(direction) * (2 * pi)) + direction;
        }
        return direction;
    }

    //module control
    public void drive(double speed, double angle){

        // make pid continuous on (-pi, pi)
        angleController.enableContinuousInput(-pi, pi);

        // get the current reading of the direction encoder
        double currentAngle = (encoder.getSelectedSensorPosition() / 4096) * (pi * 2);
        double setpoint = 0;

        // find the shortest path to a set module angle
        double setpointAngle = nearestAngle(currentAngle, angle);
        double setpointAngleOpposite = nearestAngle(currentAngle, angle + pi);

        if (Math.abs(setpointAngle) <= Math.abs(setpointAngleOpposite)) {
            setpoint = currentAngle + setpointAngle;
        } else {
            setpoint = currentAngle + setpointAngleOpposite;
            speed *= -1;
        }

        double optimizedAngle = angleController.calculate(currentAngle, setpoint);

        // set the drive speed and direction of the module
        driveMotor.set(ControlMode.PercentOutput, speed);
        directionMotor.set(ControlMode.PercentOutput, optimizedAngle);
    }
    
}
