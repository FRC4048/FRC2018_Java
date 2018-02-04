package org.usfirst.frc4048.commands.intake;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RaiseIntake extends Command {

    public RaiseIntake() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(2.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//if(!isTimedOut())
    		Robot.intake.raiseIntake();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.intake.isRaised();// || !isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.stopLowerOrRaiseIntake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.stopLowerOrRaiseIntake();
    }
}
