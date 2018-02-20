package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExtensionIntake extends LoggedCommand {

	GroupCommandCallback callback;
	
    public ExtensionIntake() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this(GroupCommandCallback.NONE);
    }
    
    public ExtensionIntake(GroupCommandCallback callback) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(String.format("Subcommand From: %s", callback.getName()));
    	requires(Robot.arm);
    	this.callback = callback;
    }

    // Called just before this Command runs the first time
    protected void loggedInitialize() {
    	setTimeout(3.0);
    }

    // Called repeatedly when this Command is scheduled to run

    protected void loggedExecute() {
    	if(!Robot.arm.extensionAtIntake() && !callback.hasGroupBeenCanceled())
    	{
    		Robot.arm.extensionToIntakeBegin();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean loggedIsFinished() {
        return Robot.arm.extensionAtHome() || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void loggedEnd() {
    	callback.doCancel(isTimedOut());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void loggedInterrupted() {
    	callback.doCancel(true);
    }

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
