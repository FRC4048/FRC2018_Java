package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.LoggedCommand;

public class MoveClawToLevel extends LoggedCommand {

	private final GroupCommandCallback callback;

	public MoveClawToLevel(GroupCommandCallback callback) {
		super(String.format("Subcommand From: %s", callback.getName()));
		requires(Robot.claw);
		this.callback = callback;
	}

	protected void loggedInitialize() {
		setTimeout(5.0);
	}

	protected void loggedExecute() {
		if (!Robot.claw.isLevel()) {
			Robot.claw.moveClawToLevel();
		}
	}

	protected boolean loggedIsFinished() {
		return Robot.claw.isLevel() || isTimedOut();
	}

	protected void loggedEnd() {
		Robot.claw.stopWrist();
		callback.doCancel(isTimedOut());
	}

	protected void loggedInterrupted() {
		Robot.claw.stopWrist();
		callback.doCancel(true);
	}

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
