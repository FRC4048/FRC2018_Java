package org.usfirst.frc4048.commands.auto;
/**Baseline  = baseline,
 * RScale = Scale Robot Right, 
 * LScale = Scale Robot Left,
 * RSwitch = Switch Robot Right,
 * LSwitch = Switch Robot Left,
 * RLocalSwitchPriority = Local Robot Right(Switch Priority),
 * LLocalSwitchPriority = Local Robot Left(Switch priority),
 * RLocalScalePriority = Local Robot Right(Scale Priority),
 * LLocalScalePriority = Local Robot Left(Scale Priority),
 * MiddleSwitch = Do the Switch from the center position
 * Nothing = Do nothing,
 * 
 * NOTE: Local means that the robot will choose to go to either the switch or the scale
 * depending on the position of the scale/switch
 */
public enum Action {
	
	//For the labeling of the commands (Ex: AutoScaleRR) the first letter is the 
	//robot position and the second is the position of the switch/scale

	Baseline,RScale,LScale,RSwitch,LSwitch,RLocalSwitchPriority,LLocalSwitchPriority,Nothing,RLocalScalePriority,LLocalScalePriority,MiddleSwitch 
	
	
}
