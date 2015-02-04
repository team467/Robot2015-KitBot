package org.usfirst.frc.team467.robot;

import org.apache.log4j.Logger;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically it 
 * contains the code necessary to operate a robot with tank drive.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
    
	private static final Logger LOGGER = Logger.getLogger(Robot.class);
	
	RobotDrive myRobot;  // class that handles basic drive operations
    Joystick leftStick;  // set to ID 1 in DriverStation
    Joystick rightStick; // set to ID 2 in DriverStation

    // Threshold in degrees / second
    private final double HIGH_PASS_THRESHOLD = 0.01;
    Gyro2015 gyro;
    PowerDistributionPanel panel;
    CameraServer cameraServer;
    public Robot() {
    	
        myRobot = new RobotDrive(0, 1);
        myRobot.setExpiration(0.1);
        leftStick = new Joystick(0);
        rightStick = new Joystick(1);
        
        gyro = new Gyro2015(0);
        
//        cameraServer = CameraServer.getInstance();
//        cameraServer.setQuality(50);
//        cameraServer.startAutomaticCapture("cam0");
    }
    
    /**
     * Runs the motors with tank steering.
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
        	gyro.updateAngleHighPass(HIGH_PASS_THRESHOLD); // updateAngleHighPassFilter(HIGH_PASS_THRESHOLD)
        	//System.out.println(gyro);
        	double frontLeft = panel.getCurrent(RobotMap.LEFT_CHANNEL);
        	double frontRight = panel.getCurrent(RobotMap.RIGHT_CHANNEL);
//        	double backLeft = panel.getCurrent(RobotMap.BACK_LEFT_CHANNEL);
//        	double backRight = panel.getCurrent(RobotMap.BACK_RIGHT_CHANNEL);
        	System.out.println("frontLeft=" + frontLeft +
        					 ", frontRight=" + frontRight);
//        	", backLeft=" + backLeft +
//			 ", backRight=" + backRight
        	myRobot.tankDrive(leftStick, rightStick);
        	
        	// wait for a motor update time
        	Timer.delay(0.005);	
        }
    }
}
