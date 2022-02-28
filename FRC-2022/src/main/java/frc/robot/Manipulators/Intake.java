package frc.robot.Manipulators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;

public class Intake {

    // intake components
    private TalonFX intakeMain;
    private TalonSRX lowerMortarIntake;
    private TalonSRX upperMortarIntake;
    private Solenoid lift;
    private static boolean pistonactive = false;

    // intake constructor
    public Intake(int intakeMain, int lowarMortarIntake, int upperMortarIntake, int lift){

        this.intakeMain = new TalonFX(intakeMain);
        this.lowerMortarIntake = new TalonSRX(lowarMortarIntake);
        this.upperMortarIntake = new TalonSRX(upperMortarIntake);
        this.lift = new Solenoid(1, PneumaticsModuleType.REVPH, lift);
    }

    // initializes intake components
    public void init(){
        intakeMain.setNeutralMode(NeutralMode.Brake);
        lowerMortarIntake.setNeutralMode(NeutralMode.Brake);
        upperMortarIntake.setNeutralMode(NeutralMode.Brake);
        lowerMortarIntake.setInverted(true);
        lift.set(false);
    }

    // intake control
    public void run(){

        RobotMap.mortarVelocity.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

        /*if (RobotMap.operator.getRawButton(RobotMap.scoreLow) && RobotMap.mortarVelocity.getSelectedSensorVelocity() > 3700 || RobotMap.operator.getRawButton(RobotMap.scoreHigh) && RobotMap.mortarVelocity.getSelectedSensorVelocity() > 5600){

            lowerMortarIntake.set(ControlMode.PercentOutput, -0.5);
            upperMortarIntake.set(ControlMode.PercentOutput, 0.5);
            intakeMain.set(ControlMode.PercentOutput, 0.5);
        } else{
            lowerMortarIntake.set(ControlMode.PercentOutput, 0);
            upperMortarIntake.set(ControlMode.PercentOutput, 0);
            intakeMain.set(ControlMode.PercentOutput, 0);
        }*/

        // intake arm control
        if (RobotMap.driver.getRawButtonPressed(RobotMap.intakeLift)){
            if (!pistonactive){

                lift.set(true);
                pistonactive = true;
            }
            else{

                lift.set(false);
                pistonactive = false;
            }

        }

        if (RobotMap.driver.getRawButton(5)){

            intakeMain.set(ControlMode.PercentOutput, 0.5);
        } else{

            intakeMain.set(ControlMode.PercentOutput, 0);
        }

        if (RobotMap.driver.getRawButton(6)){

            lowerMortarIntake.set(ControlMode.PercentOutput, -0.5);
            upperMortarIntake.set(ControlMode.PercentOutput, 0.5);
        } else{

            lowerMortarIntake.set(ControlMode.PercentOutput, 0);
            upperMortarIntake.set(ControlMode.PercentOutput, 0);
        }
    }
}   
