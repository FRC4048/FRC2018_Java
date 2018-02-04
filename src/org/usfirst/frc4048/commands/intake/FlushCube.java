package org.usfirst.frc4048.commands.intake;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlushCube extends Command {

    public FlushCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.flushCube();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.stopIntake();
    	Command lower = new LowerIntake();
    	lower.start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.stopIntake();
    	Command lower = new LowerIntake();
    	lower.start();
    }
}