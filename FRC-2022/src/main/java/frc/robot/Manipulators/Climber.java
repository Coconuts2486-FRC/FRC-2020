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
    private double climbSpeed = 0;
    private double output = 0;

    public Climber(int climbMotor, int climbLock){

        this.climbMotor = new TalonFX(climbMotor);
        this.climbLock = new Solenoid(PneumaticsModuleType.CTREPCM, climbLock);
    }

    public void init(){

        climbMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
        climbMotor.setNeutralMode(NeutralMode.Brake);
        climbMotor.setSelectedSensorPosition(0);
        climbLock.set(false);
    }
    
    public void disabled(){

        climbLock.set(true);
    }

    public void run(){

        double climbPosition = climbMotor.getSelectedSensorPosition();
        double setpoint = 157843;
        double kP = 0.0001;
        double maxHeight = 220000;

        double error = setpoint - climbPosition;

        if (RobotMap.operator.getRawButton(RobotMap.toMid)){

            output = kP * error;
        }

        else if (climbPosition > maxHeight && RobotMap.operator.getRawAxis(RobotMap.slowClimb) < -0.2 || climbPosition > maxHeight && RobotMap.operator.getRawAxis(RobotMap.fastClimb) < -0.1){

            output = 0;
        }

        else if (Math.abs(RobotMap.operator.getRawAxis(RobotMap.slowClimb)) > 0.2){

            climbSpeed = -0.75;
            output = climbSpeed * RobotMap.operator.getRawAxis(RobotMap.slowClimb);
        }

        else if (Math.abs(RobotMap.operator.getRawAxis(RobotMap.fastClimb)) > 0.1){

            climbSpeed = -1;
            output = climbSpeed * RobotMap.operator.getRawAxis(RobotMap.fastClimb);
        }

        else{

            output = 0;
        }

        climbMotor.set(TalonFXControlMode.PercentOutput, output);

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
