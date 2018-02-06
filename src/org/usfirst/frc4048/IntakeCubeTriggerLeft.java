package org.usfirst.frc4048;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class IntakeCubeTriggerLeft extends Trigger {
	private final XboxController xboxController;

	public IntakeCubeTriggerLeft(XboxController xboxController) {
		this.xboxController = xboxController;
	}

	@Override
	public boolean get() {
		if (xboxController.getTriggerAxis(Hand.kLeft) > 0.75)
			return true;
		else
			return false;
	}

}
