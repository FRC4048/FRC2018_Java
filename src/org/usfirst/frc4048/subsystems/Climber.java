package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.Climb;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private final SpeedController climbMotor = RobotMap.climberclimbMotor;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new Climb());
	}

	public void winchUp() {
		climbMotor.set(1);
	}

	public void winchDown() {
		climbMotor.set(-1);
	}

	public void stopWinch() {
		climbMotor.set(0);
	}
}

