package frc.robot.Drivetrain;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.RobotMap;
import frc.robot.Vision.Pixy;
import frc.robot.Vision.Track;

public class Swerve {

    // swerve components
    public static double speedMultiplier = 0.5;

    // swerve modules
    private Module backRight;
    private Module backLeft;
    private Module frontRight;
    private Module frontLeft;

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

        if (RobotMap.driver.getRawButton(RobotMap.cutSpeed)) {

            speedMultiplier = 0.3;
        } else {

            speedMultiplier = 1;
        }

        // set speed and angle each module operates at
        backRight.drive(backRightSpeed, backRightAngle, speedMultiplier);
        backLeft.drive(backLeftSpeed, backLeftAngle, speedMultiplier);
        frontRight.drive(frontRightSpeed, frontRightAngle, speedMultiplier);
        frontLeft.drive(frontLeftSpeed, frontLeftAngle, speedMultiplier);

    }

    // account for gyro drift by rezeroing gyro
    public void realignToField(int button) {

        if (RobotMap.driverElite.getRawButton(button)) {

            RobotMap.gyro.setYaw(0);
        }
    }

    // field, goal, and ball centric control
    public void run() {

        // drive inputs
        double twist = 0.0;
        double y = 0.0;
        double x = RobotMap.driverElite.getRawAxis(4);
        double twistAdjustment = Track.adjustYaw();

        // deadband
        /*if (RobotMap.driverElite.getRawAxis(0) < 0.25 || RobotMap.driverElite.getRawAxis(0) > -0.25){

            twist = 0.0;
        } else{

            twist = RobotMap.driverElite.getRawAxis(0);
        }*/

        // goal centric assist
        if (RobotMap.driverElite.getRawButton(RobotMap.eliteTrackTarget)) {

            twist = 0.0;
        } else {
            twist = RobotMap.driverElite.getRawAxis(0);
        }

        // distance assist for firing at 6-7 feet for low and high port
        if (RobotMap.operator.getRawButton(RobotMap.scoreLow) || RobotMap.operator.getRawButton(RobotMap.scoreHigh)) {

            y = 0.0;
        } else {
            y = RobotMap.driverElite.getRawAxis(5);
        }

        // pixy centric assist
        if (RobotMap.driverElite.getRawButton(RobotMap.eliteTrackBall) && Pixy.seesBall()){

            twistAdjustment = Pixy.adjustToBall();
        } else{

            twistAdjustment = Track.adjustYaw();
        }

        // drive inputs
        drive(x, y + Track.adjustPosition(), twist + twistAdjustment);
        realignToField(RobotMap.eliteZeroGyro);
    }
}
