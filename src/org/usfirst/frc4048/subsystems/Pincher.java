//package org.usfirst.frc4048.subsystems;
//
//import org.usfirst.frc4048.RobotMap;
//import org.usfirst.frc4048.commands.Pinch;
//import org.usfirst.frc4048.utils.Logging;
//
//import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
//import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
//import com.ctre.phoenix.motorcontrol.NeutralMode;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//
//import edu.wpi.first.wpilibj.command.Subsystem;
//
///**
// *
// */
//public class Pincher extends Subsystem {
//
//	WPI_TalonSRX pincherMotor = RobotMap.clawgripMotor;
//	
//	private final double PINCHER_OPEN_SPEED = 0.6;
//	private final double PINCHER_CLOSE_SPEED = 0.0;
//	
//	private final int TIMEOUT = 100;
//
//	public enum PincherState {
//		Open, Close
//	}
//	
//	private PincherState state = PincherState.Close;
//	
//	public Pincher()
//	{
//		super("Pincher");
//		
//		pincherMotor.setNeutralMode(NeutralMode.Brake);
//    	pincherMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
//    												LimitSwitchNormal.NormallyOpen, 
//													TIMEOUT);
//    	pincherMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
//													LimitSwitchNormal.NormallyOpen, 
//													TIMEOUT);
//	}
//	
//    public void initDefaultCommand() {
//        // Set the default command for a subsystem here.
//        //setDefaultCommand(new MySpecialCommand());
//    	
//    	setDefaultCommand(new Pinch());
//    }
//    
//    public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(Logging.Subsystems.PINCHER) {
//
//		@Override
//		protected void addAll() {
//			add("Pincher Open", pincherIsOpen());
//			add("Pincher Closed", pincherIsClosed());
//		}
//    	
//    };
//    
//    @Override
//    public void periodic()
//    {
//    	loggingContext.writeData();
//    }
//
//    
//    public void openPincher()
//    {
//    	state = PincherState.Open;
//    	pincherMotor.set(ControlMode.PercentOutput, PINCHER_OPEN_SPEED);
//    }
//    
//    /*
//     * Speed is zero because of the spring
//     */
//    public void closePincher()
//    {
//    	state = PincherState.Close;
//    	pincherMotor.set(PINCHER_CLOSE_SPEED);
//    }
//    
//    public boolean pincherIsOpen()
//    {
//    	return pincherMotor.getSensorCollection().isFwdLimitSwitchClosed();
//    }
//    
//    public boolean pincherIsClosed()
//    {
//    	return pincherMotor.getSensorCollection().isRevLimitSwitchClosed();
//    }
//    
//    public PincherState getPincherState()
//    {
//    	return state;
//    }
//}
//
