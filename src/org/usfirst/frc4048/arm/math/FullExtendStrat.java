package org.usfirst.frc4048.arm.math;

public class FullExtendStrat implements ArmStrat{
		
	/**
	 * Converts angle of arm into extension length. Meant to be used only when placing cube.
	 * @param angle - angle given is relative to starting, is in degrees
	 * @param placingCube - true if placing cube, false if not
	 * @return
	 */
	@Override
	public double getExtensionLength(double angle)
	{
		if(angle >= MIN_CLIMB_ANGLE)
			return CLIMBER_LENGTH;
		else if(angle >= MIN_HIGH_SCALE_ANGLE)
			return HIGH_SCALE_LENGTH;
		else if(angle >= MIN_LOW_SCALE_ANGLE)
			return LOW_SCALE_LENGTH;
		else if(angle >= MIN_SWITCH_ANGLE)
			return SWITCH_LENGTH;
		else
			return 0.0;
	}
}
