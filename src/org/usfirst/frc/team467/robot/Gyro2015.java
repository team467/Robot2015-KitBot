package org.usfirst.frc.team467.robot;

import edu.wpi.first.wpilibj.Gyro;

public class Gyro2015 {
	private Gyro gyro;
	private double trustedAngle;
	/**
	 * 2015 Analog Gyro with filtering.
	 * @param port: Analog port gyro is connected to
	 */
	public Gyro2015(int port)
	{
		gyro = new Gyro(port);
        gyro.startLiveWindowMode();
        
        trustedAngle = gyro.getAngle();
	}
	
	/**
	 * Return gyro angle without filtering
	 * @return angle
	 */
	public double getAngle(){
		trustedAngle += gyro.getRate();
		return trustedAngle;
	}
	/**
	 * Return gyro angle with high pass filtering (Not inclusive)
	 * @return angle
	 */
	public double getAngleHighPass(double filter){
		if (gyro.getRate() > filter)
    	{
			return getAngle();
    	}
		return trustedAngle;
	}
	/**
	 * Return gyro angle with low pass filtering (Not inclusive)
	 * @return angle
	 */
	public double getAngleLowPass(double filter){
		if (gyro.getRate() < filter)
    	{
			return getAngle();
    	}
		return trustedAngle;
	}
	/**
	 * Return gyro angle with low and high pass filtering (Not inclusive)
	 * @return angle
	 */
	public double getAngleLowAndHighPass(double low, double high){
		if (gyro.getRate() > low && gyro.getRate() < high)
    	{
			return getAngle();
    	}
		return trustedAngle;
	}
	
	/**
	 * Unfiltered rate from analog gyro.
	 * @return Rate
	 */
	public double getUnfilteredRate() {
		return gyro.getRate();
	}
}
