package org.usfirst.frc4048.commands;

import java.util.Arrays;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.subsystems.Drivetrain.SonarSide;
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.Logging.MessageLevel;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class CalculateSonarDistance extends LoggedCommand {
	
	SonarSide side;
	double distance;
	
	private final double MIN_SPEED = 0.25;
	private final double MAX_ADJUST = 100;
	
	
	double[] sonarArray;

	int runs;
	int counter;
	//runs%5
	public CalculateSonarDistance(SonarSide side, double distance) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super(String.format("CalculateSonarDistance, Side: &s, Distance: &f", side.toString(), distance));
		
		requires(Robot.drivetrain);
		this.side = side;
		this.distance = distance;
	}
	
	 

	// Called just before this Command runs the first time
	protected void loggedInitialize() {
		runs = 0;
		counter = 0;
		sonarArray = new double[5];
	}

	// Called repeatedly when this Command is scheduled to run
	protected void loggedExecute() {
		if(runs%5 == 0) {
			sonarArray[counter] = Robot.drivetrain.getSonar(side);
			counter++;
		}	    
		runs++; //Make sure we don't get sonar more frequently than once every 5 runs
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
	protected boolean loggedIsFinished() {
		if(counter >= 5) {
			Arrays.sort(sonarArray);
			
			if(sonarArray[2] < 8 || Math.abs(sonarArray[2]-distance) > MAX_ADJUST) 
			{
				Robot.drivetrain.globalDriveDistance = 0;
				Robot.drivetrain.globalDriveDirSpeed = 0;
			}
			else 
			{
				Robot.drivetrain.globalDriveDistance = Math.abs(sonarArray[2]-distance);
				Robot.drivetrain.globalDriveDirSpeed = dirSpeed(sonarArray[2]);
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append(sonarArray);
			Robot.logging.traceMessage(MessageLevel.INFORMATION, sb.toString());
			return true;
		}
		else
			return false;
	}

	// Called once after isFinished returns true
	protected void loggedEnd() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void loggedInterrupted() {
	}



	@Override
	protected void loggedCancel() {
		// TODO Auto-generated method stub
		
	}
}
