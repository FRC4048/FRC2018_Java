package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.subsystems.Claw;
import org.usfirst.frc4048.subsystems.Wrist.WristPostion;

/**
 *
 */
public class SetClawPositionAndWait extends SetClawPosition {
	
	private final GroupCommandCallback callback;
	
    public SetClawPositionAndWait(final WristPostion position) {
    	super(position);
    	this.callback = GroupCommandCallback.NONE;
    	setTimeout(3.0);
    }
    
    public SetClawPositionAndWait(final GroupCommandCallback callback, final WristPostion position) {
    	super(position);
    	this.callback = callback; 
    }

    //TODO Get position returns an enum, and should not be used for determining current position
    protected boolean loggedIsFinished() {
        return (position.equals(WristPostion.Level) && Robot.wrist.isLevel()) 
        	|| (position.equals(WristPostion.Compact) && Robot.wrist.clawUp()) 
        	|| (position.equals(WristPostion.Straight) && Robot.wrist.isStraight()) ||isTimedOut();
    }
    
    protected void loggedEnd() {
    	callback.doCancel(isTimedOut());
    	Robot.wrist.stopWrist();
    }

    protected void loggedInterrupted() {
    	callback.doCancel(true);
    	Robot.wrist.stopWrist();
    }

}
