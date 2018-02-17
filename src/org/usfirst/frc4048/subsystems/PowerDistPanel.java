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
    	Robot.logging.traceSubsystem(Logging.Subsystems.POWERDISTPANEL, "");//header
    	Robot.logging.traceSubsystem(Logging.Subsystems.POWERDISTPANEL, Logging.df5.format(leftSonar.getVoltage()) + "\t" + "\t" +
    								 Logging.df3.format(getSonar(SonarSide.LEFT)));//value
    }
}

