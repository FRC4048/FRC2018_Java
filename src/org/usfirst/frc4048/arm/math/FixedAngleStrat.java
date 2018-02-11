package org.usfirst.frc4048.arm.math;

/**
 * All angles in this strategy are relative to home
 * @author Team4048
 */
public class FixedAngleStrat implements ArmStrat{

	/**
	 * Defines where home starts
	 */
	private static final double MIN_HOME = 0.0;
	/**
	 * Defines where home
	 */
	private static final double MAX_HOME = 10.0;
	/**
	 * Minimum angle for fully retracted range
	 */
	private static final double MIN_CRIT_ANGLE = 20.0;
	/**
	 * Maximum angle for fully retracted range
	 */
	private static final double MAX_CRIT_ANGLE = 112.0;
	/**
	 * Margin for minimum critical angle
	 */
	private static final double LOWER_MARGIN = 5.0;
	/**
	 * Margin for maximum critical angle
	 */
	private static final double UPPER_MARGIN = 3.0;
	
	/*
	 * 
	 */
	private static final double HOME_EXT_LENGTH = 0.0;
	/**
	 * Extension length within critical angle range
	 */
	private static final double CRIT_EXT_LENGTH = 0.0;
	/**
	 * Length of extension outside of critical angle range
	 */
	private static final double EXT_LENGTH = 10.75;
	
	/**
	 * Converts angle of arm into extension length
	 * @param angle - angle given is relative to home, is in degrees
	 * @return
	 */
	@Override
	public double getExtensionLength(double angle)
	{
		if(angle <= MAX_HOME)
		{
			return HOME_EXT_LENGTH;
		}
		else if(angle < MIN_CRIT_ANGLE - LOWER_MARGIN)
		{
			return EXT_LENGTH;
		}
		else if(angle <= MAX_CRIT_ANGLE + UPPER_MARGIN)
		{
			return CRIT_EXT_LENGTH;
		}
		else
		{
			return EXT_LENGTH;
		}
	}
}
