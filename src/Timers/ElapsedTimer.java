package Timers;

import idkMate.Backgammon;
import javafx.beans.property.SimpleStringProperty;
import idkMate.Game;
import java.text.SimpleDateFormat;
import idkMate.Player;
import java.util.Date;


public class ElapsedTimer extends Thread {

	private SimpleDateFormat formatter = new SimpleDateFormat("mm:ss:S");
	private SimpleStringProperty minutes;
	private SimpleStringProperty seconds;
	private SimpleStringProperty milliseconds;
		
	
	public String[] time_parts;
	public Thread timerThread = null;
	public SimpleStringProperty s_time;
	public long elapsed;
	
	public ElapsedTimer(int duration) {
		
		
		this.minutes = new SimpleStringProperty("00");
		this. seconds = new SimpleStringProperty("00");
		this.milliseconds = new SimpleStringProperty("00");
		this.s_time = new SimpleStringProperty("" + this.minutes.get() + ":" + this.seconds.get() + ":" + this.milliseconds.get() + "");
		
		this.startTimer(duration*60000);
	}

	
	
	public void startTimer(long time) {
	   

	    this.timerThread = new Thread(this);
	    this.timerThread.setPriority(Thread.MIN_PRIORITY);
	    this.elapsed = time;
	    this.timerThread.start();
	    
	}
	
	

	public void setTimerTime(long time) {
	    
		    this.elapsed = time;
		    this.time_parts = this.formatter.format(new Date(time)).split(":");
		    
		   // System.out.println(this.formatter.format(new Date(time)));
		    
		   this.minutes.set(this.time_parts[0]);
		   this.seconds.set(this.time_parts[1]);

		    if (this.time_parts[2].length() == 1) {
		        this.time_parts[2] = "0" + this.time_parts[2];
		    }
		    this.milliseconds.set(this.time_parts[2].substring(0, 2));

		   
		    this.s_time.set(this.minutes.get() + ":" + this.seconds.get() + ":" + this.milliseconds.get());
	}
	
	@Override
	public void run() 
	{
	    try {
	        while (!this.timerThread.isInterrupted()) {
	        	this.setTimerTime(elapsed);
	            sleep(10);
	            this.elapsed = this.elapsed - 10;
	        }
	    } catch (Exception e) {
	    }
	
	}

	public void stopTimer() {
	   
		if (this.timerThread != null) {
	    	this.timerThread.interrupt();
	    }
		
	    //this.elapsed = time;
	    
	    //this.setTimerTime(time);
	}


}