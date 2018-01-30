package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GetCube extends Command {

	Command moveArm = new MoveArm(ArmPositions.Ground);
	Command lowerIntake = new LowerIntake();
	Command intakeCube = new IntakeCube();
	
    public GetCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	moveArm.start();
    	lowerIntake.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!lowerIntake.isRunning())
    	{
    		if(!intakeCube.isRunning()) {
    			intakeCube.start();
    		}
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !moveArm.isRunning() && !lowerIntake.isRunning() && !intakeCube.isRunning();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
