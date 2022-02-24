package frc.robot.Vision;

import frc.robot.RobotMap;

public class Pixy {

    private static double pixyVoltageRange = 3.3;
    private static double pixyFOV = 60;

    public static boolean seesBall(){

        return RobotMap.pixyDigitalInput.get();
    }

    public static double angleError(){

        return ((RobotMap.pixyAnalogInput.getAverageVoltage() / pixyVoltageRange) - 0.5) * pixyFOV;
    }

    public static double adjustToBall(){

        double kP = 0.03;
        return kP * angleError();
    }

    
}
