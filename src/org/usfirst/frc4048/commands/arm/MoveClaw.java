package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.subsystems.Claw;
import org.usfirst.frc4048.subsystems.Claw.WristPostion;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveClaw extends Command {

    public MoveClaw() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.claw);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.claw.getPosition() == WristPostion.Compact)
    	{
    		if(!Robot.claw.clawUp())
    		{
    			Robot.claw.angleUp();
    		}
    		else
    		{
    			Robot.claw.stopWrist();
    		}
    	}
    	if(Robot.claw.getPosition() == WristPostion.Level)
    	{
    		Robot.claw.moveClawToLevelWithPID();
    		// PID: Robot.claw.moveClawToLevelWithPID();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
