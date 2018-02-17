package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.utils.Logging.MessageLevel;

import edu.wpi.first.wpilibj.command.Command;

abstract public class LoggedCommand extends Command {
	private final String ident;

	public LoggedCommand(final String ident) {
		this.ident = ident;
	}

	private void log(final String text) {
		final String logEntry = this.getClass().getSimpleName() + " " + ident + " " + text;
		Robot.logging.traceMessage(MessageLevel.InfoMessage,logEntry);
	}

	@Override
	final protected boolean isFinished() {
		final boolean result = loggedIsFinished();
		if (result)
			log(String.format("isFinished()"));
		return result;
	}

	abstract protected boolean loggedIsFinished();

	@Override
	final protected void initialize() {
		log("initialize()");
		loggedInitialize();
	}

	abstract protected void loggedInitialize();

	@Override
	final protected void execute() {
		log("execute()");
		loggedExecute();
	}

	abstract protected void loggedExecute();

	@Override
	final protected void end() {
		log("end()");
		loggedEnd();
	}

	abstract protected void loggedEnd();

	@Override
	final protected void interrupted() {
		log("interrupted()");
		loggedInterrupted();
	}

	abstract protected void loggedInterrupted();

	@Override
	final protected synchronized boolean isTimedOut() {
		final boolean result = super.isTimedOut();
		if (result)
			log(String.format("isTimedOut()"));
		return result;
	}

	@Override
	final public synchronized void cancel() {
		log("cancel()");
		loggedCancel();
	}

	abstract protected void loggedCancel();
}
