package frc.robot.Manipulators;

import com.ctre.phoenix.motorcontrol.ControlMode;
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

    public void init(){

        mortarRight.setInverted(true);
    }

    public void run(){

        if (RobotMap.driver.getRawButton(3)){

            mortarLeft.set(ControlMode.PercentOutput, 0.6);
            mortarRight.set(ControlMode.PercentOutput, 0.6);
        }
        else if (RobotMap.driver.getRawButton(4)){

            mortarLeft.set(ControlMode.PercentOutput, 0.3);
            mortarRight.set(ControlMode.PercentOutput, 0.3);
        }
        else if (RobotMap.operator.getRawButton(RobotMap.scoreLow)){

            mortarLeft.set(ControlMode.PercentOutput, 0.23);
            mortarRight.set(ControlMode.PercentOutput, 0.23);
        }
        else if (RobotMap.driver.getRawButton(6)){

            mortarLeft.set(ControlMode.PercentOutput, 0.1);
            mortarRight.set(ControlMode.PercentOutput, 0.1);
        }
        else{

            mortarLeft.set(ControlMode.PercentOutput, 0.1);
            mortarRight.set(ControlMode.PercentOutput, 0.1);
        }
    }
    
}
