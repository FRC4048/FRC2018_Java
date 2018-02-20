package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.wpilibj.command.Command;

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
//    	this.setInterruptible(false);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	try {
    	if(!Robot.claw.gripClosed() && !isTimedOut())
    		Robot.claw.closeClaw();
    	} catch (Throwable t) {
    		t.printStackTrace(System.out);
    		throw t;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	try {
    	return Robot.claw.gripClosed() || isTimedOut() || pdpMotor.isStalled();
    	} catch (Throwable t) {
    		t.printStackTrace(System.out);
    		throw t;
    	}

    }

    // Called once after isFinished returns true
    protected void end() {
    	try {
    	callback.doCancel(isTimedOut());
    	Robot.claw.stopGrip();
    	} catch (Throwable t) {
    		t.printStackTrace(System.out);
    		throw t;
    	}

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	try {
    	callback.doCancel(true);
    	Robot.claw.stopGrip();
    	} catch (Throwable t) {
    		t.printStackTrace(System.out);
    		throw t;
    	}

    }
}
