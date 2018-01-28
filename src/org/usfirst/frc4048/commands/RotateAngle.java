package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateAngle extends Command {

	private double angle;
	
	private final double PID_P = 310.0;
    private final double ANGLE_TOLERANCE = 3;	//Defines angle tolernace used when going to a specific location
    private final double MAX_SPEED = 0.22;
    private final double SPEED_LOWER = 0.14;
	
    public RotateAngle(double angle) {
    	requires(Robot.drivetrain);
    	
    	this.angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	final double currAngle = Robot.drivetrain.getGyro();
    	
    	if(Math.abs(angle - currAngle) < ANGLE_TOLERANCE)
    	{
    		Robot.drivetrain.move(0, 0, 0);
    	}
    	else
    	{
	    	double speed = 0.0;
	    	
	    	if(Math.abs(currAngle - angle) > 180)
	    	{
	    		if(currAngle > angle)
	    			angle += 360;
	    		else
	    			angle -= 360;
	    	}
	    	
	    	speed = (angle - currAngle) / PID_P;
	        if (angle > currAngle) {
	            speed += SPEED_LOWER;
	        } else {
	            speed -= SPEED_LOWER;
	        }
	        if (Math.abs(speed) > MAX_SPEED) {
	            if (speed > 0) speed = MAX_SPEED;
	            else speed = -MAX_SPEED;
	        }
	    	
	        if (Math.abs(angle - currAngle) < ANGLE_TOLERANCE) speed = 0;	
	        
	    	Robot.drivetrain.move(0.0, 0.0, speed);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(angle - Robot.drivetrain.getGyro()) < ANGLE_TOLERANCE;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    } 

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.stop();
    }
}
