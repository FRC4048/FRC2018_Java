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
//import org.usfirst.frc4048.commands.arm.MoveClaw;
import org.usfirst.frc4048.utils.Logging;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final WPI_TalonSRX gripMotor = RobotMap.clawgripMotor;
    private final DigitalInput cubeSwitch = RobotMap.clawcubeSwitch;
//    private final AnalogInput pressureSensor = RobotMap.clawpressureSensor;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    private final double CLOSE_SPEED = -0.6;//*Robot.GLOBAL_SCALE_FACTOR;
    private final double OPEN_SPEED = 0.6;//*Robot.GLOBAL_SCALE_FACTOR;
    
    private final int TIMEOUT = 100;
    
    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
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
    }

    
	public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(Logging.Subsystems.CLAW) {

		@Override
		protected void addAll() {
			add("Cube Present", cubePresent());
		}

	};

    
    @Override
    public void periodic() {
        // Put code here to be run every loop
    	
    	loggingContext.writeData();
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public boolean cubePresent()
    {
    	return !cubeSwitch.get();
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
        
}

