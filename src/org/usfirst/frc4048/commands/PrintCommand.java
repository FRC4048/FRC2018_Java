package org.usfirst.frc4048.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *THIS COMMAND IS JUST FOR TESTING PARALLEL COMMANDS
 */
public class PrintCommand extends Command {
double time;
char x;
    public PrintCommand(char x) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.x = x;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	time = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.print(x);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Timer.getFPGATimestamp() - time > 5)
    		return true;
    	else 
    		return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
