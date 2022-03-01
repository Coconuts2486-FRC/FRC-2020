package frc.robot.Manipulators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
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

        mortarLeft.configFactoryDefault();
        mortarRight.configFactoryDefault();


        mortarRight.setInverted(true);

        mortarLeft.configNeutralDeadband(0.001);
        mortarRight.configNeutralDeadband(0.001);

        mortarLeft.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
        mortarRight.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

        mortarLeft.configNominalOutputForward(0);
        mortarRight.configNominalOutputForward(0);
        mortarLeft.configNominalOutputReverse(0);
        mortarLeft.configNominalOutputReverse(0);
        mortarLeft.configPeakOutputForward(1);
        mortarRight.configPeakOutputForward(1);
        mortarLeft.configPeakOutputReverse(-1);
        mortarLeft.configPeakOutputReverse(-1);
        
        
        //mortarRight.config_kF(0, 0.05);
        //mortarLeft.config_kF(0, 0.05);
        mortarRight.config_kP(0, 0.0055);
        mortarLeft.config_kP(0, 0.0055);
        

    }

    // mortar control
    public void run(){

        if (RobotMap.driver.getRawButton(3)){

            mortarLeft.set(TalonFXControlMode.Velocity, 10000 * 2048);
            mortarRight.set(TalonFXControlMode.Velocity, 10000 * 2048);
        }
        else if (RobotMap.operator.getRawButton(RobotMap.scoreHigh)){

            // high goal scoring speed for 6-7 feet away
            mortarLeft.set(ControlMode.PercentOutput, 0.31);
            mortarRight.set(ControlMode.PercentOutput, 0.31);
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
            mortarLeft.set(ControlMode.PercentOutput, 0);
            mortarRight.set(ControlMode.PercentOutput, 0);
        }
    }
    
}
