package org.usfirst.frc4048.commands.intake;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleIntake extends LoggedCommand {

	Command lowerIntake;
	Command raiseIntake;
	
	private static final boolean DEBUG = false;
	
    public ToggleIntake() {
    	super(String.format("command is running"));
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void loggedInitialize() {
    	lowerIntake = new LowerAndCloseIntake();
    	raiseIntake = new RaiseAndOpenIntake();
		if (DEBUG) {
			// DriverStation.reportError("A Thing: " + Robot.intake.isLowered() + ", " +
			// !Robot.intake.isRaised() + ", " + !Robot.intake.hasCube(), true);
			DriverStation.reportError("isRaised: " + Robot.intake.isRaised(), true);
			DriverStation.reportError("isLowered: " + Robot.intake.isLowered(), true);
		}
		if (Robot.intake.hasCube()) {
			// Do nothing, since it has a cube
			if (DEBUG) {
				DriverStation.reportError("There's a cube.", true);
			}
		} else if (!Robot.intake.isLowered() && Robot.intake.isRaised()) {
			lowerIntake.start();
			if (DEBUG) {
				DriverStation.reportError("lowerIntake running", true);
			}
		} else if (!Robot.intake.isRaised() && Robot.intake.isLowered()) {
			raiseIntake.start();
			if (DEBUG) {
				DriverStation.reportError("raiseIntake running", true);
			}
		} else {
			// When we can't tell if the intake is up or down, favor up because it's the
			// safer choice.
			raiseIntake.start();
			if (DEBUG) {
				DriverStation.reportError("raiseIntake running", true);
			}
		}
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
