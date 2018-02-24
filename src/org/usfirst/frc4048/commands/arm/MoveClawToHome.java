package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveClawToHome extends Command {

	GroupCommandCallback callback;
	
    public MoveClawToHome(GroupCommandCallback callback) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.callback = callback;
    	requires(Robot.wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(5.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.wrist.clawUp() && !callback.hasGroupBeenCanceled())
    	{
    		Robot.wrist.angleUp();
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.wrist.clawUp() || isTimedOut() || callback.hasGroupBeenCanceled();
    }

    // Called once after isFinished returns true
    protected void end() {
    	callback.doCancel(isTimedOut());
    	Robot.wrist.stopWrist();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	callback.doCancel(true);
    	Robot.wrist.stopWrist();
    }
}
