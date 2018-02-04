package org.usfirst.frc4048.utils;

import org.usfirst.frc4048.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/*
 *   MotorStall object should be instantiated in init() method of a command
 *   isFinished() should call isStalled() to determine if stalled for longer than allowed
 */

public class MotorUtils {
	public static final double DEFAULT_TIMEOUT = 0.15;
	private double timeout;
	private double time;
	int PDPChannel;
	double currentThreshold;
	
	public MotorUtils(int PDPPort, double currentThreshold)
	{
		this.timeout = DEFAULT_TIMEOUT;
		this.PDPChannel = PDPPort;
		this.currentThreshold = currentThreshold;
		time = Timer.getFPGATimestamp();
	}
	
	public MotorUtils(int PDPPort, double currentThreshold, double timeout)
	{
		this(PDPPort, currentThreshold);
		this.timeout = timeout;
	}
	
	public boolean isStalled()
	{
		if (RobotMap.pdp.getCurrent(PDPChannel) < currentThreshold)
		{
			time = Timer.getFPGATimestamp();
		}
		else
		{
			if (Timer.getFPGATimestamp() - time > timeout)
			{
				DriverStation.reportError("Motor stall, PDP Channel=" + PDPChannel, false);
				return true;
			}
		}
		return false;
	}
}