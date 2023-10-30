// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.filter.MedianFilter;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final WPI_TalonSRX m_leftDrive = new WPI_TalonSRX(1);
  private final WPI_TalonSRX m_rightDrive = new WPI_TalonSRX(2);
  // private final Encoder m_leftEncoder = new Encoder(0, 1);
  // private final Encoder m_rightEncoder = new Encoder(2, 3);
  private final XboxController m_controller = new XboxController(0);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftDrive, m_rightDrive);

  private final DigitalOutput m_ultrasonicEnable = new DigitalOutput(9);
  private final AnalogPotentiometer m_ultrasonic1 = new AnalogPotentiometer(0, 512, 0);
  private final AnalogPotentiometer m_ultrasonic2 = new AnalogPotentiometer(1, 512, 0);
  private final AnalogPotentiometer m_ultrasonic3 = new AnalogPotentiometer(2, 512, 0);
  private final AnalogPotentiometer m_ultrasonic4 = new AnalogPotentiometer(3, 512, 0);
  private final MedianFilter m_ultrasonicFilter1 = new MedianFilter(5);
  private final MedianFilter m_ultrasonicFilter2 = new MedianFilter(5);
  private final MedianFilter m_ultrasonicFilter3 = new MedianFilter(5);
  private final MedianFilter m_ultrasonicFilter4 = new MedianFilter(5);

  // private final AHRS m_nav = new AHRS(Port.kUSB1); // Nav-X
  private final Accelerometer m_accelerometer = new BuiltInAccelerometer(); // RoboRIO

  private final Timer m_timer = new Timer();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_ultrasonicEnable.set(true);
    CameraServer.startAutomaticCapture();

    // m_nav.calibrate();
    // while (m_nav.isCalibrating()) {
    //   try {
    //     Thread.sleep(500, 0);
    //   } catch (InterruptedException e) {
    //     e.printStackTrace();
    //   }
    // }
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {}

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    m_robotDrive.tankDrive(-0.6 * m_controller.getLeftY(), -0.6 * m_controller.getRightY());
    
    SmartDashboard.putNumber("Ultrasonic 1", m_ultrasonicFilter1.calculate(m_ultrasonic1.get()));
    SmartDashboard.putNumber("Ultrasonic 2", m_ultrasonicFilter2.calculate(m_ultrasonic2.get()));
    SmartDashboard.putNumber("Ultrasonic 3", m_ultrasonicFilter3.calculate(m_ultrasonic3.get()));
    SmartDashboard.putNumber("Ultrasonic 4", m_ultrasonicFilter4.calculate(m_ultrasonic4.get()));

    // Nav-X
    // SmartDashboard.putNumber("Pitch", m_nav.getPitch());
    // SmartDashboard.putNumber("Roll", m_nav.getRoll());
    // SmartDashboard.putNumber("Yaw", m_nav.getYaw());

    // RoboRIO Accelerometer
    SmartDashboard.putNumber("Accelerometer X", m_accelerometer.getX());
    SmartDashboard.putNumber("Accelerometer Y", m_accelerometer.getY());
    SmartDashboard.putNumber("Accelerometer Z", m_accelerometer.getZ());
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
    m_timer.restart();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
     // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards 30% speed, make sure to turn input squaring off
      m_robotDrive.arcadeDrive(0.6, 0.0, false);
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }
}
