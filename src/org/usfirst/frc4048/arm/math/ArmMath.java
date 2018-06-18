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
	
	public double convertElbowEncToAngle(double encMax, double encMin, double angleMax, double angleMin, double inputEnc) {
		double output = ((angleMax-angleMin)/(encMax-encMin)) * (inputEnc - encMin) + angleMin;
		return output;
	}
	
	public double convertEblowAngleToEnc(double encMax, double encMin, double angleMax, double angleMin, double inputAngle) {
		double output = encMin + ((encMax- encMin)/(angleMax-angleMin)) * (inputAngle - angleMin);
		return output;
	}
}