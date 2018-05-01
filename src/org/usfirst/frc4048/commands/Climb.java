package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.*;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Climb extends Command {

	public Climb() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.climber);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(Robot.arm.armAtPosition(ArmPositions.Climb)) {
			if(Robot.oi.getUpDPAD()) {
				Robot.climber.winchUp();
//			} else if(Robot.oi.getDownDPAD()) {
//				Robot.climber.winchDown();
			} else {
				Robot.climber.stopWinch();
			}
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
