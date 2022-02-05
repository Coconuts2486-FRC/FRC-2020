package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
    public static void SmartDashPrintData() {
        SmartDashboard.putString("Data", Map.driver.getZ()
        + ", " + Map.driver.getY()
        + ", " + Map.driver.getX()
        + ", " + Map.driver.getRawButton(1) 
        + ", " + Map.driver.getRawButton(2)
        + ", " + Map.driver.getRawButton(3)
        + ", " + Map.driver.getRawButton(4)
        + ", " + Map.driver.getRawButton(5)
        );
    }
}
