package frc.robot.Manipulators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.RobotMap;

public class Mortar {

    // mortar components
    private TalonFX mortarLeft;
    private TalonFX mortarRight;
    

    // mortar constructor
    public Mortar (int mortarLeft, int mortarRight){

        this.mortarLeft = new TalonFX(mortarLeft);
        this.mortarRight = new TalonFX(mortarRight);
        
    }

    // initializes mortar components and velocity control
    public void init(){

        mortarRight.setInverted(true);
        mortarRight.config_kF(0, 0.05);
        mortarLeft.config_kF(0, 0.05);
        mortarRight.config_kP(0, 0.055);
        mortarLeft.config_kP(0, 0.055);
    }

    // mortar control
    public void run(){

        if (RobotMap.driver.getRawButton(3)){

            mortarLeft.set(TalonFXControlMode.Velocity, 10000);
            mortarRight.set(TalonFXControlMode.Velocity, 10000);
        }
        else if (RobotMap.operator.getRawButton(RobotMap.scoreHigh)){

            // high goal scoring speed for 6-7 feet away
            mortarLeft.set(ControlMode.PercentOutput, 0.32);
            mortarRight.set(ControlMode.PercentOutput, 0.32);
        }
        else if (RobotMap.operator.getRawButton(RobotMap.scoreLow)){

            // low goal scoring speed
            mortarLeft.set(ControlMode.PercentOutput, 0.24);
            mortarRight.set(ControlMode.PercentOutput, 0.24);
        }
        else if (RobotMap.driver.getRawButton(7)){

            mortarLeft.set(ControlMode.PercentOutput, .75);
            mortarRight.set(ControlMode.PercentOutput, .75);
        }
        else{

            // idling mortar speed
            mortarLeft.set(ControlMode.Velocity, 2000);
            mortarRight.set(ControlMode.Velocity, 2000);
        }
    }
    
}
