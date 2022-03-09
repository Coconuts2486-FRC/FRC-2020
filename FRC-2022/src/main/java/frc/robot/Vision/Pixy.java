package frc.robot.Vision;

import frc.robot.RobotMap;

public class Pixy {

    private static double pixyVoltageRange = 3.3;
    private static double pixyFOV = 60;

    // receives a signal that the pixy has recognized an object
    public static boolean seesBall(){

        return RobotMap.pixyDigitalInput.get();
    }

    // returns how much the swerve has to turn to align itself with the ball
    public static double angleError(){

        return ((RobotMap.pixyAnalogInput.getAverageVoltage() / pixyVoltageRange) - 0.5) * pixyFOV;
    }

    // adjusts swerve to align itself with ball
    public static double adjustToBall(){

        double kP = 0.03;
        return kP * angleError();
    }

    
}
