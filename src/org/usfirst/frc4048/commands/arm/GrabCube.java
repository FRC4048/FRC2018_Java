package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.wpilibj.command.Command;

/**
 * TODO - if this command has a timeout then we need to add the callback for the group command cancel.
 */
public class GrabCube extends Command {
	
//	MotorUtils currentCheck 
	
	//TODO Add motorutils into this command
	
    public GrabCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.claw.gripClosed() && !isTimedOut())
    		Robot.claw.closeClaw();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.claw.gripClosed() || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.claw.stopGrip();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.claw.stopGrip();
    }
}
