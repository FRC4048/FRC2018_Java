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
    
	public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(Logging.Subsystems.POWERDISTPANEL) {

		@Override
		protected void addAll() {
			add("Total Voltage", pdp.getVoltage());
			add("Total Current", pdp.getTotalCurrent());
			add("FR Steer", pdp.getCurrent(RobotMap.PDP_STEERING_FR));
			add("FL Steer", pdp.getCurrent(RobotMap.PDP_STEERING_FL));
			add("RL Steer", pdp.getCurrent(RobotMap.PDP_STEERING_RL));
			add("RR Steer", pdp.getCurrent(RobotMap.PDP_STEERING_RR));
			add("FR Drive", pdp.getCurrent(RobotMap.PDP_DRIVE_FR));
			add("FL Drive", pdp.getCurrent(RobotMap.PDP_DRIVE_FL));
			add("RL Drive", pdp.getCurrent(RobotMap.PDP_DRIVE_RL));
			add("RR Drive", pdp.getCurrent(RobotMap.PDP_DRIVE_RR));
			add("Intake Deploy", pdp.getCurrent(RobotMap.PDP_INTAKE_DEPLOY_MOTOR));
			add("Right Intake", pdp.getCurrent(RobotMap.PDP_RIGHT_INTAKE_MOTOR));
			add("Left Intake", pdp.getCurrent(RobotMap.PDP_LEFT_INTAKE_MOTOR));
			add("Arm Motor", pdp.getCurrent(RobotMap.PDP_ARM_MOTOR));
			add("Elbow Motor", pdp.getCurrent(RobotMap.PDP_ELBOW));
			add("Wrist Motor", pdp.getCurrent(RobotMap.PDP_WRIST_MOTOR));
			add("Gripper Motor", pdp.getCurrent(RobotMap.PDP_GRIP_MOTOR));
		}

	};

    
    public void periodic() {
    	/*
    	 * Logging:
    	 *  Battery Voltage
    	 *  Swerve Drive Motors
    	 *  Swerve Steer Motors
    	 *  
    	 */
    	loggingContext.writeData();
    }
    
}

