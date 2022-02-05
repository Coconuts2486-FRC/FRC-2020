// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;


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

  private Module backRight = new Module (RobotMap.rotateBR, RobotMap.driveBR, RobotMap.encoderBR);
  private Module backLeft = new Module (RobotMap.rotateBL, RobotMap.driveBL, RobotMap.encoderBL);
  private Module frontRight = new Module (RobotMap.rotateFR, RobotMap.driveFR, RobotMap.encoderFR);
  private Module frontLeft = new Module (RobotMap.rotateFL, RobotMap.driveFL, RobotMap.encoderFL);

  private Swerve swerve = new Swerve(backRight, backLeft, frontRight, frontLeft);

  @Override
  public void robotInit() {}

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {

    swerve.init();
  }


  @Override
  public void teleopPeriodic() {

    swerve.drive(RobotMap.driver.getX(), RobotMap.driver.getY(), RobotMap.driver.getTwist());
    swerve.realignToField(RobotMap.zeroGyro);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {

    swerve.disabled();
  }

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
