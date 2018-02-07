package org.usfirst.frc4048.arm.math;

public interface ArmStrat {
	
	public final double MIN_CRIT_ANGLE = 20.0;
	
	public final double MIN_CLIMB_ANGLE = 100.0;
	public final double MIN_HIGH_SCALE_ANGLE = 75.0;
	public final double MIN_LOW_SCALE_ANGLE = 55.0;
	public final double MIN_SWITCH_ANGLE = 30.0;
	
	public final double CLIMBER_LENGTH = 12.0;
	public final double HIGH_SCALE_LENGTH = 10.75;
	public final double LOW_SCALE_LENGTH = 7.5;
	public final double SWITCH_LENGTH = 4.0;
	
	/**
	 * Converts angle of arm into extension length. Meant to be used only when placing cube.
	 * @param angle - angle given is relative to starting, is in radians
	 * @return
	 */
	public double getExtensionLength(double angle);
}