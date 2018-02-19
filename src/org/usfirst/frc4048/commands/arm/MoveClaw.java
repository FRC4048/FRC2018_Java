package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.subsystems.Claw.WristPostion;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveClaw extends Command {

	MotorUtils util = new MotorUtils(RobotMap.PDP_WRIST_MOTOR, RobotMap.CURRENT_THRESHOLD_WRIST_MOTOR);
	
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
    		Robot.claw.moveClawToLevel();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return util.isStalled();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.claw.stopWrist();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.claw.stopWrist();
    }
}
