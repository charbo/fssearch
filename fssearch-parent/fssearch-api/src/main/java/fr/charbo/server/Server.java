package fr.charbo.server;

public interface Server {
	
	void start();
	
	boolean isRunning();
	
	void stop();
	
	Client getClient();

}
