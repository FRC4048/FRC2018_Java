package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.LoggedCommand;
import org.usfirst.frc4048.subsystems.Claw;
import org.usfirst.frc4048.subsystems.Wrist.WristPostion;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetClawPosition extends LoggedCommand {

	protected final WristPostion position;
	
    public SetClawPosition(WristPostion position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(String.format("Now running SetClawPosition, position: %s", position.toString()));
    	this.position = position;
    	
    	//Does not require a subsystem because the default command would be cancelled
    }

    // Called just before this Command runs the first time
    protected void loggedInitialize() {
    	Robot.wrist.setPosition(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void loggedExecute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean loggedIsFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void loggedEnd() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
	@Override
	protected void loggedInterrupted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
