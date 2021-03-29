package com.xmm0;

import java.util.Set;
import java.util.HashSet;

public class Sendable extends Timeable {
	
	private Set<Client> history;

	public Sendable(double lifeDuration) {
		super(lifeDuration);
		this.history = new HashSet<Client>();
	}

	public Sendable() {
		super(Double.MAX_VALUE);
		this.history = new HashSet<Client>();
	}

	public boolean shouldSend(Client client) {
		return !history.contains(client);
	}

	public void send(Client client) {
		history.add(client);
	}

}