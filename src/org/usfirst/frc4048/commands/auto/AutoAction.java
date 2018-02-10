package org.usfirst.frc4048.commands.auto;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.subsystems.Drivetrain;

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
	public static final double LOCAL_SWITCH_SPEED = 0.3;
	public static final double DISTANCE_TO_MIDDLE_OF_SWITCH = 145;
	
	public static final double DISTANCE_TO_MIDDLE_OF_LANE = 222;
	
	public static final double LOCAL_SCALE_SPEED = 0.35;
	public static final double DISTANCE_TO_MIDDLE_OF_SCALE = 300;
	
	public static final double AUTO_RUN_DISTANCE = 112;
	
	public static final double TRAVEL_THROUGH_SWITCH = 180;
	//200 is just a placeholder until we know a better messurement
	public static final double TRAVEL_ACROSS_SWITCH = 200;
	public static final double LANE_TO_SCALE = 80;
	
	
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
    			selectCmd = new AutoScaleRLGroup();
    		}
    		else 
    		{
    			selectCmd = new AutoBase();
    		}
    		break;
    	case LScale:
    		if(scalePos == 'R')
    		{
    			selectCmd = new AutoScaleLRGroup();
    		}
    		else if(scalePos == 'L')
    		{
    			selectCmd = new AutoScaleLLGroup();
    		}
    		else 
    		{
    			selectCmd = new AutoBase();
    		}
    		break;
    	case RSwitch:
    		if(switchPos == 'R')
    		{
    			selectCmd = new AutoSwitchRRGroup();
    		}
    		else if(switchPos == 'L')
    		{
    			selectCmd = new AutoSwitchRLGroup();
    		}
    		else 
    		{
    			selectCmd = new AutoBase();
    		}
    		break;
    	case LSwitch:
    		if(switchPos == 'R')
    		{
    			selectCmd = new AutoSwitchLRGroup();
    		}
    		else if(switchPos == 'L')
    		{
    			selectCmd = new AutoSwitchLLGroup();
    		}
    		else 
    		{
    			selectCmd = new AutoBase();
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
    		else
    		{
    			selectCmd = new AutoBase();
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
    		else
    		{
    			selectCmd = new AutoBase();
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
    		else
    		{
    			selectCmd = new AutoBase();
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
    		else
    		{
    			selectCmd = new AutoBase();
    		}
    		break;
    	case Nothing:
    		break;
    	default:
    		
    		break;
    	}
    	SmartDashboard.putString("Running Auto Command ", selectCmd.getName());    	
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
