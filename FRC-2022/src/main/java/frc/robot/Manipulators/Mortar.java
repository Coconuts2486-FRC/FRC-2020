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
    //public double movingVelocity = 0.0;
    //public double lastTimeStamp = 0;
    //public double lastVelocity = 0;
    public boolean readyToFire = false;
    //public boolean equal = false;
    
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

        mortarLeft.configOpenloopRamp(0.1);
        mortarRight.configOpenloopRamp(0.1);
        
        
        mortarRight.config_kF(0, 0.048);
        mortarLeft.config_kF(0, 0.048);
        mortarRight.config_kP(0, 0.4);
        mortarLeft.config_kP(0, 0.4);
        mortarRight.config_kI(0, 0.001);
        mortarLeft.config_kI(0, 0.001);
        mortarRight.config_IntegralZone(0, 300);
        mortarLeft.config_IntegralZone(0, 300);

        /*lastTimeStamp = Timer.getFPGATimestamp();
        lastVelocity = 0;*/
    }

    // calculate mortar velocity
    public double calculateVelocity(double y){

        return ((11212 * Math.pow(y, 4)) + (-210.25 * Math.pow(y, 3)) + (479.97 * Math.pow(y, 2)) + (-1692.4 * y) + 5796.6);
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

    /*public double movingAdjustment(){

        double velocity = calculateVelocity(LimeLight.getY());
        double dt = Timer.getFPGATimestamp() - lastTimeStamp;

        double Rate = (velocity - lastVelocity) / 0.02;

        movingVelocity = 2 * Rate;

        lastTimeStamp = Timer.getFPGATimestamp();
        lastVelocity = velocity;

        return movingVelocity;
    }*/

    public double getVelocity(){
        
        return mortarLeft.getSelectedSensorVelocity();
    }

    // mortar control
    public void run(boolean score){

        double mortarVelocity = calculateVelocity(LimeLight.getY()) + adjustVelocity();

        double highVelocity = 6650 + adjustVelocity();
        double lowVelocity = 5100 + adjustVelocity();

        if (getVelocity() > mortarVelocity - 25){

            readyToFire = true;
        } else{

            readyToFire = false;
        }

        SmartDashboard.putBoolean("Ready To Fire", readyToFire);

        if (score){

            mortarLeft.set(TalonFXControlMode.Velocity, mortarVelocity);
            mortarRight.set(TalonFXControlMode.Velocity, mortarVelocity);
        }
        else if (RobotMap.operator.getRawButton(RobotMap.scoreHigh)){

            // high goal scoring speed for 6-7 feet away
            mortarLeft.set(ControlMode.Velocity, highVelocity);//6650
            mortarRight.set(ControlMode.Velocity, highVelocity);
        }
        else if (RobotMap.operator.getRawButton(RobotMap.scoreLow)){

            // low goal scoring speed
            mortarLeft.set(ControlMode.Velocity, lowVelocity);//5100
            mortarRight.set(ControlMode.Velocity, lowVelocity);
        }
        else if (RobotMap.operator.getRawButton(RobotMap.override)){

            // high goal manual scoring speed for 6-7 feet away
            mortarLeft.set(ControlMode.Velocity, highVelocity);//6650
            mortarRight.set(ControlMode.Velocity, highVelocity);
        }
        else{

            // idling mortar speed
            mortarLeft.set(ControlMode.Velocity, 3000);
            mortarRight.set(ControlMode.Velocity, 3000);
        }
    }
    
}
