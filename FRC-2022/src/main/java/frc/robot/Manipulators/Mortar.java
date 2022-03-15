package frc.robot.Manipulators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.Vision.LimeLight;

public class Mortar {

    // mortar components
    private TalonFX mortarLeft;
    private TalonFX mortarRight;
    public double tune = 0.0;
    
    

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
        mortarLeft.configNominalOutputReverse(0);
        mortarLeft.configPeakOutputForward(1);
        mortarLeft.configPeakOutputReverse(-1);
        mortarRight.configNominalOutputForward(0);     
        mortarRight.configNominalOutputReverse(0);      
        mortarRight.configPeakOutputForward(1);
        mortarRight.configPeakOutputReverse(-1);

        mortarLeft.configOpenloopRamp(1);
        mortarRight.configOpenloopRamp(1);
        
        
        mortarRight.config_kF(0, 0.048);
        mortarLeft.config_kF(0, 0.048);
        mortarRight.config_kP(0, 0.3);
        mortarLeft.config_kP(0, 0.3);
    }

    // calculate mortar velocity
    public double calculateVelocity(double y){

        return ((12213 * Math.pow(y, 3)) + (8850.5 * Math.pow(y, 2)) + (-2341.6 * y) + 5530.5);
    }

    // manual velocity adjustment
    public double adjustVelocity(){

        if (RobotMap.operator.getRawButtonPressed(RobotMap.increaseMortarVelocity)){

            tune += 25;
        } 

        if (RobotMap.operator.getRawButtonPressed(RobotMap.decreaseMortarVelocity)){

            tune -= 25;
        } 

        return tune;
    }

    // mortar control
    public void run(boolean score){

        double mortarVelocity = calculateVelocity(LimeLight.getY()) + adjustVelocity();

        if (score){

            mortarLeft.set(TalonFXControlMode.Velocity, mortarVelocity);
            mortarRight.set(TalonFXControlMode.Velocity, mortarVelocity);
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
        else if (RobotMap.operator.getRawButton(RobotMap.override)){

            // high goal manual scoring speed for 6-7 feet away
            mortarLeft.set(ControlMode.PercentOutput, 0.31);
            mortarRight.set(ControlMode.PercentOutput, 0.31);
        }
        else{

            // idling mortar speed
            mortarLeft.set(ControlMode.Velocity, 2000);
            mortarRight.set(ControlMode.Velocity, 2000);
        }
    }
    
}
