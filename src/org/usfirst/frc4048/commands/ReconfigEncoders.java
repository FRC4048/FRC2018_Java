package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReconfigEncoders extends LoggedCommand {

    public ReconfigEncoders() {
    	super(String.format("command is running"));
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void loggedInitialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void loggedExecute() {
    	Robot.drivetrain.resetQuadEncoder();
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
