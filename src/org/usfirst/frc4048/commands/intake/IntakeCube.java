package org.usfirst.frc4048.commands.intake;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.wpilibj.command.Command;

/**
 * <a href="https://docs.google.com/document/d/1OgAongLY_8LNqdwn1yhQ4sSoWiFGtY9wH-Y8xEroo0Y/edit">spec</a>
 *
 */
public class IntakeCube extends Command {
	private MotorUtils leftMotor;
	private MotorUtils rightMotor;
	private final IntakeMode mode;
	private PullSide pullSide = PullSide.LEFT;
	private final GroupCommandCallback callback;
	
	private enum PullSide {
		LEFT, RIGHT,
	};

	public enum IntakeMode {

		/**
		 * Alternate pulling from one side or the other.
		 */
		TOGGLE_PULL_LEFT_OR_RIGHT,

		/**
		 * Both intake motors run at the same speed.
		 */
		STRAIGHT_PULL
	}

	public IntakeCube(final GroupCommandCallback callback, final IntakeMode mode) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.callback = callback;
		requires(Robot.intake);
		this.mode = mode; // IntakeMode.STRAIGHT_PULL;
	}

	public IntakeCube(IntakeMode event) {
		this(GroupCommandCallback.NONE, event);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		setTimeout(6.0);
		leftMotor = new MotorUtils(RobotMap.PDP_LEFT_INTAKE_MOTOR, RobotMap.CURRENT_THRESHOLD_INTAKE_MOTOR,
				RobotMap.TIMEOUT_INTAKE_MOTOR);
		rightMotor = new MotorUtils(RobotMap.PDP_RIGHT_INTAKE_MOTOR, RobotMap.CURRENT_THRESHOLD_INTAKE_MOTOR,
				RobotMap.TIMEOUT_INTAKE_MOTOR);
		
		switch (pullSide) {
		case LEFT:
			pullSide = PullSide.RIGHT;
			break;
		case RIGHT:
			pullSide = PullSide.LEFT;
			break;
		}

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (!Robot.intake.hasCube() && Robot.intake.isLowered() && !isTimedOut() && !callback.hasGroupBeenCanceled()) {
			switch (mode) {
			case STRAIGHT_PULL:
				Robot.intake.intakeCube();
				break;

			case TOGGLE_PULL_LEFT_OR_RIGHT:
				switch (pullSide) {
				case LEFT:
					Robot.intake.adjustCubeLeftSide();
					break;
				case RIGHT:
					Robot.intake.adjustCubeRightSide();
					break;
				}
				break;
			}

		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.intake.hasCube() || !Robot.intake.isLowered() || isTimedOut() || leftMotor.isStalled()
				|| rightMotor.isStalled();
	}

	// Called once after isFinished returns true
	protected void end() {
		callback.doCancel(isTimedOut());
		Robot.intake.stopIntake();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		callback.doCancel(true);
		Robot.intake.stopIntake();
	}
}
