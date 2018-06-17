package org.usfirst.frc4048.arm.math;

public class ArmMath {
	public double convertPotToAngle(double potMax, double potMin, double angleMax, double angleMin, double inputPot) {
		double output = ((angleMax-angleMin)/(potMax-potMin)) * (inputPot - potMin) + angleMin;
		return output; 
	}
	
	public double convertAngleToPot(double potMax, double potMin, double angleMax, double angleMin, double inputAngle) {
		double output = potMin - ((potMin-potMax)/(angleMax-angleMin)) * (inputAngle - angleMin);
		return output;
	}
}