package frc.robot.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.Autonomous.autos.fourBall;
import frc.robot.Autonomous.autos.twoBallHanger;
import frc.robot.Autonomous.autos.twoBallRefStation;

public class Autonomous {
    public static SendableChooser autoChooser = new SendableChooser<>();

    // converts boolean input to int output for the purpose of recording button inputs for auto
    public static int boolToInt(boolean input) {
        if (input) {
            return 1;
        } else {
            return 0;
        }
    }

    // converts int input to boolean output for the purpose of recording button inputs for auto
    public static boolean intToBool(int input) {
        if (input == 1) {
            return true;
        } else {
            return false;
        }
    }

    // records controller inputs from a manually driven path to be used for auto
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

    // allows us to select our auto path
    public static void chooser() {
        autoChooser.setDefaultOption("four ball", 1);
        autoChooser.addOption("twoBallHanger", 2);
        autoChooser.addOption("twoBallRefStation", 3);
        SmartDashboard.putData("Auto Modes", autoChooser);

    }

    public static int getSelectedAuto() {
        return (int) autoChooser.getSelected();
    }

    // runs the selected auto path
    public static void run() {

        double autoTimer = (Timer.getFPGATimestamp() * 1000) + 1;
        double[][] recorded_input = twoBallRefStation.positions;

        if (getSelectedAuto() == 1) {
            recorded_input = fourBall.positions;
        } else if (getSelectedAuto() == 2) {
            recorded_input = twoBallHanger.positions;
        }
        
        int length = recorded_input.length - 1;

        RobotMap.intake.autoLift();

        for (int i = 0; i <= length; i++) {
            double currentTimer = Timer.getFPGATimestamp() * 1000;
            // x - recorded_input[i][1] * 0.5

            if ((recorded_input[i][0] * 1000) < currentTimer - autoTimer) {
                RobotMap.swerve.autoRun(recorded_input[i][1], recorded_input[i][2], recorded_input[i][3], intToBool((int) recorded_input[i][5]));
                RobotMap.intake.run(intToBool((int) recorded_input[i][4]));
                RobotMap.mortar.run(intToBool((int) recorded_input[i][6]));
            } else {
                i--;
            }
        }
    }
}
