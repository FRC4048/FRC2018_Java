package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.subsystems.Claw;

/**
 *
 */
public class SetClawPositionAndWait extends SetClawPosition {
	private final GroupCommandCallback callback;
	
    public SetClawPositionAndWait(final Claw.WristPostion position) {
    	super(position);
    	this.callback = GroupCommandCallback.NONE; 
    }
    
    public SetClawPositionAndWait(final GroupCommandCallback callback, final Claw.WristPostion position) {
    	super(position);
    	this.callback = callback; 
    }

    protected boolean isFinished() {
        return Robot.claw.getPosition().equals(position) || isTimedOut();
    }
    
    protected void end() {
    	callback.doCancel(isTimedOut());
    }

    protected void interrupted() {
    	callback.doCancel(true);
    }

}
