package org.usfirst.frc4048.commands.intake;

import org.usfirst.frc4048.IntakeCubeTrigger;
import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.utils.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeCube extends Command {
	private MotorUtils leftMotor;
	private MotorUtils rightMotor;
	private final IntakeCubeTrigger intakeCubeTrigger;

    public IntakeCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    	this.intakeCubeTrigger = null;
    }

    public IntakeCube(IntakeCubeTrigger intakeCubeTrigger) {
    	requires(Robot.intake);
		this.intakeCubeTrigger = intakeCubeTrigger;
	}

	// Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(2.0);
    	leftMotor = new MotorUtils(RobotMap.PDP_LEFT_INTAKE_MOTOR, RobotMap.CURRENT_THRESHOLD_INTAKE_MOTOR, RobotMap.TIMEOUT_INTAKE_MOTOR);
    	rightMotor = new MotorUtils(RobotMap.PDP_RIGHT_INTAKE_MOTOR, RobotMap.CURRENT_THRESHOLD_INTAKE_MOTOR, RobotMap.TIMEOUT_INTAKE_MOTOR);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.intake.hasCube() && Robot.intake.isLowered() && !isTimedOut())
    	{
			// There is a case where this command can be run without an operator trigger and
			// there is no intakeCubeTrigger object.
    		
    		if (intakeCubeTrigger == null) {
    			Robot.intake.intakeCube();
    		}
    		else if (intakeCubeTrigger.isAdjustEnabled()) 
	    	{
	    		Robot.intake.adjustCube();
	    	}
	    	else
	    	{
	    		Robot.intake.intakeCube();
	    	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.intake.hasCube() || !Robot.intake.isLowered() || isTimedOut() || leftMotor.isStalled() || rightMotor.isStalled();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.stopIntake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.stopIntake();
    }
}
