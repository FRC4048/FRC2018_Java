package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.subsystems.Drivetrain.SonarSide;
import org.usfirst.frc4048.utils.Logging;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class PowerDistPanel extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static PowerDistributionPanel pdp = RobotMap.pdp;
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void init() {
    	  pdp = new PowerDistributionPanel(0);
    }
    
    public void periodic() {
    	/*
    	 * Logging:
    	 *  Battery Voltage
    	 *  Swerve Drive Motors
    	 *  Swerve Steer Motors
    	 *  
    	 */
    	Robot.logging.traceSubsystem(Logging.Subsystems.POWERDISTPANEL, 
    			"Total Voltage \t Total Current \t FR Steer \t FL Steer \t RL Steer \t RR Steer \t FR Drive \t FL Drive \t RL Drive \t RL Drive \t Intake Deploy \t Right Intake \t Left Intake \t Arm Motor \t Extension Motor \t Wrist Motor \t Gripper Motor");//header
    	Robot.logging.traceSubsystem(Logging.Subsystems.POWERDISTPANEL, Logging.df5.format(pdp.getVoltage()) + "\t", );//value
    }
}

