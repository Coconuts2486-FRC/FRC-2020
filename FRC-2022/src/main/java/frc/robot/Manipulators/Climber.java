package frc.robot.Manipulators;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;

public class Climber {

    private TalonFX climbMotor;
    private Solenoid climbLock;
    public static boolean climbactive = false;

    public Climber(int climbMotor, int climbLock){

        this.climbMotor = new TalonFX(climbMotor);
        this.climbLock = new Solenoid(1, PneumaticsModuleType.REVPH, climbLock);
    }

    public void init(){

        climbMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
        climbMotor.setNeutralMode(NeutralMode.Brake);
        climbLock.set(true);
    }
    
    public void disabled(){

        climbLock.set(false);
    }

    public void run(){

        double encoderStop = climbMotor.getSelectedSensorPosition();

        if (RobotMap.operator.getRawButton(RobotMap.ascend)){

            climbMotor.set(TalonFXControlMode.PercentOutput, 1);
        }
        else if (RobotMap.operator.getRawButton(RobotMap.descend)){
            climbMotor.set(TalonFXControlMode.PercentOutput, -1);
        }
        else{
            climbMotor.set(TalonFXControlMode.PercentOutput, 0);
        }

        if (RobotMap.operator.getRawButtonPressed(RobotMap.climbPiston)){
            if (!climbactive){

                climbLock.set(true);
                climbactive = true;
            } else{

                climbLock.set(false);
                climbactive = false;
            }

        }
    }
    
}
