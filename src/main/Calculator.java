package main;

public class Calculator {

	long startTime;
	long endTime;
	long runTime;
	
	public Calculator() {
		// TODO Auto-generated constructor stub
		startTime = 0;
		endTime = 0;
		runTime = 0;
	}
	
	public void start()
	{
		startTime = System.currentTimeMillis();
	}
	public void end()
	{
		endTime = System.currentTimeMillis();
		runTime = (endTime - startTime);
	}

	public boolean saveTypingRecord(String string, int levelNumber, double correct, double notCorrect, double tasu) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
