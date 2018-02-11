package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.subsystems.Claw;
import org.usfirst.frc4048.subsystems.Claw.WristPostion;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveClaw extends Command {
	private final GroupCommandCallback callback;
	private final WristPostion position;

    public MoveClaw(Claw.WristPostion position) {
    	this(GroupCommandCallback.NONE, position);
    }

    public MoveClaw(final GroupCommandCallback callback, Claw.WristPostion position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.callback = callback;
    	
    	requires(Robot.claw);
    	
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch (position) {
		case Compact:
			
			break;
		case Level:
			
			break;
		default:
			break;
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	callback.doCancel(isTimedOut());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
