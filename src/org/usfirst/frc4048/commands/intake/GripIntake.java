package org.usfirst.frc4048.commands.intake;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.LoggedCommand;

import com.sun.javafx.binding.StringFormatter;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GripIntake extends LoggedCommand {

	private GroupCommandCallback callback;
	
	private GripPosition state;
	
	public enum GripPosition {
		Open,
		Close
	}
	
    public GripIntake(GripPosition state) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this(GroupCommandCallback.NONE, state);
    }
    
    public GripIntake(GroupCommandCallback callback, GripPosition state) {
    	super(String.format("Now running Grip intake, state: %s", state.toString()));
    	requires(Robot.intake);
		this.callback = callback;
		this.state = state;
    }

    // Called just before this Command runs the first time
    protected void loggedInitialize() {
    	setTimeout(3.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void loggedExecute() {
    	switch (this.state) {
		case Open:
			Robot.intake.openIntake();
			break;
			
		case Close:
			Robot.intake.closeIntake();
			break;
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean loggedIsFinished() {
        return isTimedOut() || (Robot.intake.intakeOpen() && state == GripPosition.Open)
        					|| (Robot.intake.intakeClose() && state == GripPosition.Close);
    }

    // Called once after isFinished returns true
    protected void loggedEnd() {
    	callback.doCancel(isTimedOut());
    	Robot.intake.stopIntake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void loggedInterrupted() {
    	Robot.intake.stopIntake();
    	callback.doCancel(true);
    }

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
