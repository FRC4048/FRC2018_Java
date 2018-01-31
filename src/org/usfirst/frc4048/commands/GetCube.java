package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.subsystems.Arm;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GetCube extends Command {

	Command moveArmGround;
	Command moveArmExchange;
	Command lowerIntake;
	Command intakeCube;
	Command levelClaw;
	Command closeClaw;
	Command openClaw;
	
    public GetCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	setTimeout(5.0);
    	
    	moveArmGround = new MoveArm(ArmPositions.Cube);
    	moveArmExchange = new MoveArm(ArmPositions.Exchange);
    	lowerIntake = new LowerIntake();
    	intakeCube = new IntakeCube();
    	levelClaw = new MoveClaw(0.0);
    	
    	//Add something to check for cube in claw
    	
    	moveArmExchange.start();
    	lowerIntake.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!lowerIntake.isCompleted() && !moveArmExchange.isCompleted())
    	{
    		moveArmExchange = new MoveArm(ArmPositions.Exchange);
    		if(!intakeCube.isRunning() && !intakeCube.isCompleted()  &&
    		   !openClaw.isRunning() && ! openClaw.isCompleted()) {
    			intakeCube.start();
    			openClaw.start();
    		}
    		else {
    			if(!levelClaw.isRunning() && !levelClaw.isCompleted()) {
    				levelClaw.start();
    			}
    			else {
    				if(!moveArmGround.isRunning() && !moveArmGround.isCompleted()) {
    					moveArmGround.start();
    				}
    				else {
    					
    				}
    			}
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() && !moveArmExchange.isCompleted() && !moveArmGround.isCompleted() && !lowerIntake.isCompleted() && 
        	   !intakeCube.isCompleted();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
