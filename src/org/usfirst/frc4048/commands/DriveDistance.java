package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistance extends Command {

	private double distance;
	private double fwd, dir, rot;
	
	/**
	 * Moves robot a certain distance
	 * @param distance - defines distance robot needs to travel
	 * @param fwd - sets forward/backward speed robot travels at
	 * @param dir - sets right/left speed robot travels at
	 * @param rot - sets clockwise/counter-clockwise speed robot travels at
	 */
    public DriveDistance(double distance, double fwd, double dir, double rot) {
    	
    	requires(Robot.drivetrain);
    	
        this.distance = distance + Robot.drivetrain.getDistance();
        this.fwd = fwd;
        this.dir = dir;
        this.rot = rot;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.drivetrain.setZero();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(Robot.drivetrain.getDistance() < distance)
    		Robot.drivetrain.move(fwd, dir, rot);
    	
     }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.drivetrain.getDistance() >= distance;
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
