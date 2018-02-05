package org.usfirst.frc4048.commands.auto;

public enum Action {
	/* B  = baseline
	 * CR = Scale Robot Right 
	 * CL = Scale Robot Left
	 * WR = Switch Robot Right
	 * WL = Switch Robot Left
	 * LWR = Local Robot Right(Switch Priority)
	 * LWL = Local Robot Left(Switch priority)
	 * LCR = Local Robot Right(Scale Priority)
	 * LCL = Local Robot Left(Scale Priority)
	 * 
	 * NOTE: Local means that the robot will choose to go to either the switch or the scale
	 * depending on the position of the scale/switch
	 */
	//For the labeling of the commands (Ex: AutoScaleRR) the first letter is the 
	//robot position and the second is the position of the switch/scale

	B,CR,CL,WR,WL,LWR,LWL,N,LCR,LCL 
}
