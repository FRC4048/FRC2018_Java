package org.usfirst.frc4048.commands;

/**
 * Interface for Commands that are executed as part of a GroupCommand which also
 * have a Timeout. If the subcommand timesout, then we callback to the
 * GroupCommand to stop the remaining steps.
 * 
 * Today, the clearing of the remaining steps is done by clearing the schedulers
 * queue.
 * 
 * @author Team 4048
 *
 */

public interface GroupCommandCallback {

	/**
	 * Callback function to test if the command has timedout and therefore the group
	 * command needs to be stopped. The sub command needs to call this function in
	 * the end() method.
	 */
	public void doCancel(final boolean isTimedOut);

	static public final GroupCommandCallback NONE = new GroupCommandCallback() {

		@Override
		public void doCancel(final boolean isTimedOut) {
		}

	};

}
