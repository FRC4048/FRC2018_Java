package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.arm.MoveClaw;
import org.usfirst.frc4048.utils.Logging;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Wrist extends Subsystem {

	private final WPI_TalonSRX pitchMotor = RobotMap.clawpitchMotor;
	private final ADXRS450_Gyro gyro = RobotMap.gyro;
	
	private final double ANGLE_UP_SPEED = 0.8;
	private final double ANGLE_DOWN_SPEED = -0.70;
    private final double ANGLE_LEVEL_UP_SPEED = 0.60;
    private final double ANGLE_LEVEL_DOWN_SPEED = -0.50;
    
    private final double LEVEL_GYRO_VAL = 102.0;
    private final double STRAIGHT_GYRO_VAL = 12.0;
    private final double GYRO_SLOW_MARGIN = 10.0;
    private final double GYRO_STOP_MARGIN = 2.5;
    
    private final double LEVEL_MAX_SPEED = 0.5;
	private final double LEVEL_MIN_SPEED = 0.1;
//	private final double LEVEL_MOTION_SPEED = 0.5*Robot.GLOBAL_SCALE_FACTOR;
	
    private final int TIMEOUT = 100;

    private WristPostion position = WristPostion.Compact;
    
    public enum WristPostion
    {
    	Compact,
    	Level,
    	Straight
    }
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Wrist()
    {
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
		
		//SmartDashboard.putNumber("WRIST ANGLE", 0.0);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new MoveClaw());
    }
    
    @Override
    public void periodic()
    {
    	Robot.logging.traceSubsystem(Logging.Subsystems.WRIST, false, 
				""+gyro.getAngle(),
				position.toString());
    }
    
    /**
     * Angles the grip upward towards the compact position
     */
    public void angleUp()
    {
    	pitchMotor.set(ControlMode.PercentOutput, ANGLE_UP_SPEED);
    }
    
    /**
     * Angles the grip downward
     */
    public void angleDown()
    {
    	pitchMotor.set(ControlMode.PercentOutput, ANGLE_LEVEL_DOWN_SPEED);
    }
    
    /**
     * Returns if the claw is in the compact position
     * @return True if compact, false if not
     */
    public boolean clawUp()
    {
    	return pitchMotor.getSensorCollection().isFwdLimitSwitchClosed();
    }
    
    /**
     * Returns if the claw is fully down (hitting lower limit switch)
     * @return True if fully down, false if not
     */
    public boolean clawDown()
    {
    	return pitchMotor.getSensorCollection().isRevLimitSwitchClosed();
    }
    
    public boolean isLevel()
    {
    	double gyro = getGyroVal();
    	return gyro >= LEVEL_GYRO_VAL - GYRO_STOP_MARGIN && gyro <= LEVEL_GYRO_VAL + GYRO_STOP_MARGIN;
    }
    
    public boolean isStraight()
    {
    	double gyro = getGyroVal();
    	return gyro >= STRAIGHT_GYRO_VAL - GYRO_STOP_MARGIN && gyro <= STRAIGHT_GYRO_VAL + GYRO_STOP_MARGIN;
    }
    
    public void stopWrist()
    {
    	pitchMotor.stopMotor();
    }
    
    public void recalibrateClawGyro()
    {
    	gyro.reset();
    	gyro.calibrate();
    }
    
    /**
     * Returns the enum value that the claw is traveling to
     * @return Enum that arm is traveling to
     */
    public WristPostion getPosition()
    {
    	return position;
    }
    
    public void setPosition(WristPostion newPos){
    	position = newPos;
    }
    
    /**
     * Levels using makeshift PID
     */
    public void moveClawToLevelWithPID()
    {
    	double error;
    	double speed = 0.0;
    	final double currAngle = gyro.getAngle();
    	double angle = LEVEL_GYRO_VAL;
    	double kP = 100;
    	
    	if(Math.abs(angle - currAngle) < GYRO_STOP_MARGIN)
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
	    	
	    	speed = (error / kP);
	    	if (error < 0)
	    		speed -= LEVEL_MIN_SPEED;
	    	else
	    		speed += LEVEL_MIN_SPEED;
	    	
	        if (Math.abs(angle - currAngle) < GYRO_STOP_MARGIN) speed = 0;	
    	}
    	
    	speed *= -1;
    	
    	//SmartDashboard.putNumber("CLAW PITCH SPEED", speed);
    	
    	pitchMotor.set(ControlMode.PercentOutput, speed);
    }
    
    /**
     * Levels without PID, but looks like PID
     */
    public void moveClawToLevel()
    {
    	double angle = getGyroVal(); 
    	if(angle < LEVEL_GYRO_VAL - GYRO_STOP_MARGIN) {
    		if(angle < LEVEL_GYRO_VAL - GYRO_SLOW_MARGIN) {
    			pitchMotor.set(ANGLE_DOWN_SPEED);
    		} else {
    			pitchMotor.set(ANGLE_LEVEL_DOWN_SPEED);
    		}
    	}
    	else if(angle > LEVEL_GYRO_VAL + GYRO_STOP_MARGIN) {
    		if(angle > LEVEL_GYRO_VAL + GYRO_SLOW_MARGIN) {
    			pitchMotor.set(ANGLE_UP_SPEED);
    		} else {
    			pitchMotor.set(ANGLE_LEVEL_UP_SPEED);
    		}
    	} else {
    		pitchMotor.stopMotor();
    	}
    }
    
    public void moveClawToLevelOrig()
    {
    	if(getGyroVal() > LEVEL_GYRO_VAL + GYRO_STOP_MARGIN) {
    		pitchMotor.set(ANGLE_LEVEL_UP_SPEED);
    	}
    	else if(getGyroVal() < LEVEL_GYRO_VAL - GYRO_STOP_MARGIN) {
    		pitchMotor.set(ANGLE_LEVEL_DOWN_SPEED);
    	}
    	else
    	{
    		pitchMotor.stopMotor();
    	}
    }
    
    public void moveClawToStraight()
    {
    	double angle = getGyroVal(); 
    	if(angle < STRAIGHT_GYRO_VAL - GYRO_STOP_MARGIN) {
    		if(angle < STRAIGHT_GYRO_VAL - GYRO_SLOW_MARGIN) {
    			pitchMotor.set(ANGLE_DOWN_SPEED);
    		} else {
    			pitchMotor.set(ANGLE_LEVEL_DOWN_SPEED);
    		}
    	}
    	else if(angle > STRAIGHT_GYRO_VAL + GYRO_STOP_MARGIN) {
    		if(angle > STRAIGHT_GYRO_VAL + GYRO_SLOW_MARGIN) {
    			pitchMotor.set(ANGLE_UP_SPEED);
    		} else {
    			pitchMotor.set(ANGLE_LEVEL_UP_SPEED);
    		}
    	} else {
    		pitchMotor.stopMotor();
    	}
    }
    
    public void moveClawToStraightOrig()
    {
    	if(getGyroVal() > STRAIGHT_GYRO_VAL + GYRO_STOP_MARGIN) {
    		pitchMotor.set(ANGLE_LEVEL_UP_SPEED);
    	}
    	else if(getGyroVal() < STRAIGHT_GYRO_VAL - GYRO_STOP_MARGIN) {
    		pitchMotor.set(ANGLE_LEVEL_DOWN_SPEED);
    	}
    	else
    	{
    		pitchMotor.stopMotor();
    	}
    }
    
    public double getGyroVal()
    {
    	return gyro.getAngle() *-1;
    }
    
    public String[] wristHeadings() {
    	String log[] = {"Gyro Angle", "Position"};
    	return log;
    }
}
