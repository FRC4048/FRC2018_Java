package org.usfirst.frc4048.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.TimerTask;

import org.usfirst.frc4048.Robot;


public class Logging {
	private java.util.Timer executor;
	private long period;

	PrintWriter log;

	public Logging(long period) {
		this.period = period;
	}

	public void startThread() {
		this.executor = new java.util.Timer();
		this.executor.schedule(new ConsolePrintTask(this), 0L, this.period);

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
			log.println(FileHeading());
			log.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private String FileHeading()
	{
		return "ARM \t Arm Position \t Extention Position";
	}
	
	public void print() {
		log.println(
				//ARM
				Robot.arm.getArmPos() + "\t" +
				Robot.arm.getExtPos() + "\t" +
				"debug"
				);
		
		log.flush();
		
	}
	
	
	private class ConsolePrintTask extends TimerTask {
		private Logging console;

		private ConsolePrintTask(Logging printer) {
			if (printer == null) {
				throw new NullPointerException("printer was null");
			}
			this.console = printer;
		}

		/**
		 * Called periodically in its own thread
		 */
		public void run() {
			console.print();
		}
	}
}