package org.usfirst.frc4048.commands.intake;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LowerIntake extends Command {
	private static final boolean DEBUG = false;
	private final GroupCommandCallback callback;
	private final MotorUtils util = new MotorUtils(RobotMap.PDP_INTAKE_DEPLOY_MOTOR, RobotMap.CURRENT_THRESHOLD_INTAKE_DEPLOY_MOTOR);
	
    public LowerIntake() {
    	this(GroupCommandCallback.NONE);
    }

    public LowerIntake(GroupCommandCallback callback) {
    	this.callback = callback;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(4.0); //TODO Implement LowerIntake timeouts and test them (uncomment them, and make sure they work)
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!isTimedOut() && !Robot.intake.hasCube() && !callback.hasGroupBeenCanceled())
    		Robot.intake.lowerIntake();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.intake.isLowered() || isTimedOut() || util.isStalled();
    }

    // Called once after isFinished returns true
    protected void end() {
    	callback.doCancel(isTimedOut());
    	if (DEBUG) {
    		DriverStation.reportError("ending LowerIntake!", true);
    	}
    	Robot.intake.stopLowerOrRaiseIntake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		callback.doCancel(true);
    	Robot.intake.stopLowerOrRaiseIntake();
    }
}
