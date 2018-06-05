package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmFinetune extends Command {

	public ArmFinetune() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.arm);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(Robot.arm.armAtPosition(ArmPositions.Climb)) {
			return;
		} else {
			if(Robot.oi.getLeftstickDown())
				Robot.arm.finetuneDown();
			if(Robot.oi.getLeftstickUp())
				Robot.arm.finetuneUp();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.arm.stopArm();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.arm.stopArm();
	}
}
