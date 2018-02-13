package org.usfirst.frc4048.commands.getcube;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;

import edu.wpi.first.wpilibj.command.Command;

public class CancelIfCubeInClaw extends Command {
	private final GroupCommandCallback callback;

	public CancelIfCubeInClaw(GroupCommandCallback callback) {
		this.callback = callback;
		requires(Robot.claw);
	}

	public void execute() {
		if (Robot.claw.cubePresent()) {
			callback.doCancel(true);
		}
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
	
	@Override
	protected void end() {
	}
	
	@Override
	protected void interrupted() {
		callback.doCancel(true);
	}

}
