package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.subsystems.Arm;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.LoggedCommand;

/**
 *
 */
public class MoveArm extends LoggedCommand {

	private final GroupCommandCallback callback;
	private final ArmPositions position;

	public MoveArm(final ArmPositions position) {
		this(GroupCommandCallback.NONE, position);
	}

	public MoveArm(final GroupCommandCallback callback, ArmPositions position) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super(String.format("Now running MoveArm, position: %s", position.toString()));
		requires(Robot.arm);
		this.callback = callback;
		this.position = position;
	}

	// Called just before this Command runs the first time
	protected void loggedInitialize() {
		setTimeout(6.0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void loggedExecute() {
		Robot.arm.moveToPos(position);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean loggedIsFinished() {
		return isTimedOut() || Robot.arm.armAtPosition(position);
	}

	// Called once after isFinished returns true
	protected void loggedEnd() {
		callback.doCancel(isTimedOut());
		Robot.arm.stopArm();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void loggedInterrupted() {
		Robot.arm.stopArm();
//		callback.doCancel(true);
	}

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
