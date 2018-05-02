package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.subsystems.Arm;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;
import org.usfirst.frc4048.subsystems.Wrist.WristPostion;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.LoggedCommand;

/**
 *
 */
public class MoveArm extends LoggedCommand {
	private final double CLIMB_FOLD_ANGLE= 120;
	private final GroupCommandCallback callback;
	private final ArmPositions position;
	private boolean retractElbow = false;
	private boolean elbowWasRetracted = false;
	private final MotorUtils armStall = new MotorUtils(	RobotMap.PDP_ARM_MOTOR, 
														RobotMap.CURRENT_THRESHOLD_ARM_MOTOR,
														RobotMap.TIMEOUT_ARM_MOTOR);
	
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
		retractElbow = Robot.arm.elbowShouldCompact(position);
		elbowWasRetracted = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void loggedExecute() {
//		SmartDashboard.putBoolean("Running Move arm", true);
//		SmartDashboard.putBoolean("Retract Elbow", retractElbow);
//		SmartDashboard.putBoolean("Elbow Was Retracted", elbowWasRetracted);
		Robot.arm.setDisabled(false);
		if(position == ArmPositions.Climb && DriverStation.getInstance().getMatchTime() > 30 && Robot.arm.getArmAngle() < (Robot.arm.GET_HIGHSCALE_SETPOINT() - Robot.arm.GET_ANGLE_MARGIN_VALUE())) {
			return;
		}
		if(!callback.hasGroupBeenCanceled() && !armStall.isStalled() && !(Robot.arm.armAtPosition(position) && Robot.arm.elbowAtPosition(position))) {
			
			if (position == ArmPositions.Climb && Robot.arm.getArmAngle() >= CLIMB_FOLD_ANGLE) {
				retractElbow = false;
			}
			
			if(retractElbow) { 

				if(!Robot.arm.elbowAtPosition(ArmPositions.Home) && !Robot.arm.armAtPosition(position)) {
					Robot.arm.elbowToPosition(ArmPositions.Home);
				}
				
				if(Robot.arm.elbowAtPosition(ArmPositions.Home)) {
					Robot.arm.armToPosition(position);
				}
							
				if(Robot.arm.armAtPosition(position)) {
					Robot.arm.elbowToPosition(position);
				}	
			} else {
				Robot.arm.armToPosition(position);
				Robot.arm.elbowToPosition(position);
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean loggedIsFinished() {
		boolean output = isTimedOut() || armStall.isStalled() || (Robot.arm.armAtPosition(position) && Robot.arm.elbowAtPosition(position)) 
				|| (position == ArmPositions.Climb && DriverStation.getInstance().getMatchTime() > 30); 
		
//		SmartDashboard.putBoolean("Running Move arm", !output);
		return output;
	}

	// Called once after isFinished returns true
	protected void loggedEnd() {
		callback.doCancel(isTimedOut());
		Robot.arm.stopArm();
		Robot.arm.stopElbow();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void loggedInterrupted() {
//		Robot.arm.stopArm();
//		Robot.arm.stopElbow();
	}

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
