package frc.robot.Drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.Vision.Pixy;
import frc.robot.Vision.Track;

public class Swerve {

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

    // testing method for absolute encoder offsets
    public void autoInit(){

        backLeft.autoInit(-(13 * Math.PI / 36));
        backRight.autoInit(Math.PI / 12);
        frontLeft.autoInit(41 * Math.PI / 36);
        frontRight.autoInit(59 * Math.PI / 45);
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
        backRight.drive(backRightSpeed, backRightAngle + (Math.PI / 12));
        backLeft.drive(backLeftSpeed, backLeftAngle - (13 * Math.PI / 36));
        frontRight.drive(frontRightSpeed, frontRightAngle + (59 * Math.PI / 45));
        frontLeft.drive(frontLeftSpeed, frontLeftAngle + (41 * Math.PI / 36));

    }

    // account for gyro drift by rezeroing gyro
    public void realignToField(int button) {

        if (RobotMap.driverElite.getRawButton(button)) {

            RobotMap.gyro.setYaw(0);
        }
    }

    // field, goal, and ball centric control
    public void run(double x, double y, double z, boolean track) {

        // drive inputs
        double twist = 0.0;
        double twistAdjustment = Track.adjustYaw(track);
        double twistDeadband = 0.4;
        double directionDeadband = 0.2;

        // deadband
        if (Math.abs(z) < twistDeadband){

            twist = 0.0;
        } else{

            twist = (1 / (1 - twistDeadband)) * (z + -Math.signum(z) * twistDeadband); 
        } 

        if (Math.abs(x) < directionDeadband){

            x = 0.0;
        } else{

            x = (1 / (1 - directionDeadband)) * (x + -Math.signum(x) * directionDeadband); 
        } 

        if (Math.abs(y) < directionDeadband){

            y = 0.0;
        } else{

            y = (1 / (1 - directionDeadband)) * (y + -Math.signum(y) * directionDeadband); 
        } 

        // goal centric assist
        if (track) {

            twist = 0.0;
        } else {
            twist = z;
        }

        // distance assist for firing at 6-7 feet for low and high port
        if (RobotMap.operator.getRawButton(RobotMap.scoreLow) || RobotMap.operator.getRawButton(RobotMap.scoreHigh)) {

            y = 0.0;
        } else {
            y = y * 1;
        }

        SmartDashboard.putNumber("Limelight.getX", Track.adjustYaw(track));


        // pixy centric assist
        /*if (RobotMap.driverElite.getRawButton(RobotMap.eliteTrackBall) && Pixy.seesBall()){

            twistAdjustment = Pixy.adjustToBall();
        } else{

            twistAdjustment = Track.adjustYaw(RobotMap.driverElite.getRawButton(RobotMap.eliteTrackTarget));
        }*/

        // drive inputs
        drive(x, y + Track.adjustPosition(), twist + twistAdjustment);
        realignToField(RobotMap.eliteZeroGyro);
    }

    // same run function but used in auto to reduce conflict
    public void autoRun(double x, double y, double z, boolean track) {

        // drive inputs
        double twist = 0.0;
        double twistDeadband = 0.4;
        double directionDeadband = 0.2;
        double kP = 0.28; 
        double[] ypr_deg = new double[3];

        RobotMap.gyro.getYawPitchRoll(ypr_deg);
        double robotAngle = ypr_deg[0] * Math.PI / 180;
        double errorAngle = (0 - robotAngle) % (Math.PI * 2);
        double correction = errorAngle * kP;
        double twistAdjustment = correction;

        // deadband
        if (Math.abs(z) < twistDeadband){

            twist = 0.0;
        } else{

            twist = (1 / (1 - twistDeadband)) * (z + -Math.signum(z) * twistDeadband); 
        } 

        if (Math.abs(x) < directionDeadband){

            x = 0.0;
        } else{

            x = (1 / (1 - directionDeadband)) * (x + -Math.signum(x) * directionDeadband); 
        } 

        if (Math.abs(y) < directionDeadband){

            y = 0.0;
        } else{

            y = (1 / (1 - directionDeadband)) * (y + -Math.signum(y) * directionDeadband); 
        } 

        

        // goal centric assist
        if (track) {

            twist = 0.0;
        } else {
            twist = z;
        }

        // pixy centric assist
        if (RobotMap.driverElite.getRawButton(RobotMap.eliteTrackBall) && Pixy.seesBall()){

            twistAdjustment = Pixy.adjustToBall();
        } else{

            twistAdjustment = Track.adjustYaw(RobotMap.driverElite.getRawButton(RobotMap.eliteTrackTarget));
        }

        // drive inputs
        drive(x, y + Track.adjustPosition(), twist + twistAdjustment);
        realignToField(RobotMap.eliteZeroGyro);
    }
}
