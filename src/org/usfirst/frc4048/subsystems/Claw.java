// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.*;
import org.usfirst.frc4048.commands.arm.MoveClaw;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Claw extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    private final double LEVEL_MAX_SPEED = 0.5;
	private final double LEVEL_MIN_SPEED = 0.1;
	private final double LEVEL_MOTION_SPEED = 0.5;
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final WPI_TalonSRX gripMotor = RobotMap.clawgripMotor;
    private final WPI_TalonSRX pitchMotor = RobotMap.clawpitchMotor;
    private final DigitalInput cubeSwitch = RobotMap.clawcubeSwitch;
//    private final AnalogInput pressureSensor = RobotMap.clawpressureSensor;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    private final ADXRS450_Gyro gyro = RobotMap.gyro;
    
    //TODO Figure out current value to be used by grab cube
    private final double CURRENT_MAX_VALUE = 2.75;
    private final double CLOSE_SPEED = -0.6;
    private final double OPEN_SPEED = 0.6;
    
    private final double ANGLE_UP_SPEED = 0.4;
    private final double ANGLE_DOWN_SPEED = -0.3;
    private final double LEVEL_GYRO_VAL = 110.0;
    private final double LEVEL_GYRO_TOLERANCE = 0.05;
    private final int TIMEOUT = 100;
  
    
    public enum WristPostion
    {
    	Compact,
    	Level
    }
    
    private WristPostion position = WristPostion.Compact;
    		
    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new MoveClaw());
    }

    public Claw()
    {
    	super("Claw");
    	
    	gripMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
    											 LimitSwitchNormal.NormallyOpen, 
    											 TIMEOUT);
    	gripMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
				 								 LimitSwitchNormal.NormallyOpen, 
				 								 TIMEOUT);
		gripMotor.selectProfileSlot(0, 0);
    	gripMotor.configPeakOutputForward(1, TIMEOUT);
    	gripMotor.configPeakOutputReverse(-1, TIMEOUT);
    	gripMotor.configNominalOutputForward(0, TIMEOUT);
    	gripMotor.configNominalOutputReverse(0, TIMEOUT);
		gripMotor.setNeutralMode(NeutralMode.Brake);
		
		pitchMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
				 								 LimitSwitchNormal.NormallyOpen, 
				 								 TIMEOUT);
		pitchMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
				 								  LimitSwitchNormal.NormallyOpen, 
				 								  TIMEOUT);
		pitchMotor.selectProfileSlot(0, 0);
    	pitchMotor.configPeakOutputForward(1, TIMEOUT);
    	pitchMotor.configPeakOutputReverse(-1, TIMEOUT);
    	pitchMotor.configNominalOutputForward(0, TIMEOUT);
    	pitchMotor.configNominalOutputReverse(0, TIMEOUT);
		pitchMotor.setNeutralMode(NeutralMode.Brake);
		gyro.calibrate();
    }
    
    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public boolean cubePresent()
    {
    	return cubeSwitch.get();
    }

    public boolean gripClosed()
    {
    	return gripMotor.getSensorCollection().isRevLimitSwitchClosed();
    }
    
    public boolean gripOpen()
    {
    	return gripMotor.getSensorCollection().isFwdLimitSwitchClosed();
    }
    
    public void openClaw()
    {
    	gripMotor.set(OPEN_SPEED);
    }
    
    public void closeClaw()
    {
    	gripMotor.set(CLOSE_SPEED);
    }
    
    public void stopGrip()
    {
    	gripMotor.stopMotor();
    }
    
    public void angleUp()
    {
    	pitchMotor.set(ControlMode.PercentOutput, ANGLE_UP_SPEED);
    }
    
    public void angleDown()
    {
    	pitchMotor.set(ControlMode.PercentOutput, ANGLE_DOWN_SPEED);
    }
    
    public boolean clawUp()
    {
    	return pitchMotor.getSensorCollection().isFwdLimitSwitchClosed();
    }
    
    public boolean clawDown()
    {
    	return pitchMotor.getSensorCollection().isRevLimitSwitchClosed();
    }
    
    public void stopWrist()
    {
    	pitchMotor.stopMotor();
    }
    
    //TODO: can delete later if this is unnecessary
    public void recalibrateClawGyro()
    {
    	gyro.calibrate();
    }
    
    public WristPostion getPosition()
    {
    	return position;
    }
    
    public void setPosition(WristPostion newPos){
    	position = newPos;
    }
    
    public void moveClawToLevelWithPID()
    {
    	double error;
    	double speed = 0.0;
    	final double currAngle = gyro.getAngle();
    	double angle = LEVEL_GYRO_VAL;
    	
    	if(Math.abs(angle - currAngle) < LEVEL_GYRO_TOLERANCE)
    	{
    		speed = 0.0;
    	}
    	else
    	{	
	    	if(Math.abs(currAngle - angle) > 180)
	    	{
	    		if(currAngle > angle)
	    			angle += 360;
	    		else
	    			angle -= 360;
	    	}
	    	//180 is the maximum error
	    	error = angle - currAngle;
	    	
	    	speed = (error / 180) * (LEVEL_MAX_SPEED - LEVEL_MIN_SPEED);
	    	if (error < 0)
	    		speed -= LEVEL_MIN_SPEED;
	    	else
	    		speed += LEVEL_MIN_SPEED;
	    	
	        if (Math.abs(angle - currAngle) < LEVEL_GYRO_TOLERANCE) speed = 0;	
    	}
    	pitchMotor.set(ControlMode.PercentOutput, speed);
    }
    
    public void moveClawToLevel()
    {
    	if(!(Math.abs(gyro.getAngle() - LEVEL_GYRO_VAL) < LEVEL_GYRO_TOLERANCE)){
    		pitchMotor.set(ControlMode.PercentOutput, LEVEL_MOTION_SPEED);
    	}
    }
    
    public double getGyroVal()
    {
    	return gyro.getAngle();
    }
}

