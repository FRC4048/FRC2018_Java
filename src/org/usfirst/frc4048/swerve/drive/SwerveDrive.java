package org.usfirst.frc4048.swerve.drive;

import org.usfirst.frc4048.swerve.math.CentricMode;
import org.usfirst.frc4048.swerve.math.SwerveMath;
import org.usfirst.frc4048.swerve.math.SwerveDirective;

import java.util.List;

public class SwerveDrive {
	
	private SwerveEnclosure swerveEnclosure1;
	private SwerveEnclosure swerveEnclosure2;
	private SwerveEnclosure swerveEnclosure3;
	private SwerveEnclosure swerveEnclosure4;
	
	private final SwerveMath swerveMath;
	
	public SwerveDrive(SwerveEnclosure swerveEnclosure1,
					   SwerveEnclosure swerveEnclosure2,
					   SwerveEnclosure swerveEnclosure3,
					   SwerveEnclosure swerveEnclosure4,
					   double width, double length) 
	{
		this.swerveEnclosure1 = swerveEnclosure1;
		this.swerveEnclosure2 = swerveEnclosure2;
		this.swerveEnclosure3 = swerveEnclosure3;
		this.swerveEnclosure4 = swerveEnclosure4;
		
		swerveMath = new SwerveMath(width, length);
	}
	
	public void move(double fwd, double str, double rcw, Double gyroValue)
	{
		List<SwerveDirective> swerveDirectives = swerveMath.move(fwd, str, rcw, gyroValue);
		
		swerveEnclosure1.move(swerveDirectives.get(0).getSpeed(), swerveDirectives.get(0).getAngle());
		swerveEnclosure1.move(swerveDirectives.get(1).getSpeed(), swerveDirectives.get(1).getAngle());
		swerveEnclosure1.move(swerveDirectives.get(2).getSpeed(), swerveDirectives.get(2).getAngle());
		swerveEnclosure1.move(swerveDirectives.get(3).getSpeed(), swerveDirectives.get(3).getAngle());
	}
	
	public void stop()
	{
		swerveEnclosure1.stop();
		swerveEnclosure2.stop();
		swerveEnclosure3.stop();
		swerveEnclosure4.stop();
	}
	
	public void setCentricMode(CentricMode centricMode)
	{
		this.swerveMath.setCentricMode(centricMode);
	}
}
