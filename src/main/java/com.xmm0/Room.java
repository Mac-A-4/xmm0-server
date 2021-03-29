package com.xmm0;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Room extends Timeable {
	
	private static final double LIFETIME = 3600.0;

	private ClientTable clientTable;
	private List<Event> eventQueue;

	public Room() {
		super(LIFETIME);
		this.clientTable = new ClientTable();
		this.eventQueue = new ArrayList<Event>();
	}

	private synchronized void clean() {
		var list = this.clientTable.shouldClean();
		for (var x : list) {
			this.leave(x);
		}
	}

	public synchronized Client join(String name) {
		this.clean();
		if (this.clientTable.contains(name)) {
			throw new IllegalArgumentException("Client already exists.");
		}
		var client = new Client(name);
		this.clientTable.add(client);
		this.eventQueue.add(RoomEvent.join(client));
		client.sendHeartbeat();
		return client;
	}

	public synchronized void leave(ClientID id) {
		this.clean();
		if (!this.clientTable.contains(id)) {
			throw new NoSuchElementException("Client does not exist.");
		}
		var client = this.clientTable.get(id);
		this.clientTable.remove(id);
		this.eventQueue.add(RoomEvent.leave(client));
	}

	public synchronized void send(ClientID id, String cipherText) {
		this.clean();
		if (!this.clientTable.contains(id)) {
			throw new NoSuchElementException("Client does not exist.");
		}
		var client = this.clientTable.get(id);
		this.eventQueue.add(new Message(client, cipherText));
		client.sendHeartbeat();
	}

	public synchronized List<EventPackage> read(ClientID id) {
		this.clean();
		if (!this.clientTable.contains(id)) {
			throw new NoSuchElementException("Client does not exist.");
		}
		var client = this.clientTable.get(id);
		var res = new ArrayList<EventPackage>();
		for (var x : this.eventQueue) {
			if (x.shouldSend(client) && x.shouldRead(client)) {
				res.add(x.getPackage());
			}
		}
		client.sendHeartbeat();
		return res;
	}

}