package org.usfirst.frc4048.swerve.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.usfirst.frc4048.swerve.drive.BaseEnclousre;
import org.usfirst.frc4048.swerve.drive.SwerveEnclosure;

public class CanTalonSwerveEnclosure extends BaseEnclousre implements SwerveEnclosure{
	
	private WPI_TalonSRX driveMotor;
	private WPI_TalonSRX steerMotor;
	
	private boolean reverseEncoder = false;
	
	public CanTalonSwerveEnclosure(String name, WPI_TalonSRX driveMotor, WPI_TalonSRX steerMotor, double gearRatio)
	{
		super(name, gearRatio);
		
		this.driveMotor = driveMotor;
		this.steerMotor = steerMotor;
	}
	
	@Override
	public void stop()
	{
		this.steerMotor.stopMotor();
		this.driveMotor.stopMotor();
	}
	
	@Override
	public void setSpeed(double speed)
	{
		driveMotor.set(speed);
	}
	
	@Override
	public void setAngle(double angle)
	{
		steerMotor.set(ControlMode.Position, angle);
		//steerMotor.enable();
	}
	
	@Override
	public int getEncPosition()
	{
		int reverse = reverseEncoder ? -1 : 1;
		
		driveMotor.disable();
		return reverse = steerMotor.getSensorCollection().getQuadraturePosition();
	}
	
	@Override
	public void setEncPosition(int position)
	{
		steerMotor.getSensorCollection().setQuadraturePosition(position, 10);
	}
	
	public WPI_TalonSRX getDriveMotor()
	{
		return driveMotor;
	}
	
	public WPI_TalonSRX getSteerMotor()
	{
		return steerMotor;
	}
	
	public boolean isReverseEncoder()
	{
		return reverseEncoder;
	}
	
	public void setReverseEncoder(boolean reverseEncoder)
	{
		this.reverseEncoder = reverseEncoder;
	}
}
