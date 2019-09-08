package logic.hanoi;

import java.text.SimpleDateFormat;

public class Timer {
	private static final long GAME_TIMER_EASY = (long) (90 * Math.pow(10, 3));
	private static final long GAME_TIMER_MIDDLE =  (long) (60 * Math.pow(10, 3));
	private static final long GAME_TIMER_HARD =  (long) (30 * Math.pow(10, 3));
	private static final long GAME_TIMER_IMPOSSIBLE =  (long) (15 * Math.pow(10, 3));;
	private static final long GAME_TIME_ALERT = (long) (10 * Math.pow(10, 3));

	private long sessionStartTime;
	private long gameTimer;
	private long timeCounter;
	

	
	public Timer() {
		this.sessionStartTime = System.currentTimeMillis();
		this.gameTimer = GAME_TIMER_MIDDLE;
		this.timeCounter = gameTimer;
	}
	
	public Timer(long gameTimer) {
		this.sessionStartTime = System.currentTimeMillis();
		this.gameTimer = gameTimer;
		this.timeCounter = gameTimer;
	}
	
	public void setGameTimer(long gameTimer) {
		this.gameTimer = gameTimer;
	}
	
	public long getGameTimer() {
		return this.gameTimer;
	}

	public void setTimer() {
		long currentSessionTime = System.currentTimeMillis();
		long diff = currentSessionTime - sessionStartTime;
		timeCounter = gameTimer-diff;
//		System.out.println(sessionStartTime + "     " + currentSessionTime + "      " + diff + "        " + timeCounter);
	}
	
	public void setTimeCounter(long time) {
		this.timeCounter = time;
	}
	public String getTimerAsString() {
		
		String formattedDate = new SimpleDateFormat("mm:ss.SSS").format(timeCounter);		
		return formattedDate;
	}

	public long getTime() {
		return this.timeCounter;
	}
	
	public static long getGameTimerEasy() {
		return GAME_TIMER_EASY;
	}
	public static long getGameTimerMiddle() {
		return GAME_TIMER_MIDDLE;
	}
	public static long getGameTimerHard() {
		return GAME_TIMER_HARD;
	}
	public static long getGameTimerImpossible() {
		return GAME_TIMER_IMPOSSIBLE;
	}
	
	public static long getGameTimeAlert() {
		return GAME_TIME_ALERT;
	}
	
	
}
