package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.subsystems.Arm;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;
import org.usfirst.frc4048.subsystems.Claw.WristPostion;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveClaw extends LoggedCommand {

	MotorUtils util = new MotorUtils(RobotMap.PDP_WRIST_MOTOR, RobotMap.CURRENT_THRESHOLD_WRIST_MOTOR);
	
    public MoveClaw() {
    	super(String.format("command is running"));
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.claw);
    }

    // Called just before this Command runs the first time
    protected void loggedInitialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void loggedExecute() {
    	if(Robot.arm.isGoingHome() || Robot.arm.getArmAngle() <= (Arm.HOME_MAX_ANGLE))
    	{
    		Robot.claw.setPosition(WristPostion.Compact);
    	}
    	else
    	{
    		Robot.claw.setPosition(WristPostion.Level);
    	}
    	
    	if(Robot.claw.getPosition() == WristPostion.Compact)
    	{
    		if(!Robot.claw.clawUp())
    		{
    			Robot.claw.angleUp();
    		}
    		else
    		{
//    			if(Robot.arm.armAtPosition(ArmPositions.Home)) {
//    				Robot.claw.recalibrateClawGyro();
//    			}
    			Robot.claw.stopWrist();
    		}
    	}
    	else if(Robot.claw.getPosition() == WristPostion.Level)
    	{
    		Robot.claw.moveClawToLevel();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean loggedIsFinished() {
    	return false;
//        return util.isStalled();
    }

    // Called once after isFinished returns true
    protected void loggedEnd() {
    	Robot.claw.stopWrist();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void loggedInterrupted() {
    	Robot.claw.stopWrist();
    }

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
