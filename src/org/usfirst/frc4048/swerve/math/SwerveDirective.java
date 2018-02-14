package org.usfirst.frc4048.swerve.math;

public class SwerveDirective {
	private double angle;
	private double speed;

	
	public SwerveDirective() { 
	}

	
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

	public void setAngle(double angle) {
		this.angle = angle;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}
}