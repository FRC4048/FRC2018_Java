package org.usfirst.frc4048.arm.math;

public interface ArmStrat {
	
	/**
	 * Converts angle of arm into extension length. Meant to be used only when placing cube.
	 * @param angle - angle given is relative to starting, is in radians
	 * @return
	 */
	public double getExtensionLength(double angle);
}