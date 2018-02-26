package org.usfirst.frc4048.commands.auto;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.arm.GrabCube;
import org.usfirst.frc4048.subsystems.Drivetrain;
import org.usfirst.frc4048.utils.Logging;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoAction extends Command {

	char switchPos;
	char scalePos;
	Action autoAction;
	
	//NOTE: These messurements and speeds are not FINAL
	public static final double LOCAL_SWITCH_SPEED = 0.4;
	public static final double DISTANCE_TO_MIDDLE_OF_SWITCH = 148;
	
	public static final double DISTANCE_TO_MIDDLE_OF_LANE = 222; //NOTE: We are not currently using this
	
	public static final double LOCAL_SCALE_SPEED = 0.5;
	public static final double DISTANCE_TO_MIDDLE_OF_SCALE = 305;
	
	public static final double AUTO_RUN_DISTANCE = 90;
	
	public static final double TRAVEL_THROUGH_SWITCH = 180;
	//200 is just a placeholder until we know a better messurement
	public static final double TRAVEL_ACROSS_SWITCH = 200;
	public static final double LANE_TO_SCALE = 80;
	
	public static final double DISTANCE_FROM_WALL_SCALE = 25;
	public static final double DISTANCE_FROM_WALL_SWITCH = 40;
	
	Command selectCmd;
	
    public AutoAction() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    }
    
    public AutoAction(char switchPos, char scalePos, Action autoAction) {
    	this.switchPos = switchPos;
    	this.scalePos = scalePos;
    	this.autoAction = autoAction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//For the labeling of the commands (Ex: AutoScaleRR) the first letter is the 
    	//robot position and the second is the position of the switch/scale
    	selectCmd = new AutoBase();
    	switch(autoAction)
    	{
    	case Baseline:
    		selectCmd = new AutoBase();
  
    		break;
    		
    	case RScale:
    		if(scalePos == 'R')
    		{
    			selectCmd = new AutoScaleRRGroup();
    		}
    		else if(scalePos == 'L')
    		{
    			//selectCmd = new AutoScaleRLGroup();
    		}
    		break;
    	case LScale:
    		if(scalePos == 'R')
    		{
    			//selectCmd = new AutoScaleLRGroup();
    		}
    		else if(scalePos == 'L')
    		{
    			selectCmd = new AutoScaleLLGroup();
    		}
    		break;
    	case RSwitch:
    		if(switchPos == 'R')
    		{
    			selectCmd = new AutoSwitchRRGroup();
    		}
    		else if(switchPos == 'L')
    		{
    			//selectCmd = new AutoSwitchRLGroup();
    		}
    		break;
    	case LSwitch:
    		if(switchPos == 'R')
    		{
    			//selectCmd = new AutoSwitchLRGroup();
    		}
    		else if(switchPos == 'L')
    		{
    			selectCmd = new AutoSwitchLLGroup();
    		}
    		break;
    	case RLocalSwitchPriority:
    		if(switchPos == 'R')
    		{
    			selectCmd = new AutoSwitchRRGroup();
    		}
    		else if (scalePos == 'R')
    		{
    			selectCmd = new AutoScaleRRGroup();
    		}
    		break;
    	case LLocalSwitchPriority:
    		if(switchPos == 'L')
    		{
    			selectCmd = new AutoSwitchLLGroup();
    		}
    		else if (scalePos == 'L')
    		{
    			selectCmd = new AutoScaleLLGroup();
    		}
    		break;
    	case RLocalScalePriority:
    		if(scalePos == 'R')
    		{
    			selectCmd = new AutoScaleRRGroup();
    		}
    		else if (switchPos == 'R')
    		{
    			selectCmd = new AutoSwitchRRGroup();
    		}
    		break;
    	case LLocalScalePriority:
    		if(scalePos == 'L')
    		{
    			selectCmd = new AutoScaleLLGroup();
    		}
    		else if (switchPos == 'L')
    		{
    			selectCmd = new AutoSwitchLLGroup();
    		}
    		break;
    	case MiddleSwitch:
    		if(switchPos == 'L')
    		{
    			selectCmd = new AutoSwitchMLGroup();
    		}
    		else if (switchPos == 'R')
    		{
    			selectCmd = new AutoSwitchMRGroup();
    		}
    		break;
    	case Nothing:
    		selectCmd = new GrabCube();
    		break;
    	default:
    		
    		break;
    	}
    	SmartDashboard.putString("Running Auto Command ", selectCmd.getName());    
    	Robot.logging.traceMessage(Logging.MessageLevel.INFORMATION,  "Autonomous Command:" + selectCmd.getName());
    	selectCmd.start();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
