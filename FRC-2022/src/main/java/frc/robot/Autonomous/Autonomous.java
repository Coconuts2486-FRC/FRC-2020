package frc.robot.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class Autonomous {
    public static void recordAuto() {
        SmartDashboard.putString("Data", RobotMap.driver.getX()
        + ", " + RobotMap.driver.getY()
        + ", " + RobotMap.driver.getTwist());
        // + ", " + RobotMap.Ids.joystick.getRawButton(1) 
        // + ", " + RobotMap.Ids.joystick.getRawButton(2)
        // + ", " + RobotMap.Ids.joystick.getRawButton(3)
        // + ", " + RobotMap.Ids.joystick.getRawButton(4)
        // + ", " + RobotMap.Ids.joystick.getRawButton(5));
    }

    public static void run() {
        double autoTimer = (Timer.getFPGATimestamp() * 1000) + 1;
        SmartDashboard.putNumber("auto timer", autoTimer);
        double[][] recorded_input = data.positions;
        int length = recorded_input.length - 1;
        SmartDashboard.putNumber("length", length);
        for (int i = 0; i <= length; i++) {
        double currentTimer = Timer.getFPGATimestamp() * 1000;
        double time_stamp = recorded_input[i][0];
        double[] this_pos = {recorded_input[i][1], recorded_input[i][2], recorded_input[i][3]};
        // double[] button_presses = {recorded_input[i][3], 
        //     recorded_input[i][4], recorded_input[i][5], 
        //     recorded_input[i][6], recorded_input[i][7]};
        // boolean[] button_bool = {true, true, true, true, true};
        // for(int j = 0; j < 5; j++) {
        //     if (button_presses[j] != 1) {
        //     button_bool[j] = false;
        //     }
        // }
        if (time_stamp < currentTimer - autoTimer) {
            RobotMap.swerve.drive(this_pos[0], this_pos[1], this_pos[2]);
        } else {
            i--;
        }
        }
    }
}
