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
public class ExtendArmToCube extends LoggedCommand {

	GroupCommandCallback callback;
	MotorUtils util = new MotorUtils(RobotMap.PDP_ARM_MOTOR, RobotMap.CURRENT_THRESHOLD_ARM_CUBE_PICKUP);
	
    public ExtendArmToCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this(GroupCommandCallback.NONE);
    }
    
    public ExtendArmToCube(GroupCommandCallback callback) {
    	super(String.format("Subcommand From: %s", callback.getName()));
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);
    	this.callback = callback;
    }

    // Called just before this Command runs the first time
    protected void loggedInitialize() {
    	setTimeout(7.5);
    	Robot.arm.setExtIntakePID();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void loggedExecute() {
    	if(!Robot.claw.cubePresent() && !isTimedOut() && !util.isStalled() && !callback.hasGroupBeenCanceled())
    	{
    		Robot.arm.extensionToIntakeEnd();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean loggedIsFinished() {
        return isTimedOut() || Robot.claw.cubePresent() || util.isStalled();
    }

    // Called once after isFinished returns true
    protected void loggedEnd() {
    	//TODO Is stalling needed for canceling?
    	callback.doCancel(isTimedOut());// || util.isStalled());
    	Robot.arm.setExtNormalPID();
    	Robot.arm.setExtToCurrentPos();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void loggedInterrupted() {
    	callback.doCancel(true);
    	Robot.arm.setExtNormalPID();
    	Robot.arm.setExtToCurrentPos();
    }

	@Override
	protected void loggedCancel() {
	}
}
