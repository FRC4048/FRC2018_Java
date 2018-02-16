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
import org.usfirst.frc4048.subsystems.Claw;

import edu.wpi.first.wpilibj.DriverStation;
import sun.rmi.runtime.Log;


public class Logging {
	
	public static enum MessageLevel {
		InfoMessage, WarningMessage, ErrorMessage
	}
	public static enum Subsystems {
		ARM, CLAW, DRIVETRAIN, INTAKE, CLIMBER
	}
	
	private static final int MSG_QUEUE_DEPTH = 512;
	private java.util.Timer executor;
	private long period;
	private WorkQueue wq;
	public static DecimalFormat df4 = new DecimalFormat(".####");

	public Logging(long period, WorkQueue wq) {
		this.period = period;
		this.wq = wq;
	}

	public void startThread() {
		this.executor = new java.util.Timer();
		this.executor.schedule(new ConsolePrintTask(wq, this), 0L, this.period);
	}
	private void printLog(String message) {
		System.out.println(message);
	}
	public void traceSubsystem(Subsystems s, String message)
	{
		int size = wq.size();
		if (size < MSG_QUEUE_DEPTH)
		{
			try {
				wq.append(s.name() + " " + message);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (size == MSG_QUEUE_DEPTH)
		{
			try {
				wq.append("LOGGING GAP!!!");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			// we don't want to overflow the queue if logger is not reading fast enough...
		}
	}
	public void traceMessage(MessageLevel ml, String message)
	{
		int size = wq.size();
		if (size < MSG_QUEUE_DEPTH)
		{
			try {
				wq.append(ml.name() + " " + message);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (size == MSG_QUEUE_DEPTH)
		{
			try {
				wq.append("LOGGING GAP!!!");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			// we don't want to overflow the queue if logger is not reading fast enough...
		}
	}

	
	private class ConsolePrintTask extends TimerTask {
		PrintWriter log;
		WorkQueue wq;
		Logging l;
		
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
				Date date = new Date() ;
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
				dateFormat.setTimeZone(TimeZone.getTimeZone("EST5EDT"));
				this.log = new PrintWriter("/home/lvuser/Logs/" + dateFormat.format(date) + "-Log.txt", "UTF-8");
				log.println(FileHeading());
				log.flush();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private String FileHeading()
		{
			return "Arm Position \t Extention Position";
		}
		
		public void print() {
			String message;
			// Log all events, we want this done also when the robot is disabled
			int size = wq.size();
			for (int i = 0 ; i < size ; i++)
			{
				try {
					message = wq.getNext();
					log.println(message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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