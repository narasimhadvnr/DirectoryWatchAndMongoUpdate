package com.indix.thread;

public class LoadThread extends Thread {
		 private String name;
		 private int currentIndex;
		  public LoadThread (String fileName,int index) { 
		    super();
		    this.name = fileName;
		    this.currentIndex = index;
		  }

		  public void run() { 
			  
		  } 
}
