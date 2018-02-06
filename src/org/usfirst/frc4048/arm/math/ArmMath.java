package org.usfirst.frc4048.arm.math;

public class ArmMath {
	
	ArmStrat strat;
	
	public ArmMath()
	{
		this.strat = new LinearMoveStrat();
	}
	
	/**
	 * Converts the pot value for the arm to an angle relative to the towers
	 * @param potMin --> pot value at starting configuration
	 * @param angleMin --> angle at starting configuration
	 * @param potMax --> pot value when arm is straight up, rotated ~180 deg
	 * @param angleMax --> angle at the max pot
	 * @param inputPot
	 * @return 
	 */
	public double convertPotToAngle(double potMin, double angleMin, double potMax, double angleMax, double inputPot){	
		//equation: point slope form given two points: (potMin, angleMin) and (potMax, angleMax)
		return ((angleMax - angleMin)/(potMax - potMin))*(inputPot - potMin) + angleMin;
	}
	
	/**
	 * Converts arm angle value into extension pot value
	 * @param angle - angle from starting position in degrees
	 * @return
	 */
	public double convertArmAngleToExtPot(double angle)
	{
		return 0.0;
	}
}
