package org.usfirst.frc4048.commands.intake;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleIntake extends Command {

	Command lowerIntake;
	Command raiseIntake;
	
	private static final boolean DEBUG = false;
	
    public ToggleIntake() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lowerIntake = new LowerIntake();
    	raiseIntake = new RaiseIntake();
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
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
