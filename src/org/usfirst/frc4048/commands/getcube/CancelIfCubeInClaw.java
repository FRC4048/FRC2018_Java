package org.usfirst.frc4048.commands.getcube;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.LoggedCommand;

public class CancelIfCubeInClaw extends LoggedCommand {
	private final GroupCommandCallback callback;

	public CancelIfCubeInClaw(GroupCommandCallback callback) {
		super(callback.getClass().getSimpleName());
		this.callback = callback;
		requires(Robot.claw);
	}

	public void loggedExecute() {
		if (Robot.claw.cubePresent()) {
			callback.doCancel(true);
		}
	}

	@Override
	protected boolean loggedIsFinished() {
		return true;
	}
	
	@Override
	protected void loggedEnd() {
	}
	
	@Override
	protected void loggedInterrupted() {
		callback.doCancel(true);
	}

	@Override
	protected void loggedInitialize() {
		
	}

	@Override
	protected void loggedCancel() {
	}



}
