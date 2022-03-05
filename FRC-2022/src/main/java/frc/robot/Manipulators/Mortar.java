package frc.robot.Manipulators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.RobotMap;
import frc.robot.Vision.LimeLight;

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
        
        
        mortarRight.config_kF(0, 0.048);
        mortarLeft.config_kF(0, 0.048);
        mortarRight.config_kP(0, 0.15);
        mortarLeft.config_kP(0, 0.15);
    }

    // calculate mortar velocity
    public double calculateVelocity(double y){

        return ((12213 * Math.pow(y, 3)) + (8850.5 * Math.pow(y, 2)) + (-2341.6 * y) + 5405.5);
    }

    // mortar control
    public void run( boolean score){

        double mortarVelocity = calculateVelocity(LimeLight.getY());

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
        else{

            // idling mortar speed
            mortarLeft.set(ControlMode.Velocity, 2000);
            mortarRight.set(ControlMode.Velocity, 2000);
        }
    }
    
}
