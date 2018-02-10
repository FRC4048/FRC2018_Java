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

import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.*;
import org.usfirst.frc4048.commands.arm.ArmFinetune;
import org.usfirst.frc4048.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


public class Arm extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final WPI_TalonSRX extensionMotor = RobotMap.armextensionMotor;
    private final AnalogPotentiometer rotationPot = RobotMap.armrotationPot;
    private final WPI_TalonSRX movementMotor = RobotMap.armmovementMotor;
    private final AnalogPotentiometer extensionPot = RobotMap.armextensionPot;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS 
    
    private final int TIMEOUT = 100;
    
    private final double EXT_P = 10.0;
    private final double EXT_I = 0.0;
    private final double EXT_D = 0.0;
    
    private final double ARM_P = 10.0;
    private final double ARM_I = 0.0;
    private final double ARM_D = 0.0;
    
    private final double FINETUNE_SPEED = 0.01;
    private final double ARM_STOP_SPEED = 0.05;
    
    private double armSetpoint;
    
    private final int RANGE_VALUE = 5;
    private final int EXCHANGE_SETPOINT = 200;
    private final int SWITCH_SETPOINT = 500;
    private final int LOWSCALE_SETPOINT = 800;
    private final int HIGHSCALE_SETPOINT = 1000;
    private final int CLIMBER_SETPOINT = 1200;
    private final int HOME_SETPOINT = 300;
    
	public static enum ArmPositions
	{
		Intake,
		Exchange,
		Switch,
		LowScale,
		HighScale,
		Climb,
		Home
	}
	
	private PIDController armController = new PIDController(ARM_P, ARM_I, ARM_D, rotationPot, movementMotor);
	
	public Arm() {
		super("Arm");
		
		extensionMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
		extensionMotor.selectProfileSlot(0, 0);
    	extensionMotor.configPeakOutputForward(1, TIMEOUT);
    	extensionMotor.configPeakOutputReverse(-1, TIMEOUT);
    	extensionMotor.configNominalOutputForward(0, TIMEOUT);
    	extensionMotor.configNominalOutputReverse(0, TIMEOUT);
		extensionMotor.setNeutralMode(NeutralMode.Brake);
		extensionMotor.configAllowableClosedloopError(0, 4, TIMEOUT);
		extensionMotor.config_kP(0, EXT_P, TIMEOUT);
		extensionMotor.config_kI(0, EXT_I, TIMEOUT);
		extensionMotor.config_kD(0, EXT_D, TIMEOUT);
		
		movementMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
		movementMotor.selectProfileSlot(0, 0);
    	movementMotor.configPeakOutputForward(1, TIMEOUT);
    	movementMotor.configPeakOutputReverse(-1, TIMEOUT);
    	movementMotor.configNominalOutputForward(0, TIMEOUT);
    	movementMotor.configNominalOutputReverse(0, TIMEOUT);
		movementMotor.setNeutralMode(NeutralMode.Brake);
		movementMotor.configAllowableClosedloopError(0, 4, TIMEOUT);
		movementMotor.config_kP(0, ARM_P, TIMEOUT);
		movementMotor.config_kI(0, ARM_I, TIMEOUT);
		movementMotor.config_kD(0, ARM_D, TIMEOUT);
		
		armSetpoint = getArmPos();
		
		//Used for test bed
		armController.enable();
	}
	
    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ArmFinetune());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    	
    	moveArm();
    	moveExtension();
    	   	
    	SmartDashboard.putNumber("Setpoint", armSetpoint);
    	SmartDashboard.putNumber("Current Value", getArmPos());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public void finetuneUp()
    {
    	armSetpoint += FINETUNE_SPEED;
    }
    
    public void finetuneDown()
    {
    	armSetpoint -= FINETUNE_SPEED;
    }
    
    public void stopArm()
    {
//    	movementMotor.set(ControlMode.Position, armSetpoint);
    	armController.setSetpoint(armSetpoint);
    }
    
//    public int getArmPos()
//    {
//    	return movementMotor.getSelectedSensorPosition(0);
//    }
    
    //Used for software pid
    public double getArmPos()
    {
    	return rotationPot.get();
    }
    
    public int getExtPos()
    {
    	return extensionMotor.getSelectedSensorPosition(0);
    }
    
    /**
     * Checks to see if the arm is within the correct position
     * @param position
     * @return True if arm is in correct position., False otherwise
     */
    public boolean armAtPosition(ArmPositions position)
    {
//    	int armPos = getArmPos();
    	switch (position) {
//		case Exchange:			
//			return armPos >= EXCHANGE_SETPOINT - RANGE_VALUE && armPos <= EXCHANGE_SETPOINT + RANGE_VALUE;	
//		case Switch:
//			return armPos >= SWITCH_SETPOINT - RANGE_VALUE && armPos <= SWITCH_SETPOINT + RANGE_VALUE;
//		case LowScale:
//			return armPos >= LOWSCALE_SETPOINT - RANGE_VALUE && armPos <= LOWSCALE_SETPOINT + RANGE_VALUE;
//		case HighScale:
//			return armPos >= HIGHSCALE_SETPOINT - RANGE_VALUE && armPos <= HIGHSCALE_SETPOINT + RANGE_VALUE;
//		case Climb:
//			return armPos >= CLIMBER_SETPOINT - RANGE_VALUE && armPos <= CLIMBER_SETPOINT + RANGE_VALUE;
//		case Home:
//			return armPos >= HOME_SETPOINT - RANGE_VALUE && armPos <= HOME_SETPOINT + RANGE_VALUE;
		default:
			return false;
		}
    }
    
    public void moveToPos(ArmPositions pos)
    {
    	switch (pos) {
		case Exchange:			
			armSetpoint = EXCHANGE_SETPOINT;
			break;
		case Switch:
			armSetpoint = SWITCH_SETPOINT;
			break;
		case LowScale:
			armSetpoint = LOWSCALE_SETPOINT;
			break;
		case HighScale:
			armSetpoint = HIGHSCALE_SETPOINT;
			break;
		case Climb:
			armSetpoint = CLIMBER_SETPOINT;
			break;
		case Home:
			armSetpoint = HOME_SETPOINT;
			break;
		default:
			break;
		}
    }
    
    /**
     * Uses arm math to calculate new position for extension
     */
    private void moveExtension()
    {
    	
    }
    
    /**
     * Keeps arm locked to its current setpoint position
     */
    private void moveArm()
    {
//    	movementMotor.set(ControlMode.Position, (int) armSetpoint);
    	
    	//Used for software pid
    	armController.setSetpoint(armSetpoint);
    }
}

