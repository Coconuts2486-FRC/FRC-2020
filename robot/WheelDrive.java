package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.controller.PIDController;

public class WheelDrive {

    private TalonSRX angleMotor;
    private TalonSRX speedMotor;
    private PIDController pidController;

    public WheelDrive (int angleMotor, int speedMotor, int encoder) {
        this.angleMotor = new TalonSRX (angleMotor);
        this.speedMotor = new TalonSRX (speedMotor);
        pidController = new PIDController (1, 0, 0);
    
      

    }
    
    
    
}
