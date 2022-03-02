package frc.robot.Vision;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Track{
         
        // tracking components
        // d = (h2-h1) / tan(a1+a2)
        public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        public static final double cameraHeight = 14.5; // inches
        public static final double tapeHeight = 53; // still inches
        public static final double angle1 = 28.9;
        public static double angle2 = 1;
        public static int x = 0;
        public static XboxController joystick = RobotMap.driverElite;
        public static double lastpos = 0;

        // gets distance limelight is from target
        // 4ft
        public static double getDistance() {
                double ty = table.getEntry("ty").getDouble(0.0);
                angle2 = ty;
                double distance = (tapeHeight - cameraHeight)
                                / Math.tan(Math.toRadians(angle1) + Math.toRadians(angle2));
                return distance;
        }

        // returns how much the swerve needs to turn to align itself with the goal
        public static double adjustYaw() {
                SmartDashboard.putNumber("lastPos", lastpos);
                double Kp = -0.032;
                double min_command = 0.05;
                double tx = LimeLight.getX();
                double steering_adjust = 0.0;

                if (joystick.getRawButton(RobotMap.eliteTrackTarget) && LimeLight.isTarget()) {
                        double heading_error_X = -tx;

                        if (tx > 1.0) {
                                steering_adjust = Kp * heading_error_X - min_command;
                        } else if (tx < 1.0) {
                                steering_adjust = Kp * heading_error_X + min_command;
                        }

                }
                return steering_adjust;
        }

        // returns how far the swerve most travel to be within 6-7 feet of the target
        public static double adjustPosition() {

                double Kp = -0.032;
                double min_command = 0.05;
                double ty = LimeLight.getY() * 27;
                double heading_error_Y = -ty;
                double distance_adjust = 0.0;

                if (RobotMap.operator.getRawButton(RobotMap.scoreLow) || RobotMap.operator.getRawButton(RobotMap.scoreHigh) && LimeLight.isTarget()) {

                        if (ty > 1.0) {
                                distance_adjust = Kp * heading_error_Y - min_command;
                        } else if (ty < 1.0) {
                                distance_adjust = Kp * heading_error_Y + min_command;
                        }

                }

                return distance_adjust;
        }

}