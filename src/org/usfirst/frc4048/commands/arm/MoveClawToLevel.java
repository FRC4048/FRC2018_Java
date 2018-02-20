package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc4048.commands.GroupCommandCallback;

public class MoveClawToLevel extends Command {

	private final GroupCommandCallback callback;

	public MoveClawToLevel()
	{
		this(GroupCommandCallback.NONE);
	}
	
	public MoveClawToLevel(GroupCommandCallback callback) {
		requires(Robot.claw);
		this.callback = callback;
	}

	protected void initialize() {
		setTimeout(5.0);
	}

	protected void execute() {
		if (!Robot.claw.isLevel() && !callback.hasGroupBeenCanceled()) {
			Robot.claw.moveClawToLevel();
		}
	}

	protected boolean isFinished() {
		return Robot.claw.isLevel() || isTimedOut();
	}

	protected void end() {
		Robot.claw.stopWrist();
		callback.doCancel(isTimedOut());
	}

	protected void interrupted() {
		Robot.claw.stopWrist();
		callback.doCancel(true);
	}
}
