package com.xmm0;

public abstract class Event extends Sendable {
	
	public Event() {
		super();
	}


	public abstract boolean shouldRead(Client client);

	public abstract EventPackage getPackage();

}