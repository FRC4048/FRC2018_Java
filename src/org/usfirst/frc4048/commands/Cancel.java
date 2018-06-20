package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.subsystems.Arm.Position;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Cancel extends Command {

    public Cancel() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(6.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.arm.elbwAtPos(Position.HOME)) {
    		Robot.arm.elbwToPos(Position.HOME);
    	}
    	if(!Robot.arm.armAtPos(Position.HOME) && Robot.arm.elbwAtPos(Position.HOME)) {
    		Robot.arm.armToPos(Position.HOME);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() || (Robot.arm.elbwAtPos(Position.HOME) && Robot.arm.armAtPos(Position.HOME));
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
