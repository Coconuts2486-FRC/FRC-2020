// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Autonomous.Autonomous;
import frc.robot.Vision.LimeLight;
import frc.robot.Vision.Track;
import frc.robot.ColorSensor;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    //arduino.set();
  }

  @Override
  public void robotPeriodic() {
   // arduino.run(); 
  }

  @Override
  public void autonomousInit() {

    RobotMap.swerve.init();
    RobotMap.intake.init();
    RobotMap.mortar.init();
    Autonomous.run();
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {

    RobotMap.swerve.init();
    RobotMap.intake.init();
    RobotMap.mortar.init();
  }

  @Override
  public void teleopPeriodic() {

    //RobotMap.swerve.autoInit();
    RobotMap.swerve.run(RobotMap.driverElite.getRawAxis(4), RobotMap.driverElite.getRawAxis(5), RobotMap.driverElite.getRawAxis(0), RobotMap.driverElite.getRawButton(RobotMap.eliteTrackTarget));
    RobotMap.intake.run(RobotMap.driverElite.getRawButton(RobotMap.eliteIntake));
    RobotMap.mortar.run(RobotMap.operator.getRawButton(RobotMap.score));
    Autonomous.recordAuto();
    ColorSensor.ejectWrongBalls();

     SmartDashboard.putNumber("mortar velocity", RobotMap.mortarVelocity.getSelectedSensorVelocity());
     SmartDashboard.putNumber("calculated velocity", RobotMap.mortar.calculateVelocity(LimeLight.getY()));
     SmartDashboard.putNumber("limelight.getY", LimeLight.getY());
     SmartDashboard.putNumber("x", RobotMap.driverElite.getRawAxis(4));
     SmartDashboard.putNumber("y", RobotMap.driverElite.getRawAxis(5));
     SmartDashboard.putNumber("z", RobotMap.driverElite.getRawAxis(0));
    // SmartDashboard.putNumber("adjustPosition", Track.adjustPosition());
    
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {

    RobotMap.swerve.disabled();
    SmartDashboard.putNumber("limelight.getY", LimeLight.getY());
  }

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
