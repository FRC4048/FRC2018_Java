package org.usfirst.frc4048.arm.math;

public class LinearMoveStrat implements ArmStrat{
	
	/**
	 * Max distance the arm can extend to at 90 degrees (based on 16 inch boundary)
	 */
	public static final double MAX_DISTANCE = 44;
	/**
	 * Margin for max length (currently zero because the arm is physically limited)
	 */
	public static final double DISTANCE_MARGIN = 0.0;
	/**
	 * Angle of arm from tower at home position. Angle should never be below this.
	 */
	public static final double STARTING_ANGLE = Math.toRadians(22.0);
	/**
	 * Extension at home
	 */
	public static final double START_EXT_LENGTH = 0.0;
	/**
	 * Angle that when above begins the linear math.
	 */
	public static final double HOME_MAX_ANGLE = Math.toRadians(32.0);
	/**
	 * Relative to starting
	 */
	public static final double MAX_ANGLE = Math.toRadians(158.0);
	/**
	 * Length of arm when at home
	 */
	public static final double FIXED_ARM_LENGTH = 44.0;
	/**
	 * Maximum length of extension
	 */
	public static final double MAX_EXTENSION = 15.25;//changed to match the EXT_MAX_LENGTH in ArmTesting.java
	
	//added constructor to resolve error in ArmTesting
	public LinearMoveStrat()
	{
		
	}
	
	/**
	 * Converts angle of arm into extension length. Meant to be used only when placing cube.
	 * @param angle - angle given is relative to starting, is in radians
	 * @return
	 */
	@Override
	public double getExtensionLength(double angle)
	{
//		return testGetExtensionLength(angle, STARTING_ANGLE, HOME_MAX_ANGLE, START_EXT_LENGTH, MAX_DISTANCE, DISTANCE_MARGIN, FIXED_ARM_LENGTH, MAX_EXTENSION);
		angle = Math.toRadians(angle) + STARTING_ANGLE;	//Is now relative to tower
		if(angle <= HOME_MAX_ANGLE)
		{
			return START_EXT_LENGTH;
		}
		else if(angle > STARTING_ANGLE + MAX_ANGLE)
		{
			return START_EXT_LENGTH;
		}
		else
		{
			double length = ((MAX_DISTANCE-DISTANCE_MARGIN)/Math.sin(angle)) - FIXED_ARM_LENGTH;
			if(length > MAX_EXTENSION)
			{
				length = MAX_EXTENSION;
			}
			if(length < 0)
			{
				length = 0.0;
			}
			return length;
		}
	}
	
//	public double testGetExtensionLength(double angle, double startingAngle, double homeMaxAngle, double startExtLength, double maxDistance, double distanceMargin, double fixedArmLength, double maxExtension)
//	{
//		angle = Math.toRadians(angle) + startingAngle;	//Is now relative to tower
//		if(angle <= homeMaxAngle)
//		{
//			return startExtLength;
//		}
//		else
//		{
//			double length = ((maxDistance-distanceMargin)/Math.sin(angle)) - fixedArmLength;
//			if(length > maxExtension)
//			{
//				length = maxExtension;
//			}
//			if(length < 0)
//			{
//				length = 0.0;
//			}
//			return length;
//		}
//	}
}
