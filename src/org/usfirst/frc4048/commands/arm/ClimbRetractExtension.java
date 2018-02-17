package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimbRetractExtension extends Command {

	GroupCommandCallback callback;
	
    public ClimbRetractExtension() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this(GroupCommandCallback.NONE);
    }
    
    public ClimbRetractExtension(GroupCommandCallback callback) {
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
    	if(Robot.arm.getExtPos() > Arm.EXT_HOME_SETPOINT + Arm.POS_MARGIN_VALUE)
    	{
    		Robot.arm.extensionToHome();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.arm.getExtPos() <= Arm.HOME_SETPOINT + Arm.POS_MARGIN_VALUE || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	callback.doCancel(isTimedOut());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	callback.doCancel(true);
    }
}
