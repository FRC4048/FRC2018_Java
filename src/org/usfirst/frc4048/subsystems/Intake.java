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
import org.usfirst.frc4048.utils.Logging;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    private final SpeedController gripMotor = RobotMap.intakeGripMotor;
    private final DigitalInput closeLimit = RobotMap.intakeCloseLimit;
    private final DigitalInput openLimit = RobotMap.intakeOpenLimit;
    
    private final double RAISE_SPEED =  1.0;
    private final double LOWER_SPEED = -0.8;
    
    private final double CLOSE_SPEED = -0.25;
    private final double OPEN_SPEED = 0.80;
    
    
	/**
	 * Approximately 8/12 volts.
	 */
//    private final double ROLLER_SPEED = 0.67;
    private final double ROLLER_SPEED = 1.0;
    
    /**
     * Approximately 4/12 volts.
     */
//    private final double VARIED_ROLLER_SPEED = 0.33;
    private final double VARIED_ROLLER_SPEED = 0.25;

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
	public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(Logging.Subsystems.INTAKE) {

		@Override
		protected void addAll() {
			add("Deploy Motor Speed", deployMotor.get());
			add("Left Wheel Speed", leftIntakeMotor.get());
			add("Right Wheel Speed", rightIntakeMotor.get());
			add("Grip Motor Speed", gripMotor.get());
			add("Raised?", isRaised());
			add("Lowered?", isLowered());
			add("Has Cube?", hasCube());
			add("Open?", openLimit.get());
			add("Close?", closeLimit.get());
		}
		

	};

    @Override
    public void periodic() {
    	loggingContext.writeData();
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
    
    public void intakeCubeSlower()
    {
    	leftIntakeMotor.set(VARIED_ROLLER_SPEED);
    	rightIntakeMotor.set(VARIED_ROLLER_SPEED);
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
     * Returns true when the intake is deployed to the lower position.
     * @return
     */
    public boolean isLowered()
    {
    	return !lowerLimit.get(); 
    }
    /**
     * Returns true when the intake is in the stowed or raised position.
     * @return
     */
    public boolean isRaised()
    {
    	return !upperLimit.get();
    }
    
//    public boolean isOpened()
//    {
//    	return !openLimit.get();
//    }
//    
//    public boolean isClosed()
//    {
//    	return !closeLimit.get();
//    }
    
    public void closeIntake()
    {
    	gripMotor.set(CLOSE_SPEED);
    }
    
    public void openIntake()
    {
    	gripMotor.set(OPEN_SPEED);
    }
    
    public void stopIntakeGrip()
    {
    	gripMotor.set(0.0);
    }
    
    public boolean intakeOpen()
    {
    	return openLimit.get();
    }
    
    public boolean intakeClose()
    {
    	return closeLimit.get();

    }
}

