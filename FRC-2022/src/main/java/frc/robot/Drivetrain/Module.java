package frc.robot.Drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.math.controller.PIDController;

public class Module {

    // module components
    private TalonFX directionMotor;
    private TalonFX driveMotor;
    private CANCoder encoder;
    private PIDController angleController;
    private double pi = Math.PI;

    // module constructor
    public Module(int directionMotor, int driveMotor, int encoder) {

        this.directionMotor = new TalonFX(directionMotor);
        this.driveMotor = new TalonFX(driveMotor);
        this.encoder = new CANCoder(encoder);
        this.angleController = new PIDController(0.4, 0, 0);
    }

    // sets encoders to zero and sets motors to brake
    public void init() {

        //encoder.setPosition(0);
        directionMotor.setNeutralMode(NeutralMode.Brake);
        driveMotor.setNeutralMode(NeutralMode.Brake);
    }

    // set motors to coast while disabled
    public void disable() {

        directionMotor.setNeutralMode(NeutralMode.Coast);
        driveMotor.setNeutralMode(NeutralMode.Coast);
    }

    // calculates fastest path to target angle relative to current angle
    public double nearestAngle(double currentAngle, double targetAngle) {

        // get direction
        double direction = (targetAngle % (2 * pi)) - (currentAngle % (2 * pi));

        // convert from -2pi to 2pi to -pi to pi
        if (Math.abs(direction) > pi) {
            direction = -(Math.signum(direction) * (2 * pi)) + direction;
        }
        return direction;
    }

    public void autoInit(double angle) {

        // make pid continuous on (-pi, pi)
        angleController.enableContinuousInput(-pi, pi);

        // get the current position reading of the direction encoder
        double currentAngle = (encoder.getAbsolutePosition() / 360) * (pi * 2);
        double setpoint = 0;

        // find the shortest path to a module setpoint angle
        double setpointAngle = nearestAngle(currentAngle, angle);
        double setpointAngleOpposite = nearestAngle(currentAngle, angle + pi);

        if (Math.abs(setpointAngle) <= Math.abs(setpointAngleOpposite)) {
            setpoint = currentAngle + setpointAngle;
        } else {
            setpoint = currentAngle + setpointAngleOpposite;
        }

        double optimizedAngle = angleController.calculate(currentAngle, setpoint);

        directionMotor.set(ControlMode.PercentOutput, optimizedAngle);
    }

    // module control
    public void drive(double speed, double angle, double speedModifier) {

        // make pid continuous on (-pi, pi)
        angleController.enableContinuousInput(-pi, pi);

        // get the current position reading of the direction encoder
        double currentAngle = (encoder.getAbsolutePosition() / 360) * (pi * 2);
        double setpoint = 0;
        

        // find the shortest path to a module setpoint angle
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
        driveMotor.set(ControlMode.PercentOutput, speed * speedModifier);
        directionMotor.set(ControlMode.PercentOutput, optimizedAngle);
    }
}
