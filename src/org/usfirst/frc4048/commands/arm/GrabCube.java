package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class GrabCube extends LoggedCommand {
	private final GroupCommandCallback callback;
	private final MotorUtils pdpMotor = new MotorUtils(RobotMap.PDP_GRIP_MOTOR, RobotMap.CURRENT_THRESHOLD_GRIP_MOTOR);
	
//	MotorUtils currentCheck 
	
	public GrabCube() {
		this(GroupCommandCallback.NONE);
	}
	
    public GrabCube(final GroupCommandCallback callback) {
    	super(String.format("Subcommand From: %s", callback.getName()));
    	this.callback = callback;
    	requires(Robot.pincher);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void loggedInitialize() {
    	setTimeout(3.0);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void loggedExecute() {
    	if(!Robot.pincher.pincherIsClosed() && !isTimedOut() && !callback.hasGroupBeenCanceled())
    		Robot.pincher.closePincher();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean loggedIsFinished() {
    	return Robot.pincher.pincherIsClosed() || isTimedOut() || pdpMotor.isStalled();
    }

    // Called once after isFinished returns true
    protected void loggedEnd() {
    	callback.doCancel(isTimedOut());
    	Robot.pincher.stopPincher();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void loggedInterrupted() {
    	callback.doCancel(true);
    	Robot.pincher.stopPincher();
    }

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
