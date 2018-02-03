package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.subsystems.Arm;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Schema;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class GetCube extends Command {

	Command moveArmExchange;
	Command lowerIntake;
	Command intakeCube;
	Command levelClaw;
	Command closeClaw;
	Command openClaw;
	Command finetuneDown;
	
    public GetCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	setTimeout(5.0);
    	
    	moveArmExchange = new MoveArm(ArmPositions.Exchange);
    	lowerIntake = new LowerIntake();
    	intakeCube = new IntakeCube();
    	levelClaw = new MoveClaw(0.0);
    	openClaw = new OpenClaw();
    	closeClaw = new CloseClaw();
    	finetuneDown = new FinetuneDown();
    	
    	//Add something to check for cube in claw
    	
    	moveArmExchange.start();
    	lowerIntake.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!lowerIntake.isCompleted() && !moveArmExchange.isCompleted()) {
    		moveArmExchange = new MoveArm(ArmPositions.Exchange);
    		if(!intakeCube.isRunning() && !intakeCube.isCompleted()  &&
    		   !openClaw.isRunning() && ! openClaw.isCompleted()) {
    			intakeCube.start();
    			openClaw.start();
    		} else {
    			if(!levelClaw.isRunning() && !levelClaw.isCompleted()) {
    				levelClaw.start();
    			} else {
    				if(!finetuneDown.isRunning() && !finetuneDown.isCompleted()) {
    					finetuneDown.start();
    				} else {
    					if(Robot.claw.aboveCube() && !closeClaw.isRunning() && !closeClaw.isCompleted()) {
    						closeClaw.start();
    						finetuneDown.cancel();
    					} else {
	    					if(!moveArmExchange.isRunning() && !moveArmExchange.isCompleted()) {
	    						moveArmExchange.start();
	    					}
    					}
    				}
    			}
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() && !moveArmExchange.isCompleted() && !lowerIntake.isCompleted() && 
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
