package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RetractExtension extends Command {

	GroupCommandCallback callback;
	
    public RetractExtension() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);
    	callback = GroupCommandCallback.NONE;
    }
    
    public RetractExtension(GroupCommandCallback callback) {
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
    	if(Robot.arm.getExtPos() > Arm.HOME_SETPOINT + Arm.MARGIN_VALUE)
    	{
    		Robot.arm.setAutoExtension(false);
    		Robot.arm.retractExtension();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.arm.getExtPos() <= Arm.HOME_SETPOINT + Arm.MARGIN_VALUE || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.arm.setAutoExtension(true);
    }
}
