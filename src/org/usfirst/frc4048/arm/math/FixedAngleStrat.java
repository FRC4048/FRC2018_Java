package org.usfirst.frc4048.arm.math;

public class FixedAngleStrat implements ArmStrat{

	private static final double MAX_CRIT_ANGLE = 50.0;
	private static final double LOWER_MARGIN = 5.0;
	private static final double UPPER_MARGIN = 3.0;
	
	private static final double CRIT_EXT_LENGTH = 5.5;
	private static final double EXT_LENGTH = 10.75;
	
	/**
	 * Converts angle of arm into extension length
	 * @param angle - angle given is relative to starting, is in degrees
	 * @return
	 */
	@Override
	public double getExtensionLength(double angle)
	{
		if(angle >= MIN_CRIT_ANGLE - LOWER_MARGIN && angle <= MAX_CRIT_ANGLE + UPPER_MARGIN)
			return CRIT_EXT_LENGTH;
		else
			return EXT_LENGTH;
	}
}
