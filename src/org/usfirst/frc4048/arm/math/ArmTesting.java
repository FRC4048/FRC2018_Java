package org.usfirst.frc4048.arm.math;

public class ArmTesting {

	public static double ARM_MIN_POT = 0.0;
	public static double ARM_MAX_POT = 5.0;
	public static double ARM_MIN_ANGLE = 00.0;
	public static double ARM_MAX_ANGLE = 120.0;
	
	public static double EXT_MIN_POT = 0.0;
	public static double EXT_MAX_POT = 5.0;
	public static double EXT_MIN_LENGTH = 2.0;
	public static double EXT_MAX_LENGTH = 15.0;
	
	public static void main(String[] args)
	{
		ArmMath math = new ArmMath();
		for(double armPot = 0.0; armPot <= ARM_MAX_POT; armPot += 0.05)
		{
			double angle = math.convertPotToAngle(ARM_MIN_POT, ARM_MIN_ANGLE, ARM_MAX_POT, ARM_MAX_ANGLE, armPot);		
			double potValue = math.convertArmAngleToExtPot(EXT_MIN_POT, EXT_MIN_LENGTH, EXT_MAX_POT, EXT_MAX_LENGTH, angle);
			System.out.printf("ARM POT: %-2.4f\t\tARM ANGLE: %-2.4f\tEXT POT: %-2.4f\n", armPot, angle, potValue);
		}
	}
}
