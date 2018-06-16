package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClosePincher extends Command {
	private final MotorUtils pdpMotor = new MotorUtils(RobotMap.PDP_GRIP_MOTOR, RobotMap.CURRENT_THRESHOLD_GRIP_MOTOR, RobotMap.TIMEOUT_GRIP_MOTOR);
	
    public ClosePincher() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pincher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(3.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (!Robot.pincher.isClose() && !isTimedOut() && !pdpMotor.isStalled()) {
    		System.out.println("CLOSE IS RUNNING");
    		Robot.pincher.closePincher();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() || pdpMotor.isStalled() || Robot.pincher.isClose();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
