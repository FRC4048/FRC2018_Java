//package org.usfirst.frc4048.commands.arm;
//
//import org.usfirst.frc4048.Robot;
//import org.usfirst.frc4048.commands.GroupCommandCallback;
//import org.usfirst.frc4048.subsystems.Claw;
//import org.usfirst.frc4048.subsystems.Wrist.WristPostion;
//
///**
// *
// */
//public class SetClawPositionAndWait extends SetClawPosition {
//	
//	private final GroupCommandCallback callback;
//	
//    public SetClawPositionAndWait(final WristPostion position) {
//    	this(GroupCommandCallback.NONE, position);
//    }
//    
//    public SetClawPositionAndWait(final GroupCommandCallback callback, final WristPostion position) {
//    	super(position);
//    	this.callback = callback; 
//    }
//    
//    @Override
//    protected void loggedInitialize()
//    {
//    	super.loggedInitialize();
//    	setTimeout(5.0);
//    }
//
//    protected boolean loggedIsFinished() {
//        return (position.equals(WristPostion.Level) && Robot.wrist.isLevel()) 
//        	|| (position.equals(WristPostion.Compact) && Robot.wrist.clawUp()) 
//        	|| (position.equals(WristPostion.Straight) && Robot.wrist.isStraight()) ||isTimedOut();
//    }
//    
//    protected void loggedEnd() {
//    	callback.doCancel(isTimedOut());
//    	Robot.wrist.stopWrist();
//    }
//
//    protected void loggedInterrupted() {
//    	callback.doCancel(true);
//    	Robot.wrist.stopWrist();
//    }
//
//}
