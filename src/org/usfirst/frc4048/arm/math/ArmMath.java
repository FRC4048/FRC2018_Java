package org.usfirst.frc4048.arm.math;

public class ArmMath {
	
	ArmStrat strat;
	
	public ArmMath()
	{
		this.strat = new LinearMoveStrat();
	}
	
	/**
	 * Converts the pot value for the arm to an angle relative to the towers
	 * @param val1Min --> start config of val1
	 * @param val2Min --> start config of val2 at val1
	 * @param val1Max --> max value of val1
	 * @param val2Max --> val2 at the max val1
	 * @param val1Input
	 * @return 
	 */
	public double convertValToVal(double val1Min, double val2Min, double val1Max, double val2Max, double val1Input){	
		//equation: point slope form given two     points: (potMin, angleMin) and (potMax, angleMax)
		double val2Output = ((val2Max - val2Min)/(val1Max - val1Min))*(val1Input - val1Min) + val2Min;
		val2Output = Math.min(val2Output, val2Max);
		val2Output = Math.max(val2Output, val2Min);
		return val2Output;
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
}
