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

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

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
    
    private final double RAISE_SPEED =  0.3;
    private final double LOWER_SPEED = -0.15;
    
    // TODO Adjust roller speed for intake as needed
    
	/**
	 * Approximately 8/12 volts.
	 */
    private final double ROLLER_SPEED = 0.67;
    
    /**
     * Approximately 4/12 volts.
     */
    private final double VARIED_ROLLER_SPEED = 0.33;

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
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
    
    public void stopLowerOrRaiseIntake()
    {
    	deployMotor.set(0);
    }
    
    public void intakeCube()
    {
    	leftIntakeMotor.set(ROLLER_SPEED);
    	rightIntakeMotor.set(ROLLER_SPEED);
    }
    
    public void adjustCubeLeftSide()
    {
    	leftIntakeMotor.set(ROLLER_SPEED);
    	rightIntakeMotor.set(VARIED_ROLLER_SPEED);
    }
    
    public void adjustCubeRightSide()
    {
    	leftIntakeMotor.set(VARIED_ROLLER_SPEED);
    	rightIntakeMotor.set(ROLLER_SPEED);
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
    
    /**
     * Returns true when the switch is NOT pressed
     * @return
     */
    public boolean isLowered()
    {
    	return lowerLimit.get(); 
    }
    /**
     * Returns true when the switch is NOT pressed
     * @return
     */
    public boolean isRaised()
    {
    	return upperLimit.get();
    }
    
}

