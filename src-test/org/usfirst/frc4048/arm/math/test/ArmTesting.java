package org.usfirst.frc4048.arm.math.test;

import org.usfirst.frc4048.arm.math.*;

public class ArmTesting {

	public static double ARM_MIN_POT = 1.0;
	public static double ARM_MAX_POT = 3.5;
	public static double ARM_MIN_ANGLE = 00.0;
	public static double ARM_MAX_ANGLE = 158.0;
	
	public static double EXT_MIN_POT = 0.0;
	public static double EXT_MAX_POT = 5.0;
	public static double EXT_MIN_LENGTH = 2.0;
	public static double EXT_MAX_LENGTH = 15.0;
	
	public static void main(String[] args)
	{
		LinearMoveStrat strat = new LinearMoveStrat();
		
		System.out.println("STARTING TEST");
		testLinearStrat(strat);
		System.out.println("ENDING TEST");
		
//		for(double i = ARM_MIN_ANGLE; i <= ARM_MAX_ANGLE; i += 0.5)
//		{
//			System.out.println("ANGLE: " + i + "   LENGTH: " + strat.getExtensionLength(i));
//		}
//		ArmMath math = new ArmMath();
//		for(double armPot = ARM_MIN_POT; armPot <= ARM_MAX_POT; armPot += 0.05)
//		{
//			double angle = math.convertPotToAngle(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, armPot);		
//			double potValue = math.convertArmAngleToExtPot(EXT_MIN_POT, EXT_MIN_LENGTH, EXT_MAX_POT, EXT_MAX_LENGTH, angle);
//			System.out.printf("ARM POT: %-2.4f\t\tARM ANGLE: %-2.4f\tEXT POT: %-2.4f\n", armPot, angle, potValue);
//		}
	}
	
	public static void testLinearStrat(LinearMoveStrat strat)
	{
		if(!(strat.getExtensionLength(-5.0) == 0.0))
		{
			throw new AssertionError("MINUS FIVE ANGLE TEST ERROR");
		}
		
		if(!(strat.getExtensionLength(0.0) == 0.0))
		{
			throw new AssertionError("ZERO ANGLE TEST ERROR");
		}
		
		if(!(strat.getExtensionLength(7.0) == 0.0))
		{
			throw new AssertionError("SEVEN ANGLE TEST ERROR");
		}
		
		if(!(strat.getExtensionLength(15.0) > 22.112))
		{
			throw new AssertionError("15 ANGLE TEST ERROR");
		}
	}
}
