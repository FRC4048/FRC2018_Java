package org.usfirst.frc4048.arm.math;

public class FullRetractStrat implements ArmStrat{
	
	private final double HOME_LENGTH = 3.0;
	
	@Override
	public double getExtensionLength(double angle) {
		if(angle < MIN_SWITCH_ANGLE)
			return HOME_LENGTH;
		else
			return 0.0;
	}
	
}
