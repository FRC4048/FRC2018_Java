package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.*;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Climb extends LoggedCommand {

	public Climb() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super("Climb Command");
		requires(Robot.climber);
	}

	// Called just before this Command runs the first time
	protected void loggedInitialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void loggedExecute() {
		if(Robot.arm.armAtPosition(ArmPositions.Climb)) {
			if(Robot.oi.getRightstickUp()) {
				Robot.climber.winchUp();
			} else if(Robot.oi.getRightstickDown() && Robot.enableDiagnostic) {
				Robot.climber.winchDown();
			} else {
				Robot.climber.stopWinch();
			}
		}
	}	

	// Make this return true when this Command no longer needs to run execute()
	protected boolean loggedIsFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void loggedEnd() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void loggedInterrupted() {
	}

	protected void loggedCancel() {
		// TODO Auto-generated method stub

	}
}
