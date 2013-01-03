package ch.rasc.maven.plugin.execwar.run.config;

public class Shutdown {
	private int port;

	private String command;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}