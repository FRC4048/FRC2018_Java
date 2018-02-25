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
		 INFORMATION
	}

	public static enum Subsystems {
		ARM, CLAW, WRIST, DRIVETRAIN, INTAKE, CLIMBER, POWERDISTPANEL
	}

	private boolean writeLoggingGap = false;
	private static final int MSG_QUEUE_DEPTH = 512;
	private java.util.Timer executor;
	private long period;
	private WorkQueue wq;
	public static DecimalFormat df5 = new DecimalFormat(".#####");
	public static DecimalFormat df4 = new DecimalFormat(".####");
	public static DecimalFormat df3 = new DecimalFormat(".###");

	public Logging(long period, WorkQueue wq) {
		this.period = period;
		this.wq = wq;
	}
	
	abstract static public class LoggingContext {
		private int counter = 0;
		private final Subsystems subsystem;
		
		public LoggingContext(final Subsystems subsystem) {
			this.subsystem = subsystem;
		}
		
		abstract protected String[] headings();
		
	}

	public void startThread() {
		this.executor = new java.util.Timer();
		this.executor.schedule(new ConsolePrintTask(wq, this), 0L, this.period);
	}

	public void traceSubsystem(LoggingContext context, boolean alwaysPrint, double... vals) {
		traceSubsystem(context, alwaysPrint, vals, (String[]) null);
	}
	
	public void traceSubsystem(LoggingContext context, boolean alwaysPrint, String... vals) {
		traceSubsystem(context, true, (double[]) null, vals);
	}

	public void traceSubsystem(LoggingContext context, String vals1[], double... vals2) {
		traceSubsystem(context, false, vals2, vals1);
	}

	public void traceSubsystem(LoggingContext context, boolean alwaysPrint, double vals1[], String... vals2) {
		boolean printThis = alwaysPrint;
		if (!printThis) {
			printThis = DriverStation.getInstance().isEnabled() && (context.counter % 5 == 0);
			context.counter += 1;
		}

		if (printThis) {
			final StringBuilder sb = new StringBuilder();
			sb.append(df3.format(Timer.getFPGATimestamp()));
			sb.append(",");
			sb.append(context.subsystem.name());
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
	}

	private void traceMessage(final StringBuilder sb) {
		if (writeLoggingGap) {
			if (wq.append("LOGGING GAP!!"))
				writeLoggingGap = false;
		}
		if (!wq.append(sb.toString()))
			writeLoggingGap = true;
	}

	public void traceMessage(MessageLevel ml, String ...vals) {
		final StringBuilder sb = new StringBuilder();
		sb.append(df3.format(Timer.getFPGATimestamp()));
		sb.append(",");
		sb.append(ml.name());
		sb.append(",");
		if (vals != null) {
			for (final String v : vals) {
				sb.append("\"").append(v).append("\"");
				sb.append(",");
			}
		}
		traceMessage(sb);
	}

	public void printHeadings() {
		final LoggingContext list[] = { Robot.drivetrain.loggingContext, Robot.arm.loggingContext,
				Robot.claw.loggingContext, Robot.wrist.loggingContext, Robot.intake.loggingContext,
				Robot.powerdistpanel.loggingContext, };
		for (final LoggingContext c : list) {
			traceSubsystem(c, true, null, c.headings());
		}
	}

	private class ConsolePrintTask extends TimerTask {
		PrintWriter log;
		final WorkQueue wq;
		final Logging l;

		private ConsolePrintTask(WorkQueue wq, Logging l) {
			this.l = l;
			this.wq = wq;
			log = null;
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
			if (log == null) {
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
					try {
						this.log = new PrintWriter("/media/sda1/" + dateFormat.format(date) + "-Log.csv", "UTF-8");
					} catch (Exception e) {
						this.log = new PrintWriter("/home/lvuser/Logs/" + dateFormat.format(date) + "-Log.txt",
								"UTF-8");
					}

					log.flush();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			print();
		}
	}
}
