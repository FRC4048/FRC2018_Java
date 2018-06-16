package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.OpenPincher;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pincher extends Subsystem {
	WPI_TalonSRX pincherMotor = RobotMap.clawgripMotor;
	
	private final double OPEN_SPEED = -0.85;
	private final double CLOSE_SPEED = 1.0;
	private static int TIMEOUT = 100;
	

	public Pincher() {
		super("Pincher");
		pincherMotor.setNeutralMode(NeutralMode.Brake);
		pincherMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, TIMEOUT);
		pincherMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, TIMEOUT);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	@Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void closePincher() {
    	pincherMotor.set(CLOSE_SPEED);
    }
    
    public void openPincher() {
    	pincherMotor.set(ControlMode.PercentOutput, OPEN_SPEED);

    }
    
    public void stopMotor() { 
    	pincherMotor.set(0.0);
    }
    
    public boolean isOpen() {
    	return pincherMotor.getSensorCollection().isRevLimitSwitchClosed();
    }
    
    public boolean isClose() {
    	return pincherMotor.getSensorCollection().isFwdLimitSwitchClosed();
    }
}

	