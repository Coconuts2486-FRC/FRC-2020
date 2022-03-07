package frc.robot.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Autonomous.autos.twoBallRefStation;
import frc.robot.Vision.Track;

public class Autonomous {
    public static int boolToInt(boolean input) {
        if (input) {
            return 1;
        } else {
            return 0;
        }
    }

    public static boolean intToBool(int input) {
        if (input == 1) {
            return true;
        } else {
            return false;
        }
    }
    public static void recordAuto() {
        System.out.println("Auto recording start: " 
                + RobotMap.driverElite.getRawAxis(4) //x axis
                + ", " + RobotMap.driverElite.getRawAxis(5) //y axis
                + ", " + RobotMap.driverElite.getRawAxis(0) //z axis
                // + ", " + boolToInt(RobotMap.driverElite.getRawButton(RobotMap.eliteIntakeLift))
                + ", " + boolToInt(RobotMap.driverElite.getRawButton(RobotMap.eliteIntake))
                + ", " + boolToInt(RobotMap.driverElite.getRawButton(RobotMap.eliteTrackTarget))
                + ", " + boolToInt(RobotMap.operator.getRawButton(RobotMap.score))
                // + ", " + RobotMap.Ids.joystick.getRawButton(5));
                + " Auto recording end");
    }

    public static void run() {
        double autoTimer = (Timer.getFPGATimestamp() * 1000) + 1;
        double[][] recorded_input = twoBallRefStation.positions;
        int length = recorded_input.length - 1;

        RobotMap.intake.autoLift();

        for (int i = 0; i <= length; i++) {
            double currentTimer = Timer.getFPGATimestamp() * 1000;
            // x - recorded_input[i][1] * 0.5

            if ((recorded_input[i][0] * 1000) < currentTimer - autoTimer) {
                RobotMap.swerve.run(recorded_input[i][1], recorded_input[i][2], recorded_input[i][3], intToBool((int) recorded_input[i][5]));
                RobotMap.intake.run(intToBool((int) recorded_input[i][4]));
                RobotMap.mortar.run(intToBool((int) recorded_input[i][6]));
            } else {
                i--;
            }
        }
    }
}
