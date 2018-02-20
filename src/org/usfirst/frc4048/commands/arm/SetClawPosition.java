package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.subsystems.Claw;
import org.usfirst.frc4048.subsystems.Claw.WristPostion;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetClawPosition extends Command {

	protected final WristPostion position;
	
    public SetClawPosition(Claw.WristPostion position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	//Does not require a subsystem because the default command would be cancelled
    	requires(Robot.claw);
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.claw.setPosition(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
