package org.usfirst.frc.team467.robot;

import edu.wpi.first.wpilibj.Gyro;

/**
 * @author nathan
 *
 */
public class Gyro2015 {
	private final Gyro gyro;
	private double trustedAngle = 0.0;
	private double currentAngle = 0.0;
	private double prevAngle = 0.0;
	private double deltaAngle = 0.0;
	private long prevTime = 0;
	private double deltaTime = 0.0;

	/**
	 * 2015 Analog Gyro with filtering.
	 * @param port: Analog port gyro is connected to
	 */
	public Gyro2015(int port)
	{
		gyro = new Gyro(port);
        // gyro.startLiveWindowMode();
        
		//gyro.setSensitivity(voltsPerDegreePerSecond);
		
        prevTime = System.currentTimeMillis();
        prevAngle = gyro.getAngle();
	}
	
	private void update()
	{
		currentAngle = gyro.getAngle();
		deltaAngle = currentAngle - prevAngle;
		prevAngle = currentAngle;
		
		long now = System.currentTimeMillis();
		deltaTime = now - prevTime;
		prevTime = now;
	}
	
	public double getAngle()
	{
		return trustedAngle;
	}
	
	/**
	 * Return gyro angle without filtering
	 * @return angle
	 */
	public void updateAngleWithoutFilter()
	{
		update();
		trustedAngle += deltaAngle;
	}
	/**
	 * Return gyro angle with high pass filtering (Not inclusive)
	 * @return angle
	 */
	public void updateAngleHighPass(double filter)
	{
		update();
		if (Math.abs(deltaAngle / deltaTime) > filter)
    	{
			trustedAngle += deltaAngle;
    	}
	}
	/**
	 * Return gyro angle with low pass filtering (Not inclusive)
	 * @return angle
	 */
	public void updateAngleLowPass(double filter)
	{
		update();
		if (Math.abs(deltaAngle / deltaTime) < filter)
    	{
			trustedAngle += deltaAngle;
    	}
	}
	/**
	 * Return gyro angle with low and high pass filtering (Not inclusive)
	 * @return angle
	 */
	public void updateAngleLowAndHighPass(double low, double high)
	{
		update();
		if (Math.abs(deltaAngle / deltaTime) > low && Math.abs(deltaAngle / deltaTime) < high)
    	{
			trustedAngle += deltaAngle;
    	}
	}
	
	
	/**
	 * @return deltaAngle / deltaTime
	 */
	public double getRatePerMillisecond()
	{
		return deltaAngle / deltaTime;
	}

	@Override
	public String toString() {
		return String.format("Gyro2015 [trustedAngle=%-7.2f deg, deltaAngle=%-7.3f deg/sec]",
				trustedAngle, deltaAngle / deltaTime);
	}
	
	
}
