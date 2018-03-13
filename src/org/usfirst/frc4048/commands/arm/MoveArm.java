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
	private boolean retractElbow = false;
	private boolean elbowWasRetracted = false;

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
//		setTimeout(6.0);
		retractElbow = Robot.arm.elbowShouldCompact();
		elbowWasRetracted = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void loggedExecute() {
		SmartDashboard.putBoolean("Running Move arm", true);
		SmartDashboard.putBoolean("Retract Elbow", retractElbow);
		SmartDashboard.putBoolean("Elbow Was Retracted", elbowWasRetracted);
		Robot.arm.setDisabled(false);
		
		if(!callback.hasGroupBeenCanceled()) {
			
			if(retractElbow) {
				
				if(!elbowWasRetracted && Robot.arm.elbowAtPosition(ArmPositions.Home)) {
					elbowWasRetracted = true;
				} else if(!elbowWasRetracted) {
					Robot.arm.elbowToPosition(ArmPositions.Home);
				}
				
				if(elbowWasRetracted) {
					Robot.arm.armToPosition(position);

					if(Robot.arm.armAtPosition(position)) {
						Robot.arm.elbowToPosition(position);
					}
				}	
			} else {
				Robot.arm.armToPosition(position);
				Robot.arm.elbowToPosition(position);
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean loggedIsFinished() {
		return isTimedOut() || (Robot.arm.armAtPosition(position) && Robot.arm.elbowAtPosition(position));
	}

	// Called once after isFinished returns true
	protected void loggedEnd() {
		callback.doCancel(isTimedOut());
		SmartDashboard.putBoolean("Running Move arm", false);
		Robot.arm.stopArm();
		Robot.arm.stopElbow();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void loggedInterrupted() {
		SmartDashboard.putBoolean("Running Move arm", false);
		Robot.arm.stopArm();
		Robot.arm.stopElbow();

	}

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
