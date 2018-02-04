package org.usfirst.frc4048.commands.auto;

public enum Action {
	/* B  = baseline
	 * CR = Scale Robot Right 
	 * CL = Scale Robot Left
	 * WR = Switch Robot Right
	 * WL = Switch Robot Left
	 * LR = Local Robot Right
	 * LL = Local Robot Left
	 * 
	 * NOTE: Local means that the robot will choose to go to either the switch or the scale
	 * depending on the position of the scale/switch
	 */
	
	B,CR,CL,WR,WL,LR,LL,N
}
