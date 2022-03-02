// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Autonomous.Autonomous;
import frc.robot.Vision.LimeLight;
import frc.robot.Vision.Track;


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
  public void robotInit() {}

  @Override
  public void robotPeriodic() {
    Autonomous.recordAuto();
  }

  @Override
  public void autonomousInit() {}

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

    RobotMap.swerve.run();
    RobotMap.intake.run();
    RobotMap.mortar.run();
    // SmartDashboard.putNumber("mortar velocity", RobotMap.mortarVelocity.getSelectedSensorVelocity());
    // SmartDashboard.putNumber("limelight.getY", LimeLight.getY());
    // SmartDashboard.putNumber("adjustPosition", Track.adjustPosition());
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {

    RobotMap.swerve.disabled();
  }

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
