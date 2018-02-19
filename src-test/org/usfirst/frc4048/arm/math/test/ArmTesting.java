package org.usfirst.frc4048.arm.math.test;

import java.io.FileWriter;
import java.io.IOException;

import org.usfirst.frc4048.arm.math.*;
import org.usfirst.frc4048.subsystems.Arm;

public class ArmTesting {

	public static double FIXED_ARM_LENGTH = 44;
	public static double ARM_MIN_POT = 935;
	public static double ARM_MAX_POT = 84;
	public static double ARM_MIN_ANGLE = 0;
	public static double ARM_MAX_ANGLE = 158;
	
	public static double EXT_MIN_POT = 638;
	public static double EXT_MAX_POT = 321;
	public static double EXT_MIN_LENGTH = 0.0;
	public static double EXT_MAX_LENGTH = 15.25;
	
	public static void main(String[] args) throws IOException
	{
		ArmMath math = new ArmMath();
		LinearMoveStrat stratLinear = new LinearMoveStrat();
		/*System.out.println("STARTING LINEAR TEST");
		testLinearStrat(stratLinear);
		System.out.println("ENDING LINEAR TEST");*/
		
		FixedAngleStrat stratFixed = new FixedAngleStrat();
		/*System.out.println("STARTING FIXED ANGLE TEST");
		testFixedAngleStrat(stratFixed);
		System.out.println("ENDING FIXED ANGLE TEST");*/
		
		CubePositionStrat stratCube = new CubePositionStrat();
		/*System.out.println("STARTING CUBE ANGLE TEST");
		testCubePosStrat(stratCube);
		System.out.println("ENDING CUBE ANGLE TEST");*/
		
		//ArmMath armMath = new ArmMath();
		/*System.out.println("START ARM MATH TEST");
		testArmMath(armMath);
		System.out.println("END ARM MATH TEST");*/
		
		FileWriter linearWriter = new FileWriter("LinearStrat.csv");
		FileWriter fixedAngleWriter = new FileWriter("FixedAngleStrat.csv");
		FileWriter cubePositionWriter = new FileWriter("CubePositionStrat.csv");
		
		String[] stratStrings = new String[3];
		
//		for(int i = 0; i < stratStrings.length; i++)
//		{
			stratStrings[0]="Pot Value";
			stratStrings[0]+=',';
			stratStrings[0]+="Angle";
			stratStrings[0]+=',';
			stratStrings[0]+="Pot Value Back";
			stratStrings[0]+=',';
			stratStrings[0]+="Extension Length";
			stratStrings[0]+="\n";
//		}	
		
		for(double pot = -ARM_MIN_POT; pot < -ARM_MAX_POT; pot+=5)
		{
			stratStrings[0]+=pot;
			stratStrings[0]+=',';
			double angle = math.convertPotToAngle(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, pot*-1);
			stratStrings[0]+= angle;
			stratStrings[0]+=',';
			stratStrings[0]+= math.convertAngleToPot(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, angle) *-1;
//			stratStrings[0]+=(stratLinear.getExtensionLength(pot) + FIXED_ARM_LENGTH)*Math.sin(Math.toRadians(angle+22));
			stratStrings[0]+=',';
			stratStrings[0]+=stratLinear.getExtensionLength(pot);
			stratStrings[0]+='\n';
			
//			stratStrings[1]+=angle;
//			stratStrings[1]+=(',');
//			stratStrings[1]+=((stratFixed.getExtensionLength(angle) + FIXED_ARM_LENGTH)*Math.sin(Math.toRadians(angle+22)));
//			stratStrings[1]+=(',');
//			stratStrings[1]+=stratFixed.getExtensionLength(angle);
//			stratStrings[1]+=('\n');
//			
//			stratStrings[2]+=(angle);
//			stratStrings[2]+=(',');
//			stratStrings[2]+=((stratCube.getExtensionLength(angle) + FIXED_ARM_LENGTH)*Math.sin(Math.toRadians(angle+22)));
//			stratStrings[2]+=(',');
//			stratStrings[2]+=stratCube.getExtensionLength(angle);
//			stratStrings[2]+=('\n');
			
		}
		
		linearWriter.append(stratStrings[0].toString());
//		fixedAngleWriter.append(stratStrings[1].toString());
//		cubePositionWriter.append(stratStrings[2].toString());
		
		linearWriter.flush();
		linearWriter.close();
//		fixedAngleWriter.flush();
//		fixedAngleWriter.close();
//		cubePositionWriter.flush();
//		cubePositionWriter.close();
		
		System.out.println("done");
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
		
		if(!(((strat.getExtensionLength(10.0) == 0.00))))
		{
			throw new AssertionError("10 ANGLE TEST ERROR: " + strat.getExtensionLength(10.0));
		}
		
		System.out.println("15 ANGLE TEST RESULT: " + strat.getExtensionLength(15.0));
		if(!(((strat.getExtensionLength(15.0) == 15))))
		{
			throw new AssertionError("15 ANGLE TEST ERROR: " + strat.getExtensionLength(15.0));
		}
		
		if(!(((strat.getExtensionLength(68.0) == 0.00))))
		{
			throw new AssertionError("68 ANGLE TEST ERROR: " + strat.getExtensionLength(68.0));
		}
		
		System.out.println("80 ANGLE TEST RESULT: " + strat.getExtensionLength(80.0));
		if(!((Math.abs(strat.getExtensionLength(80.0) - 0.982)) < 0.001))
		{
			throw new AssertionError("80 ANGLE TEST ERROR: " + strat.getExtensionLength(80.0));
		}
		
		if(!(((strat.getExtensionLength(136.0) == 15.00))))
		{
			throw new AssertionError("136 ANGLE TEST ERROR: " + strat.getExtensionLength(136.0));
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

	public static void testArmMath(ArmMath armMath)
	{
		final double ARM_MIN_POT = 0.9;
		final double ARM_MAX_POT = 4.3;
		final double ARM_MIN_ANGLE = 0.0;
		final double ARM_MAX_ANGLE = 158.0;
		
		final double EXT_MIN_POT = 0.7;
		final double EXT_MAX_POT = 5.0;
		final double EXT_MIN_LENGTH = 0.0;
		final double EXT_MAX_LENGTH = LinearMoveStrat.MAX_EXTENSION;
		
		assert armMath.convertPotToAngle(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, 0.0) == 0 : 
			"0 POT TEST ERROR: " + armMath.convertPotToAngle(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, 0.0); 
		
		assert armMath.convertPotToAngle(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, 4.5) == 158.0 :
			"4.5 POT TEST ERROR: " + armMath.convertPotToAngle(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, 4.5);
		
		assert armMath.convertPotToAngle(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, 2.6) == 79 :
			"2.6 POT TEST ERROR: " + armMath.convertPotToAngle(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, 2.6);
		
		assert armMath.convertPotToAngle(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, 1.75) == 39.5 :
			"1.75 POT TEST ERROR: " + armMath.convertPotToAngle(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, 1.3);
		
		assert armMath.convertPotToAngle(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, 3.45) == 118.50000000000001:
			"3.45 POT TEST ERROR: " + armMath.convertPotToAngle(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, 3.45);
		
		
		assert armMath.convertArmAngleToExtPot(EXT_MIN_POT, EXT_MIN_LENGTH, EXT_MAX_POT, EXT_MAX_LENGTH, -5.0) == 0.7 : 
			"-5.0 ANGLE TO EXT POT ERROR: " + armMath.convertArmAngleToExtPot(EXT_MIN_POT, EXT_MIN_LENGTH, EXT_MAX_POT, EXT_MAX_LENGTH, -5.0);
		
		assert armMath.convertArmAngleToExtPot(EXT_MIN_POT, EXT_MIN_LENGTH, EXT_MAX_POT, EXT_MAX_LENGTH, 0.0) == 0.7 : 
			"0.0 ANGLE TO EXT POT ERROR: " + armMath.convertArmAngleToExtPot(EXT_MIN_POT, EXT_MIN_LENGTH, EXT_MAX_POT, EXT_MAX_LENGTH, -5.0);
		
		assert armMath.convertArmAngleToExtPot(EXT_MIN_POT, EXT_MIN_LENGTH, EXT_MAX_POT, EXT_MAX_LENGTH, 158) == 5.0 : 
			"158.0 ANGLE TO EXT POT ERROR: " + armMath.convertArmAngleToExtPot(EXT_MIN_POT, EXT_MIN_LENGTH, EXT_MAX_POT, EXT_MAX_LENGTH, 158.0);
		
		assert armMath.convertArmAngleToExtPot(EXT_MIN_POT, EXT_MIN_LENGTH, EXT_MAX_POT, EXT_MAX_LENGTH, 175) == 0.7 : 
			"175.0 ANGLE TO EXT POT ERROR: " + armMath.convertArmAngleToExtPot(EXT_MIN_POT, EXT_MIN_LENGTH, EXT_MAX_POT, EXT_MAX_LENGTH, 175.0);
	}
}
