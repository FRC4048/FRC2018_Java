package org.usfirst.frc4048.swerve.drive;

public abstract class BaseEnclousre implements SwerveEnclosure{
	
	private String name;
	protected double gearRatio;
	
	public BaseEnclousre(String name, double gearRatio)
	{
		this.name = name;
		this.gearRatio = gearRatio;
	}
	
	@Override
	public void move(double speed, double angle)
	{
		int encPosition = getEncPosition();
		angle = convertAngle(angle, encPosition);
		
		if(shouldReverse(angle, encPosition))
		{
			if(angle < 0)
				angle += 0.5;
			else
				angle -= 0.5;
			
			speed *= -1.0;
		}
		
		setSpeed(speed);
		
		setAngle(angle);
	}

	public String getName()
	{
		return name;
	}
	
	protected abstract int getEncPosition();
	
	protected abstract void setEncPosition(int encPosition);
	
	protected abstract void setSpeed(double speed);
	
	protected abstract void setAngle(double angle);
	
	private boolean shouldReverse(double wa, double encoderValue)
	{
		double ea = SwerveUtils.convertEncoderValue(encoderValue, encoderValue);
		
		if(wa < 0)	wa += 1;
		
		double longDiff = Math.abs(wa - ea);
		
		double diff = Math.min(longDiff, 1.0-longDiff);
		
		if(diff > 0.25) return true;
		else return false;
	}
	
	private double convertAngle(double angle, double encoderValue)
	{
		double encPos = encoderValue / gearRatio;
		
		double temp = angle;
		temp += (int)encPos;
		
		encPos = encPos % 1;
		
		if((angle - encPos) >  0.5) temp -= 1;
		if((angle - encPos) < -0.5) temp += 1;
		
		return temp;
	}
}
