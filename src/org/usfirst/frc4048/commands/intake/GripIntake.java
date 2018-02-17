package org.usfirst.frc4048.commands.intake;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GripIntake extends Command {

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
    	requires(Robot.intake);
		this.callback = callback;
		this.state = state;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(3.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
    protected boolean isFinished() {
        return isTimedOut() || (Robot.intake.intakeOpen() && state == GripPosition.Open)
        					|| (Robot.intake.intakeClose() && state == GripPosition.Close);
    }

    // Called once after isFinished returns true
    protected void end() {
    	callback.doCancel(isTimedOut());
    	Robot.intake.stopIntake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.stopIntake();
    	callback.doCancel(true);
    }
}
