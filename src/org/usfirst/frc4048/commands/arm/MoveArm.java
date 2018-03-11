package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.subsystems.Arm;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;
import org.usfirst.frc4048.subsystems.Wrist.WristPostion;

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
		super(String.format("Now running MoveArm, position: %s Subcommand from: %s", position.toString(), callback.getName()));
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
//		Robot.wrist.setPosition(WristPostion.Compact);
		if(Robot.arm.elbowShouldCompact()) {
			Robot.arm.eblowToHome();
		}
		
		Robot.arm.setDisabled(false);
		if(!callback.hasGroupBeenCanceled())
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
		Robot.arm.elbowToPosition(position);
		
//		Robot.wrist.setPosition(WristPostion.Straight);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void loggedInterrupted() {
		Robot.arm.stopArm();
		Robot.arm.stopElbow();
//		Robot.wrist.setPosition(WristPostion.Straight);
//		callback.doCancel(true);
	}

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
