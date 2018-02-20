package org.usfirst.frc4048.commands.intake;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

import edu.wpi.first.wpilibj.command.Command;

/**
 * <a href="https://docs.google.com/document/d/1OgAongLY_8LNqdwn1yhQ4sSoWiFGtY9wH-Y8xEroo0Y/edit">spec</a>
 * 
 * <pre>
 * Intake Flush:
 * If (Auto Function != idle)
 *   do nothing
 * If (Intake is not Down)
 *   do nothing // user is responsible for lowering Intake if not down
 * Set Intake motors to flush // don't care if there's a Cube or not.
 * </pre>
 */
public class FlushCube extends LoggedCommand {

    public FlushCube() {
    	super(String.format("Command is running"));
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void loggedInitialize() {
    	setTimeout(7.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void loggedExecute() {
    	if (Robot.intake.isLowered()) {
    		Robot.intake.flushCube();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean loggedIsFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void loggedEnd() {
    	Robot.intake.stopIntake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void loggedInterrupted() {
    	Robot.intake.stopIntake();
    }

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
