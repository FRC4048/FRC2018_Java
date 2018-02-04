package org.usfirst.frc4048.commands.auto;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.DriveDistance;
import org.usfirst.frc4048.commands.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoBase extends Command {

    public AutoBase() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//The distance to the autoline is 120 - (32.5[robotLength] + 6[bumpers])
    	//We want to move atleast 2 inches beyond the auto line without hitting the switch
    	new DriveDistance(83.5, .3, 0, 0).start();
    	
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