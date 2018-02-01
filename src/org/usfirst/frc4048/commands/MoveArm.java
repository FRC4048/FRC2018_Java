package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import com.sun.glass.ui.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveArm extends Command {

	ArmPositions postion; 
	
    public MoveArm(ArmPositions position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	this.postion = postion;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
