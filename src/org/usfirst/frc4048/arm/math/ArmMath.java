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
		//equation: point slope form given two     points: (potMin, angleMin) and (potMax, angleMax)
		double angle = ((angleMax - angleMin)/(potMax - potMin))*(inputPot - potMin) + angleMin;
		if(angleMax > angleMin)
		{
			angle = Math.min(angle, angleMax);
			angle = Math.max(angle, angleMin);
		}
		else
		{
			angle = Math.min(angle, angleMin);
			angle = Math.max(angle, angleMax);
		}
		return angle;
	}
	
	/**
	 * Converts the pot value for the arm to an angle relative to the towers
	 * @param potMin --> pot value at starting configuration
	 * @param angleMin --> angle at starting configuration
	 * @param potMax --> pot value when arm is straight up, rotated ~180 deg
	 * @param angleMax --> angle at the max pot
	 * @param inputAngle
	 * @return 
	 */
	public double convertAngleToPot(double potMin, double angleMin, double potMax, double angleMax, double inputAngle){	
		//equation: point slope form given two     points: (angleMin, potMin) and (potMax, angleMax)
		double pot = ((potMax - potMin)/(angleMax - angleMin))*(inputAngle - angleMin) + potMin;
		if(potMax > potMin)
		{
			pot = Math.min(pot, potMax);
			pot = Math.max(pot, potMin);
		}
		else
		{
			pot = Math.min(pot, potMin);
			pot = Math.max(pot, potMax);
		}
		return pot;
	}
	
	/**
	 * Converts arm angle value into extension pot value
	 * @param angle - angle from starting position in degrees
	 * @return
	 */
	public double convertArmAngleToExtPot(double potMin, double lengthMin, double potMax, double lengthMax, double angle)
	{		
		double length = strat.getExtensionLength(angle);
		double potVal = ((potMax - potMin)/(lengthMax - lengthMin)) * (length - lengthMin) + potMin;
		potVal = Math.min(potVal, potMax);
		potVal = Math.max(potVal, potMin);
		return potVal;
	}
	
	/**
	 * Converts arm angle value into extension pot value
	 * @param potVal - angle from starting position in degrees
	 * @return
	 */
	public double convertLengthToExtPot(double potMin, double lengthMin, double potMax, double lengthMax, double length)
	{		
		double potVal = ((potMax - potMin)/(lengthMax - lengthMin)) * (length - lengthMin) + potMin;
		potVal = Math.min(potVal, potMax);
		potVal = Math.max(potVal, potMin);
		return potVal;
	}
	
	/**
	 * Converts arm angle value into extension pot value
	 * @param angle - angle from starting position in degrees
	 * @return
	 */
	public double convertExtPotToLength(double potMin, double lengthMin, double potMax, double lengthMax, double angle)
	{		
		double potVal = ((potMax - potMin)/(lengthMax - lengthMin)) * (length - lengthMin) + potMin;
		potVal = Math.min(potVal, potMax);
		potVal = Math.max(potVal, potMin);
		return potVal;
	}
}
