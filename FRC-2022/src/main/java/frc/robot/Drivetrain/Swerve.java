package frc.robot.Drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.Vision.Track;

public class Swerve {

    // swerve modules
    private Module backRight;
    private Module backLeft;
    private Module frontRight;
    private Module frontLeft;

    // swerve variables
    double errorAngle = 0.0;
    double setpoint = 0.0;

    // swerve constructor
    public Swerve(Module backRight, Module backLeft, Module frontRight, Module frontLeft) {

        this.backRight = backRight;
        this.backLeft = backLeft;
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
    }

    // initialize drivetrain
    public void init() {

        RobotMap.gyro.setYaw(0);
        backRight.init();
        backLeft.init();
        frontRight.init();
        frontLeft.init();
    }

    // disables brake mode
    public void disabled() {

        backRight.disable();
        backLeft.disable();
        frontLeft.disable();
        frontRight.disable();

    }

    // testing method for absolute encoder offsets
    public void autoInit() {

        backLeft.autoInit(49 * Math.PI / 30);
        backRight.autoInit(4 * Math.PI / 45);
        frontLeft.autoInit(17 * Math.PI / 15);
        frontRight.autoInit(79 * Math.PI / 60);
    }

    // drive method
    public void drive(double x, double y, double Twist) {

        // initialize gyro
        double[] ypr_deg = new double[3];

        // get robot angle
        RobotMap.gyro.getYawPitchRoll(ypr_deg);
        double robotAngle = ypr_deg[0] * Math.PI / 180;

        // dimensions of wheelbase and trackwidth
        double L = 17.5;
        double W = 17.5;
        double r = Math.sqrt((L * L) + (W * W));

        // field centric adjustment
        if (RobotMap.operator.getRawButton(RobotMap.scoreLow) || RobotMap.operator.getRawButton(RobotMap.scoreHigh)) {

            x = x * 1;
            y = y * -1;
        } else {

            double x0 = y * Math.cos(robotAngle) + x * Math.sin(robotAngle);
            x = -y * Math.sin(robotAngle) + x * Math.cos(robotAngle);
            y = x0;
        }

        // chassis vector transformed to wheel vectors
        double a = x - Twist * (L / r);
        double b = x + Twist * (L / r);
        double c = y - Twist * (W / r);
        double d = y + Twist * (W / r);

        // calculated module speeds based on wheel vectors
        double backRightSpeed = Math.sqrt((a * a) + (d * d));
        double backLeftSpeed = Math.sqrt((a * a) + (c * c));
        double frontRightSpeed = Math.sqrt((b * b) + (d * d));
        double frontLeftSpeed = Math.sqrt((b * b) + (c * c));

        // calculated module angles based on wheel vectors
        double backRightAngle = Math.atan2(a, d);
        double backLeftAngle = Math.atan2(a, c);
        double frontRightAngle = Math.atan2(b, d);
        double frontLeftAngle = Math.atan2(b, c);

        // set speed and angle each module operates at
        backRight.drive(backRightSpeed, backRightAngle + (4 * Math.PI / 45));
        backLeft.drive(backLeftSpeed, backLeftAngle + (49 * Math.PI / 30));
        frontRight.drive(frontRightSpeed, frontRightAngle + (79 * Math.PI / 60));
        frontLeft.drive(frontLeftSpeed, frontLeftAngle + (17 * Math.PI / 15));

    }

    // account for gyro drift by rezeroing gyro
    public void realignToField(int button) {

        if (RobotMap.driverElite.getRawButton(button)) {

            RobotMap.gyro.setYaw(0);
        }
    }

    // returns how much the swerve needs to turn to drive straight
    public double yawCorrection(double setpoint) {

        // PID tuning
        double kP = 0.7;
        
        // initialize gyro
        double[] ypr_deg = new double[3];

        // get robot angle
        RobotMap.gyro.getYawPitchRoll(ypr_deg);
        double robotAngle0 = ypr_deg[0] * Math.PI / 180;

        // get target angle
        double targetAngle = setpoint;

        // compute how much the swerve needs to turn to be aligned to the correct angle
        if (robotAngle0 > 0){

            errorAngle = (targetAngle + robotAngle0) % (2 * Math.PI);
        }
        if (robotAngle0 < 0){

            errorAngle = (targetAngle + robotAngle0) % (2 * Math.PI);
        }

        double correction = errorAngle * kP;

        return correction;
    }

    // field, goal, and ball centric control
    public void run(double x, double y, double z, boolean track) {

        // drive inputs
        double twist = 0.0;
        double twistAdjustment = Track.adjustYaw(track);
        double twistDeadband = 0.4;
        double directionDeadband = 0.2;

        // twist deadband and goal centric assist
        if (Math.abs(z) < twistDeadband || track) {

            twist = 0.0;
        } else {

            twist = (1 / (1 - twistDeadband)) * (z + -Math.signum(z) * twistDeadband);
        }

        // x deadband
        if (Math.abs(x) < directionDeadband) {

            x = 0.0;
        } else {

            x = (1 / (1 - directionDeadband)) * (x + -Math.signum(x) * directionDeadband);
        }

        // y deadband
        if (Math.abs(y) < directionDeadband) {

            y = 0.0;
        } else {

            y = (1 / (1 - directionDeadband)) * (y + -Math.signum(y) * directionDeadband);
        }

        // drive staright assist
        if (twist == 0 && track == false) {
            twistAdjustment = yawCorrection(0);
        } else {

            twistAdjustment = Track.adjustYaw(track);
            RobotMap.gyro.setYaw(0);
        }

        // distance assist for firing at 6-7 feet for low and high port
        if (RobotMap.operator.getRawButton(RobotMap.scoreLow) || RobotMap.operator.getRawButton(RobotMap.scoreHigh)) {

            y = 0.0;
        } else {
            y = y * 1;
        }

        // drive inputs
        drive(x, y + Track.adjustPosition(), twist + twistAdjustment);
        realignToField(RobotMap.eliteZeroGyro);
    }

    // same run function but used in auto to reduce conflict
    public void autoRun(double x, double y, double z, boolean track) {

        // drive inputs
        double twist = 0.0;
        double twistAdjustment = Track.adjustYaw(track);
        double twistDeadband = 0.4;
        double directionDeadband = 0.2;

        // twist deadband and goal centric assist
        if (Math.abs(z) < twistDeadband || track) {

            twist = 0.0;
        } else {

            twist = (1 / (1 - twistDeadband)) * (z + -Math.signum(z) * twistDeadband);
        }

        // x deadband
        if (Math.abs(x) < directionDeadband) {

            x = 0.0;
        } else {

            x = (1 / (1 - directionDeadband)) * (x + -Math.signum(x) * directionDeadband);
        }

        // y deadband
        if (Math.abs(y) < directionDeadband) {

            y = 0.0;
        } else {

            y = (1 / (1 - directionDeadband)) * (y + -Math.signum(y) * directionDeadband);
        }

        // drive staright assist
        if (twist == 0 && track == false) {
            twistAdjustment = yawCorrection(0);
        } else {

            twistAdjustment = Track.adjustYaw(track);
            RobotMap.gyro.setYaw(0);
        }

        // distance assist for firing at 6-7 feet for low and high port
        if (RobotMap.operator.getRawButton(RobotMap.scoreLow) || RobotMap.operator.getRawButton(RobotMap.scoreHigh)) {

            y = 0.0;
        } else {
            y = y * 1;
        }

        // drive inputs
        drive(x, y + Track.adjustPosition(), twist + twistAdjustment);
        realignToField(RobotMap.eliteZeroGyro);
    }
}
