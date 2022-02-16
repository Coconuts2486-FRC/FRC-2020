package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Mortar {

    public static void init(){

        RobotMap.mortarRight.setInverted(true);
    }

    public static void run(){

        if (RobotMap.driver.getRawButton(3)){

            RobotMap.mortarLeft.set(ControlMode.PercentOutput, 0.6);
            RobotMap.mortarRight.set(ControlMode.PercentOutput, 0.6);
        }
        else if (RobotMap.driver.getRawButton(4)){

            RobotMap.mortarLeft.set(ControlMode.PercentOutput, 0.4);
            RobotMap.mortarRight.set(ControlMode.PercentOutput, 0.4);
        }
        else if (RobotMap.driver.getRawButton(5)){

            RobotMap.mortarLeft.set(ControlMode.PercentOutput, 0.23);
            RobotMap.mortarRight.set(ControlMode.PercentOutput, 0.23);
        }
        else if (RobotMap.driver.getRawButton(6)){

            RobotMap.mortarLeft.set(ControlMode.PercentOutput, 0.1);
            RobotMap.mortarRight.set(ControlMode.PercentOutput, 0.1);
        }
        else{

            RobotMap.mortarLeft.set(ControlMode.PercentOutput, 0);
            RobotMap.mortarRight.set(ControlMode.PercentOutput, 0);
        }
    }
    
}
