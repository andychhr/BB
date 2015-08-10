package my.util.thread;

import java.lang.Thread;

public abstract class MyThread extends Thread {
	//
	//public static Integer _threadUnitsSize; // total threads numbers

	
	public abstract  void addNewThread();

	public abstract  void removeCompletedThread();
	
	public abstract void excute();

	@Override
	public void run() {
		super.run();
	}

}
