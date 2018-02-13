package org.usfirst.frc4048.swerve.math;

public class SwerveDirective {
	private double angle;
	private double speed;

	public SwerveDirective(double angle, double speed) { 
		this.angle = angle; 
		this.speed = speed; 
	}

	public double getAngle() {
		return angle;
	}

	public double getSpeed() {
		return speed;
	}
}