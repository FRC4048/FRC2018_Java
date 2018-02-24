package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetClawGyro extends LoggedCommand {

    public ResetClawGyro() {
    	super(String.format("command is running"));
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.wrist);
    }

    // Called just before this Command runs the first time
    protected void loggedInitialize() {
    	Robot.wrist.recalibrateClawGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void loggedExecute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean loggedIsFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void loggedEnd() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void loggedInterrupted() {
    }

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
