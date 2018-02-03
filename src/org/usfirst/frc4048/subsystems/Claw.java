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
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
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
    private final WPI_TalonSRX pitchMotor = RobotMap.clawpitchMotor;
    private final DigitalInput cubeSwitch = RobotMap.clawcubeSwitch;
    private final AnalogInput pressureSensor = RobotMap.clawpressureSensor;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    private final double PRESSURE_VALUE = 2.75;
    private final double CLOSE_SPEED = -0.6;
    private final double OPEN_SPEED = 0.6;
    
    private final double ANGLE_UP_SPEED = 0.4;
    private final double ANGLE_DOWN_SPEED = -0.3;
    
    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public boolean aboveCube()
    {
    	return cubeSwitch.get();
    }
    
    
    public boolean hasCube()
    {
    	if(pressureSensor.getVoltage() >= PRESSURE_VALUE)
    		return true;
    	else
    		return false;
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
    
    public void stopWrist()
    {
    	pitchMotor.stopMotor();
    }
}

