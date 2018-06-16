package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive extends Command {
	
	double fwd, str, rcw;
	
    public Drive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	fwd = Robot.oi.getLeftJoystick().getY();
    	str = Robot.oi.getLeftJoystick().getX();
    	rcw = Robot.oi.getRightJoystick().getX();
    	
    	if (fwd < 0) {
    		fwd *= fwd * -1;
    	} else {
    		fwd *= fwd;
    	}
    	
    	if (str < 0) {
    		str *= str * -1;
    	} else {
    		str *= str;
    	}
    	
    	if (rcw < 0) {
    		rcw *= rcw * 1;
    	} else {
    		rcw *= rcw;
    	}
    	
    	Robot.drivetrain.move(fwd, str, rcw);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	Robot.drivetrain.move(0, 0, 0);
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.move(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.move(0, 0, 0);
    }
}
