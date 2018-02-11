package org.usfirst.frc4048.commands.getcube;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;

import edu.wpi.first.wpilibj.command.Command;

public class CancelIfCubeInClaw extends Command {
	private final GroupCommandCallback callback;

	public CancelIfCubeInClaw(GroupCommandCallback callback) {
		this.callback = callback;
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

}
