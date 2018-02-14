package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.subsystems.Arm;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;

/**
 *
 */
public class MoveArm extends Command {
	private final GroupCommandCallback callback;
	ArmPositions position; 
		
    public MoveArm(final ArmPositions position) {
    	this(GroupCommandCallback.NONE, position);
    }
	
    public MoveArm(final GroupCommandCallback callback, ArmPositions position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		requires(Robot.arm);
    	this.callback = callback;
    	this.position = position;
    }

    
	// Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(3.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		if(position == ArmPositions.Home)
		{
			Robot.arm.setAutoExtension(false);
			Robot.arm.retractExtension();
			if(Robot.arm.getExtPos() <= Arm.HOME_SETPOINT + Arm.MARGIN_VALUE)
			{
				Robot.arm.moveToPos(position);
			}
		}
		else
		{
			Robot.arm.moveToPos(position);
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() || Robot.arm.armAtPosition(position);
    }

    // Called once after isFinished returns true
    protected void end() {
    	callback.doCancel(isTimedOut());
    	Robot.arm.stopArm();
    	Robot.arm.setAutoExtension(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.arm.stopArm();
    	Robot.arm.setAutoExtension(true);
    }
}
