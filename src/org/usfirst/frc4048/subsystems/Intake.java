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

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Intake extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController leftIntakeMotor = RobotMap.intakeleftIntakeMotor;
    private final SpeedController rightIntakeMotor = RobotMap.intakerightIntakeMotor;
    private final DigitalInput cubeSwitch = RobotMap.intakecubeSwitch;
    private final SpeedController deployMotor = RobotMap.intakedeployMotor;
    private final DigitalInput upperLimit = RobotMap.intakeupperLimit;
    private final DigitalInput lowerLimit = RobotMap.intakelowerLimit;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
//    private final Command getCubeCommand = new GetCube();
    private final Command getCubeCommand = new IntakeCube();
    
    private final double R_TRIGGER_MIN = 0.75;
    
    private final double RAISE_SPEED =  0.3;
    private final double LOWER_SPEED = -0.15;
    
    private final double ROLLER_SPEED = 0.4;
    private final double VARIED_ROLLER_SPEED = 0.2;

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
    	
    	if(Robot.oi.getXboxController().getTriggerAxis(Hand.kRight) > R_TRIGGER_MIN 
    		&& !getCubeCommand.isRunning() )
    	{
    		System.out.println("Starting Intake");
    		getCubeCommand.start();
    	}
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void raiseIntake()
    {
    	deployMotor.set(RAISE_SPEED);
    }
    
    public void lowerIntake()
    {
    	deployMotor.set(LOWER_SPEED);
    }
    
    public void intakeCube()
    {
    	leftIntakeMotor.set(ROLLER_SPEED);
    	rightIntakeMotor.set(ROLLER_SPEED);
    }
    
    public void adjustCube()
    {
    	leftIntakeMotor.set(ROLLER_SPEED);
    	rightIntakeMotor.set(VARIED_ROLLER_SPEED);
    }
    
    public void flushCube()
    {
    	leftIntakeMotor.set(-ROLLER_SPEED);
    	rightIntakeMotor.set(-ROLLER_SPEED);
    }
    
    public void stopIntake()
    {
    	leftIntakeMotor.stopMotor();
    	rightIntakeMotor.stopMotor();
    }
    
    public boolean hasCube()
    {
    	return !cubeSwitch.get();
    }
    
    public boolean isLowered()
    {
    	return lowerLimit.get();
    }
    
    public boolean isRaised()
    {
    	return upperLimit.get();
    }
    
}

