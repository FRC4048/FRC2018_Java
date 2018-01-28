package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeCube extends Command {

    public IntakeCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.intake.hasCube())
    	{
	    	if(Robot.oi.getXboxController().getTriggerAxis(Hand.kLeft) > 0.75)
	    	{
	    		Robot.intake.adjustCube();
	    		System.out.println("Adjusting Intake");
	    	}
	    	else
	    	{
	    		Robot.intake.intakeCube();
	    		System.out.println("Running Intake");
	    	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.intake.hasCube();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.stopIntake();
    	System.out.println("stopping Intake");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.stopIntake();
    	System.out.println("stopping Intake");
    }
}
