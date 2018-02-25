package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.subsystems.Wrist.WristPostion;

public class MoveClawToStraight extends LoggedCommand {

	private final GroupCommandCallback callback;

	public MoveClawToStraight()
	{
		this(GroupCommandCallback.NONE);
	}
	
	public MoveClawToStraight(GroupCommandCallback callback) {
		super(String.format("Subcommand From: %s", callback.getName()));
		requires(Robot.wrist);
		this.callback = callback;
	}

	protected void loggedInitialize() {
		setTimeout(5.0);
		Robot.wrist.setPosition(WristPostion.Straight);
	}

	protected void loggedExecute() {
		if (!Robot.wrist.isStraight() && !callback.hasGroupBeenCanceled()) {
			Robot.wrist.moveClawToStraight();
		}
	}

	protected boolean loggedIsFinished() {
		return Robot.wrist.isStraight() || isTimedOut();
	}

	protected void loggedEnd() {
		Robot.wrist.stopWrist();
		callback.doCancel(isTimedOut());
	}

	protected void loggedInterrupted() {
		Robot.wrist.stopWrist();
		callback.doCancel(true);
	}

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
