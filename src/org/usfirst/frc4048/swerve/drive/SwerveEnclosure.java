package org.usfirst.frc4048.swerve.drive;

public interface SwerveEnclosure {

	String getName();
	
	void move(double speed, double angle);
	
	void stop();
}
