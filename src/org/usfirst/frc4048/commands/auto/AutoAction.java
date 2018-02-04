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
    	System.out.println(autoAction.toString());
    	//For the labeling of the commands (Ex: AutoScaleRR) the first letter is the 
    	//robot position and the second is the position of the switch/scale
    	switch(autoAction)
    	{
    	case B:
    		selectCmd = new AutoBase();
  
    		break;
    		
    	case CR:
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
    	case CL:
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
    	case WR:
    		if(scalePos == 'R')
    		{
    			selectCmd = new AutoSwitchRRGroup();
    		}
    		else if(scalePos == 'L')
    		{
    			selectCmd = new AutoSwitchRLGroup();
    		}
    		else 
    		{
    			selectCmd = new AutoBase();
    		}
    		break;
    	case WL:
    		if(scalePos == 'R')
    		{
    			selectCmd = new AutoSwitchLRGroup();
    		}
    		else if(scalePos == 'L')
    		{
    			selectCmd = new AutoSwitchLLGroup();
    		}
    		else 
    		{
    			selectCmd = new AutoBase();
    		}
    		break;
    	case LR:
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
    	case LL:
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
    	default:
    		
    		break;
    	}
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