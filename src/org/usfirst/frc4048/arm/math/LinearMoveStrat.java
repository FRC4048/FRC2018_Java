package org.usfirst.frc4048.arm.math;

public class LinearMoveStrat implements ArmStrat{
	
	public static final double MAX_DISTANCE = 43.25;
	public static final double STARTING_ANGLE = Math.toRadians(35.0);	//Home angle from tower
	public static final double FIXED_ARM_LENGTH = 39.0;
	
	
	/**
	 * Converts angle of arm into extension length. Meant to be used only when placing cube.
	 * @param angle - angle given is relative to starting, is in radians
	 * @return
	 */
	@Override
	public double getExtensionLength(double angle)
	{
		angle = Math.toRadians(angle);
		return (MAX_DISTANCE/Math.cos(angle + STARTING_ANGLE)) - FIXED_ARM_LENGTH;
	}
}
