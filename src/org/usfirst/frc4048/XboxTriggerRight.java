package org.usfirst.frc4048;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class XboxTriggerRight extends Trigger {
	private final XboxController xboxController;

	public XboxTriggerRight(XboxController xboxController) {
		this.xboxController = xboxController;
	}

	@Override
	public boolean get() {
		if (xboxController.getTriggerAxis(Hand.kRight) >= 0.75)
			return true;
		else
			return false;
	}

}
