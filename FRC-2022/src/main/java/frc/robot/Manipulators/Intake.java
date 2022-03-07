package frc.robot.Manipulators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotMap;
import frc.robot.Vision.LimeLight;

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
        lift.set(false);
        RobotMap.intakeTimer = (float) Timer.getFPGATimestamp();
    }

    public void autoLift(){

        lift.set(true);
    }

    // intake control
    public void run(boolean intake){

        RobotMap.mortarVelocity.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

        // intake arm control
        if (RobotMap.driverElite.getRawButtonPressed(RobotMap.eliteIntakeLift)){
            if (!pistonactive){
                lift.set(true);
                pistonactive = true;
            } else{
 
                lift.set(false);
                pistonactive = false;
            }

        }

        // main intake control
        if (intake || RobotMap.mortarVelocity.getSelectedSensorVelocity() > RobotMap.mortar.calculateVelocity(LimeLight.getY()) - RobotMap.threshold){

            intakeMain.set(ControlMode.PercentOutput, 0.5);
        } else{

            intakeMain.set(ControlMode.PercentOutput, 0);
        }

        // outtake
        if (RobotMap.driverElite.getRawButton(4)){

            intakeMain.set(ControlMode.PercentOutput, -0.5);
        }

        // secondary intake control
        if (RobotMap.mortarVelocity.getSelectedSensorVelocity() > RobotMap.mortar.calculateVelocity(LimeLight.getY()) - RobotMap.threshold){

            lowerMortarIntake.set(ControlMode.PercentOutput, 0.5);
            upperMortarIntake.set(ControlMode.PercentOutput, 0.5);
        } else{

            lowerMortarIntake.set(ControlMode.PercentOutput, 0);
            upperMortarIntake.set(ControlMode.PercentOutput, 0);
        }

        // change threshhold
        if (RobotMap.intakeTimer - Timer.getFPGATimestamp() > 90) {
            RobotMap.threshold = 100;
        }
    }
}   
