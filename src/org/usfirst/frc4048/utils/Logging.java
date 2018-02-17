package org.usfirst.frc4048.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.TimerTask;

import org.usfirst.frc4048.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Logging {

	public static enum MessageLevel {
		InfoMessage, WarningMessage, ErrorMessage
	}

	public static enum Subsystems {
		ARM, CLAW, DRIVETRAIN, INTAKE, CLIMBER, POWERDISTPANEL
	}

	private boolean writeLoggingGap = false;
	private static final int MSG_QUEUE_DEPTH = 512;
	private java.util.Timer executor;
	private long period;
	private WorkQueue wq;
	public static DecimalFormat df5 = new DecimalFormat(".#####");
	public static DecimalFormat df4 = new DecimalFormat(".####");
	public static DecimalFormat df3 = new DecimalFormat(".###");
	private int counter;

	public Logging(long period, WorkQueue wq) {
		this.period = period;
		this.wq = wq;
		counter = 0;
	}

	public void startThread() {
		this.executor = new java.util.Timer();
		this.executor.schedule(new ConsolePrintTask(wq, this), 0L, this.period);
	}

	public void traceSubsystem(Subsystems s, double... vals) {
		traceSubsystem(s, vals, (String[]) null);
	}

	public void traceSubsystem(Subsystems s, String... vals) {
		traceSubsystem(s, (double[]) null, vals);
	}

	public void traceSubsystem(Subsystems s, String vals1[], double... vals2) {
		traceSubsystem(s, vals2, vals1);
	}

	public void traceSubsystem(Subsystems s, double vals1[], String... vals2) {
		if (DriverStation.getInstance().isEnabled() && counter % 5 == 0) {
			final StringBuilder sb = new StringBuilder();
			sb.append(df3.format(Timer.getFPGATimestamp()));
			sb.append(",");
			sb.append(s.name());
			sb.append(",");
			if (vals1 != null) {
				for (final double v : vals1) {
					sb.append(df5.format(v));
					sb.append(",");
				}
			}
			if (vals2 != null) {
				for (final String v : vals2) {
					sb.append("\"").append(v).append("\"");
					sb.append(",");
				}
			}
			traceMessage(sb);
		}
		counter += 1;
	}

	private void traceMessage(final StringBuilder sb) {
		if (writeLoggingGap) {
			if (wq.append("LOGGING GAP!!"))
				writeLoggingGap = false;
		}
		if (!wq.append(sb.toString()))
			writeLoggingGap = true;
	}

	public void traceMessage(MessageLevel ml, String message) {
		final StringBuilder sb = new StringBuilder();
		sb.append(df3.format(Timer.getFPGATimestamp()));
		sb.append(",");
		sb.append(ml.name());
		sb.append(",");
		sb.append("\"").append(message).append("\"");
		traceMessage(sb);
	}
	
	public void printHeadings()
	{
		traceSubsystem(Subsystems.DRIVETRAIN, Robot.drivetrain.drivetrianHeadings());
		traceSubsystem(Subsystems.ARM, Robot.arm.armHeadings());
		traceSubsystem(Subsystems.CLAW, Robot.claw.clawHeadings());
		traceSubsystem(Subsystems.INTAKE, Robot.intake.intakeHeadings());
		traceSubsytem(Subsystems.POWERDISTPANEL, Robot.powerdistpanel.pdpHeadings());
	}
	
	private class ConsolePrintTask extends TimerTask {
		PrintWriter log;
		final WorkQueue wq;
		final Logging l;

		private ConsolePrintTask(WorkQueue wq, Logging l) {
			this.l = l;
			this.wq = wq;
			try {

				File file = new File("/home/lvuser/Logs");
				if (!file.exists()) {
					if (file.mkdir()) {
						System.out.println("Log Directory is created!");
					} else {
						System.out.println("Failed to create Log directory!");
					}
				}
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
				dateFormat.setTimeZone(TimeZone.getTimeZone("EST5EDT"));
				this.log = new PrintWriter("/home/lvuser/Logs/" + dateFormat.format(date) + "-Log.txt", "UTF-8");
				
				printHeadings();
				
				log.flush();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private String FileHeading() {
			return "";
		}

		public void print() {
			// Log all events, we want this done also when the robot is disabled
			for (;;) {
				final String message = wq.getNext();
				if (message != null)
					log.println(message);
				else
					break;
			}
			log.flush();
		}

		/**
		 * Called periodically in its own thread
		 */
		public void run() {
			print();
		}
	}
}