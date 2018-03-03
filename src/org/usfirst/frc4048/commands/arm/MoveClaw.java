package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.subsystems.Arm;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;
import org.usfirst.frc4048.subsystems.Wrist.WristPostion;
import org.usfirst.frc4048.utils.MotorUtils;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveClaw extends LoggedCommand {

	MotorUtils util = new MotorUtils(RobotMap.PDP_WRIST_MOTOR, RobotMap.CURRENT_THRESHOLD_WRIST_MOTOR, RobotMap.TIMEOUT_WRIST_MOTOR);
	
    public MoveClaw() {
    	super(String.format("command is running"));
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.wrist);
    }

    // Called just before this Command runs the first time
    protected void loggedInitialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void loggedExecute() {
    	if(Robot.arm.isGoingHome() || Robot.arm.getArmAngle() <= (Arm.HOME_MAX_ANGLE))
    	{
    		Robot.wrist.setPosition(WristPostion.Compact);
    	}
    	
    	
    	
    	
//    	else
//    	{
//    		Robot.wrist.setPosition(WristPostion.Level);
//    	}
    	
    	if(Robot.wrist.getPosition() == WristPostion.Compact)
    	{
    		if(!Robot.wrist.clawUp())
    		{
    			Robot.wrist.angleUp();
    		}
    		else
    		{
//    			if(Robot.arm.armAtPosition(ArmPositions.Home)) {
//    				Robot.claw.recalibrateClawGyro();
//    			}
    			Robot.wrist.stopWrist();
    		}
    	}
    	else if(Robot.wrist.getPosition() == WristPostion.Level)
    	{
    		Robot.wrist.moveClawToLevel();
    	}
    	else if(Robot.wrist.getPosition() == WristPostion.Straight)
    	{
    		Robot.wrist.moveClawToStraight();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean loggedIsFinished() {
//    	return false;
    	return util.isStalled();
    }

    // Called once after isFinished returns true
    protected void loggedEnd() {
    	Robot.wrist.stopWrist();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void loggedInterrupted() {
    	Robot.wrist.stopWrist();
    }

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
