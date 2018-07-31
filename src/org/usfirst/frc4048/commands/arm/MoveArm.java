package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.subsystems.Arm;
import org.usfirst.frc4048.subsystems.Arm.Position;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveArm extends Command {
	Position position;

	private final MotorUtils elbowStall = new MotorUtils(RobotMap.PDP_ELBOW, 
			RobotMap.CURRENT_THRESHOLD_ELBOW_MOTOR, 
			RobotMap.TIMEOUT_ELBOW_MOTOR);
	private final MotorUtils armStall = new MotorUtils(RobotMap.PDP_ARM_MOTOR, 
			RobotMap.CURRENT_THRESHOLD_ARM_MOTOR_PROBLEM,
			RobotMap.TIMEOUT_ARM_MOTOR_PROBLEM);

	private boolean retractElbow = false;
	public MoveArm(final Position position) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.arm);
		this.position = position;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		setTimeout(6.0);
		retractElbow = Robot.arm.retractElbow(position);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(position == Position.CLIMB && DriverStation.getInstance().getMatchTime() > 30){
			return;
		}
		if(retractElbow) {
			if(!Robot.arm.elbwAtPos(Position.HOME) && !Robot.arm.armAtPos(position)) {
				Robot.arm.elbwToPos(Position.HOME);
			}

			if(Robot.arm.elbwAtPos(Position.HOME)) {
				System.out.println("RUNNING ARM MOVEMENT");
				Robot.arm.armToPos(position);
			}

			if(Robot.arm.armAtPos(position)) {
				Robot.arm.elbwToPos(position);
			}
		} else{
			Robot.arm.armToPos(position);
			Robot.arm.elbwToPos(position);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (Robot.arm.armAtPos(position) && Robot.arm.elbwAtPos(position)) || armStall.isStalled() || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
