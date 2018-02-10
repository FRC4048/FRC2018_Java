package org.usfirst.frc4048.arm.math;

/**
 * Angles are reltaive to home
 *  * @author Team4048
 */
public class CubePositionStrat implements ArmStrat{
	
	private final double MIN_CLIMB_ANGLE = 155.0;
	private final double MIN_HIGH_SCALE_ANGLE = 115.0;
	private final double MIN_LOW_SCALE_ANGLE = 73.0;
	private final double MIN_SWITCH_ANGLE = 48.0;
	private final double MIN_INTAKE_ANGLE = 18.0;
	private final double MIN_HOME_ANGLE = 0.0;
	
	private final double CLIMBER_LENGTH = 12.0;
	private final double HIGH_SCALE_LENGTH = 10.75;
	private final double LOW_SCALE_LENGTH = 7.5;
	private final double SWITCH_LENGTH = 4.0;
	private final double INTAKE_LENGTH = 6.0;
	private final double HOME_LENGTH = 0.0;
	
	/**
	 * Converts angle of arm into extension length. Meant to be used only when placing cube, otherwise fully retracted.
	 * @param angle - angle given is relative to home, is in degrees
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
		else if(angle >= MIN_INTAKE_ANGLE)
			return INTAKE_LENGTH;
		else
			return 0.0;
	}
}