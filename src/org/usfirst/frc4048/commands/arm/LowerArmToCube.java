package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LowerArmToCube extends Command {

	GroupCommandCallback callback;
	MotorUtils util = new MotorUtils(RobotMap.PDP_ARM_MOTOR, RobotMap.CURRENT_THRESHOLD_ARM_CUBE_PICKUP);
	
    public LowerArmToCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this(GroupCommandCallback.NONE);
    }
    
    public LowerArmToCube(GroupCommandCallback callback) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);
    	this.callback = callback;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(3.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.claw.cubePresent() && !isTimedOut() && !util.isStalled())
    		Robot.arm.finetuneDown();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() || Robot.claw.cubePresent() || util.isStalled();
    }

    // Called once after isFinished returns true
    protected void end() {
    	//TODO Is stalling needed for canceling?
    	callback.doCancel(isTimedOut());// || util.isStalled());
    	Robot.arm.stopArm();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.arm.stopArm();
    	callback.doCancel(true);
    }
}
