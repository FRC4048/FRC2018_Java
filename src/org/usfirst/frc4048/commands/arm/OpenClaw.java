package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OpenClaw extends LoggedCommand {
	
	private final GroupCommandCallback callback;
	private final MotorUtils util = new MotorUtils(RobotMap.PDP_GRIP_MOTOR, RobotMap.CURRENT_THRESHOLD_GRIP_MOTOR);
	
	public OpenClaw() {
		this(GroupCommandCallback.NONE);
	}
	
    public OpenClaw(final GroupCommandCallback callback) {
    	super(String.format("Subcommand From: %s", callback.getName()));
    	this.callback = callback;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.claw);
    }

    // Called just before this Command runs the first time
    protected void loggedInitialize() {
    	setTimeout(2.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void loggedExecute() {
    	//TODO Should it detect cube?
    	if(!Robot.claw.gripOpen() && !isTimedOut() && !util.isStalled())
    		Robot.claw.openClaw();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean loggedIsFinished() {
        return Robot.claw.gripOpen() || isTimedOut() || util.isStalled();
    }

    // Called once after isFinished returns true
    protected void loggedEnd() {
    	callback.doCancel(isTimedOut());
    	Robot.claw.stopGrip();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void loggedInterrupted() {
    	callback.doCancel(true);
    	Robot.claw.stopGrip();
    }

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
