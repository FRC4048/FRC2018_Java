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
		LinearMoveStrat stratLinear = new LinearMoveStrat();
		System.out.println("STARTING LINEAR TEST");
		testLinearStrat(stratLinear);
		System.out.println("ENDING LINEAR TEST");
		
		FixedAngleStrat stratFixed = new FixedAngleStrat();
		System.out.println("STARTING FIXED ANGLE TEST");
		testFixedAngleStrat(stratFixed);
		System.out.println("ENDING FIXED ANGLE TEST");
		
		CubePositionStrat stratCube = new CubePositionStrat();
		System.out.println("STARTING CUBE ANGLE TEST");
		testCubePosStrat(stratCube);
		System.out.println("ENDING CUBE ANGLE TEST");
	}
	
	public static void testLinearStrat(LinearMoveStrat strat)
	{
		if(!(strat.getExtensionLength(-5.0) == 0.0))
		{
			throw new AssertionError("MINUS FIVE ANGLE TEST ERROR: " + strat.getExtensionLength(-5.0));
		}
		
		if(!(strat.getExtensionLength(0.0) == 0.0))
		{
			throw new AssertionError("ZERO ANGLE TEST ERROR: " + strat.getExtensionLength(0.0));
		}
		
		if(!(strat.getExtensionLength(7.0) == 0.0))
		{
			throw new AssertionError("SEVEN ANGLE TEST ERROR: " + strat.getExtensionLength(7.0));
		}
		
		if(!(((strat.getExtensionLength(15.0) - 22.112)) < 0.001))
		{
			throw new AssertionError("15 ANGLE TEST ERROR: " + strat.getExtensionLength(15.0));
		}
		
		if(!(strat.getExtensionLength(68.0) == 0.0))
		{
			throw new AssertionError("68 ANGLE TEST ERROR: " + strat.getExtensionLength(68.0));
		}
		
		if(!(((strat.getExtensionLength(80.0) - 0.982)) < 0.001))
		{
			throw new AssertionError("80 ANGLE TEST ERROR: " + strat.getExtensionLength(80.0));
		}
		
		if(!(strat.getExtensionLength(190.0) == 0.0))
		{
			throw new AssertionError("190 ANGLE TEST ERROR: " + strat.getExtensionLength(190.0));
		}
	}
	
	public static void testFixedAngleStrat(FixedAngleStrat strat)
	{
		if(!(strat.getExtensionLength(-5.0) == 0.0))
		{
			throw new AssertionError("MINUS FIVE ANGLE TEST ERROR: " + strat.getExtensionLength(-5.0));
		}
		
		if(!(strat.getExtensionLength(0.0) == 0.0))
		{
			throw new AssertionError("ZERO ANGLE TEST ERROR: " + strat.getExtensionLength(0.0));
		}
		
		if(!(strat.getExtensionLength(7.0) == 0.0))
		{
			throw new AssertionError("SEVEN ANGLE TEST ERROR: " + strat.getExtensionLength(7.0));
		}
		
		if(!(strat.getExtensionLength(14.0) == 10.75))
		{
			throw new AssertionError("14 ANGLE TEST ERROR: " + strat.getExtensionLength(14.0));
		}
		if(!(strat.getExtensionLength(18.0) == 0.0))
		{
			throw new AssertionError("18 ANGLE TEST ERROR: " + strat.getExtensionLength(18.0));
		}
		if(!(strat.getExtensionLength(68.0) == 0.0))
		{
			throw new AssertionError("68 ANGLE TEST ERROR: " + strat.getExtensionLength(68.0));
		}
		
		if(!(strat.getExtensionLength(80.0) == 0.0))
		{
			throw new AssertionError("80 ANGLE TEST ERROR: " + strat.getExtensionLength(80.0));
		}
		if(!(strat.getExtensionLength(120.0) == 10.75))
		{
			throw new AssertionError("120 ANGLE TEST ERROR: " + strat.getExtensionLength(120.0));
		}
		if(!(strat.getExtensionLength(190.0) == 10.75))
		{
			throw new AssertionError("190 ANGLE TEST ERROR: " + strat.getExtensionLength(190.0));
		}

	}
	
	public static void testCubePosStrat(CubePositionStrat strat)
	{
		if(!(strat.getExtensionLength(-5.0) == 0.0))
		{
			throw new AssertionError("MINUS FIVE ANGLE TEST ERROR: " + strat.getExtensionLength(-5.0));
		}
		
		if(!(strat.getExtensionLength(0.0) == 0.0))
		{
			throw new AssertionError("ZERO ANGLE TEST ERROR: " + strat.getExtensionLength(0.0));
		}
		
		if(!(strat.getExtensionLength(7.0) == 0.0))
		{
			throw new AssertionError("SEVEN ANGLE TEST ERROR: " + strat.getExtensionLength(7.0));
		}
		
		if(!(strat.getExtensionLength(14.0) == 0))
		{
			throw new AssertionError("14 ANGLE TEST ERROR: " + strat.getExtensionLength(14.0));
		}
		if(!(strat.getExtensionLength(18.0) == 6.0))
		{
			throw new AssertionError("18 ANGLE TEST ERROR: " + strat.getExtensionLength(18.0));
		}
		if(!(strat.getExtensionLength(43.0) == 6.0))
		{
			throw new AssertionError("43 ANGLE TEST ERROR: " + strat.getExtensionLength(43.0));
		}
		if(!(strat.getExtensionLength(56.0) == 4.0))
		{
			throw new AssertionError("56 ANGLE TEST ERROR: " + strat.getExtensionLength(68.0));
		}
		if(!(strat.getExtensionLength(68.0) == 4.0))
		{
			throw new AssertionError("68 ANGLE TEST ERROR: " + strat.getExtensionLength(68.0));
		}
		if(!(strat.getExtensionLength(80.0) == 7.5))
		{
			throw new AssertionError("80 ANGLE TEST ERROR: " + strat.getExtensionLength(80.0));
		}
		if(!(strat.getExtensionLength(120.0) == 10.75))
		{
			throw new AssertionError("120 ANGLE TEST ERROR: " + strat.getExtensionLength(120.0));
		}
		if(!(strat.getExtensionLength(190.0) == 12.0))
		{
			throw new AssertionError("190 ANGLE TEST ERROR: " + strat.getExtensionLength(190.0));
		}

	}

}
