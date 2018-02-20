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
    	Robot.logging.traceSubsystem(Logging.Subsystems.POWERDISTPANEL, false, pdp.getVoltage(), pdp.getTotalCurrent(), pdp.getCurrent(RobotMap.PDP_STEERING_FR), pdp.getCurrent(RobotMap.PDP_STEERING_FL),
    			pdp.getCurrent(RobotMap.PDP_STEERING_RL), pdp.getCurrent(RobotMap.PDP_STEERING_RR), pdp.getCurrent(RobotMap.PDP_DRIVE_FR), pdp.getCurrent(RobotMap.PDP_DRIVE_FL), pdp.getCurrent(RobotMap.PDP_DRIVE_RL)
    			, pdp.getCurrent(RobotMap.PDP_DRIVE_RR), pdp.getCurrent(RobotMap.PDP_INTAKE_DEPLOY_MOTOR), pdp.getCurrent(RobotMap.PDP_RIGHT_INTAKE_MOTOR), pdp.getCurrent(RobotMap.PDP_LEFT_INTAKE_MOTOR),
    			pdp.getCurrent(RobotMap.PDP_ARM_MOTOR), pdp.getCurrent(RobotMap.PDP_EXTENSION), pdp.getCurrent(RobotMap.PDP_WRIST_MOTOR), pdp.getCurrent(RobotMap.PDP_GRIP_MOTOR));//value
    }
    
	public String[] pdpHeadings() {
		final String vals[] = { "Total Voltage", "Total Current", "FR Steer", "FL Steer", "RL Steer", "RR Steer",
				"FR Drive", "FL Drive", "RL Drive", "RR Drive", "Intake Deploy", "Right Intake", "Left Intake",
				"Arm Motor", "Extension Motor", "Wrist Motor", "Gripper Motor" };
		return vals;
	}
}

