	package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OpenClaw extends Command {
	private final GroupCommandCallback callback;

	public OpenClaw() {
		this(GroupCommandCallback.NONE);
	}
	
    public OpenClaw(final GroupCommandCallback callback) {
    	this.callback = callback;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.claw);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(2.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//TODO Should it detect cube?
    	if(!Robot.claw.gripOpen() && !isTimedOut())
    		Robot.claw.openClaw();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.claw.gripOpen() || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	callback.doCancel(isTimedOut());
    	Robot.claw.stopGrip();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.claw.stopGrip();
    }
}
