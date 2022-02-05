package frc.robot;

public class Swerve {

    private Module backRight;
    private Module backLeft;
    private Module frontRight;
    private Module frontLeft;

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
        double x0 = y * Math.cos(robotAngle) + x * Math.sin(robotAngle);
        x = -y * Math.sin(robotAngle) + x * Math.cos(robotAngle);
        y = x0;

        // vector algebra
        double a = x - Twist * (L / r);
        double b = x + Twist * (L / r);
        double c = y - Twist * (W / r);
        double d = y + Twist * (W / r);

        // calculated module speeds
        double backRightSpeed = Math.sqrt((a * a) + (d * d));
        double backLeftSpeed = Math.sqrt((a * a) + (c * c));
        double frontRightSpeed = Math.sqrt((b * b) + (d * d));
        double frontLeftSpeed = Math.sqrt((b * b) + (c * c));

        // calculated module angles
        double backRightAngle = Math.atan2(a, d);
        double backLeftAngle = Math.atan2(a, c);
        double frontRightAngle = Math.atan2(b, d);
        double frontLeftAngle = Math.atan2(b, c);

        // give modules a speed and angle
        backRight.drive(backRightSpeed, backRightAngle);
        backLeft.drive(backLeftSpeed, backLeftAngle);
        frontRight.drive(frontRightSpeed, frontRightAngle);
        frontLeft.drive(frontLeftSpeed, frontLeftAngle);

    }

    // account for gyro drift by rezeroing gyro
    public void realignToField(int button) {

        if (RobotMap.driver.getRawButton(button)) {

            RobotMap.gyro.setYaw(0);
        }
    }

}
