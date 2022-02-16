package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake {

    // intake components
    private TalonFX intakeMain;
    private TalonSRX lowerMortarIntake;
    private TalonSRX upperMortarIntake;

    // intake constructor
    public Intake(int intakeMain, int lowarMortarIntake, int upperMortarIntake){

        this.intakeMain = new TalonFX(intakeMain);
        this.lowerMortarIntake = new TalonSRX(lowarMortarIntake);
        this.upperMortarIntake = new TalonSRX(upperMortarIntake);
    }

    public void init(){

        intakeMain.setNeutralMode(NeutralMode.Brake);
        lowerMortarIntake.setNeutralMode(NeutralMode.Brake);
        upperMortarIntake.setNeutralMode(NeutralMode.Brake);
        lowerMortarIntake.setInverted(true);
    }

    public void run(){

        if(RobotMap.driver.getRawButton(3) || RobotMap.driver.getRawButton(4) || RobotMap.driver.getRawButton(5) || RobotMap.driver.getRawButton(6)){

            lowerMortarIntake.set(ControlMode.PercentOutput, -0.5);
            upperMortarIntake.set(ControlMode.PercentOutput, 0.5);
            intakeMain.set(ControlMode.PercentOutput, 0.8);
        }else{
            lowerMortarIntake.set(ControlMode.PercentOutput, 0);
            upperMortarIntake.set(ControlMode.PercentOutput, 0);
            intakeMain.set(ControlMode.PercentOutput, 0);
        }

        
    }
}   
