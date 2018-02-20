package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class GrabCube extends Command {
	private final GroupCommandCallback callback;
	private final MotorUtils pdpMotor = new MotorUtils(RobotMap.PDP_GRIP_MOTOR, RobotMap.CURRENT_THRESHOLD_GRIP_MOTOR);
	
//	MotorUtils currentCheck 
	
	public GrabCube() {
		this(GroupCommandCallback.NONE);
	}
	
    public GrabCube(final GroupCommandCallback callback) {
    	this.callback = callback;
    	requires(Robot.claw);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(5);    	
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.claw.gripClosed() && !isTimedOut() && !callback.hasGroupBeenCanceled())
    		Robot.claw.closeClaw();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.claw.gripClosed() || isTimedOut() || pdpMotor.isStalled();
    }

    // Called once after isFinished returns true
    protected void end() {
    	callback.doCancel(isTimedOut());
    	Robot.claw.stopGrip();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	callback.doCancel(true);
    	Robot.claw.stopGrip();	
    }
}
