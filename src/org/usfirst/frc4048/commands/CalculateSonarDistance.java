package org.usfirst.frc4048.commands;

import java.util.Arrays;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.subsystems.Drivetrain.SonarSide;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CalculateSonarDistance extends Command {

	SonarSide side;
	double distance;
	
	private final double MIN_SPEED = 0.15;
	private final double MAX_ADJUST = 50;
	
	
	double[] sonarArray;

	int runs;
	int counter;
	//runs%5
	public CalculateSonarDistance(SonarSide side, double distance) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drivetrain);
		this.side = side;
		this.distance = distance;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		runs = 0;
		counter = 0;
		sonarArray = new double[5];
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(runs%5 == 0) {
			sonarArray[counter] = Robot.drivetrain.getSonar(side);
			counter++;
		}
	}
	private double dirSpeed(double currentDistance) {
		double speed = 0;

		if(side == SonarSide.LEFT) {
			if(currentDistance >= distance)
				speed = -MIN_SPEED;
			else if (currentDistance < distance)
				speed = MIN_SPEED;
		}
		if(side == SonarSide.RIGHT) {
			if(currentDistance < distance)
				speed = -MIN_SPEED;
			else if (currentDistance >= distance)
				speed = MIN_SPEED;
		}
		return speed;
	}
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(counter >= 5) {
			Arrays.sort(sonarArray);
			System.out.println(Arrays.toString(sonarArray));
			if(sonarArray[3] < 8 || Math.abs(sonarArray[3]-distance) > MAX_ADJUST) 
			{
				Robot.drivetrain.globalDriveDistance = 0;
				Robot.drivetrain.globalDriveDirSpeed = 0;
			}
			else 
			{
				Robot.drivetrain.globalDriveDistance = Math.abs(sonarArray[3]-distance);
				Robot.drivetrain.globalDriveDirSpeed = dirSpeed(sonarArray[3]);
			}
			return true;
		}
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