package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElbowFinetune extends Command {

    public ElbowFinetune() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.oi.getLeftstickUp()) {
    		Robot.arm.fineTuneUp();
    	}
    	if(Robot.oi.getLeftstickDown()) {
    		Robot.arm.fineTuneDown();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
