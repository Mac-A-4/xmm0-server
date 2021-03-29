package com.xmm0;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.NoSuchElementException;

public final class Server {

	private static Server instance = null;

	public static void setup() {
		instance = new Server();
	}

	public static Server get() {
		return instance;
	}

	private Map<String, Room> roomTable;

	private Server() {
		this.roomTable = new HashMap<String, Room>();
	}

	private synchronized void clean() {
		var res = new HashSet<String>();
		for (var x : this.roomTable.entrySet()) {
			if (x.getValue().isDead()) {
				res.add(x.getKey());
			}
		}
		roomTable.keySet().removeAll(res);
	}

	public synchronized String create() {
		this.clean();
		var id = UUID.randomUUID().toString();
		var room = new Room();
		this.roomTable.put(id, room);
		room.sendHeartbeat();
		return id;
	}

	public synchronized Client join(String room, String name) {
		this.clean();
		if (!this.roomTable.containsKey(room)) {
			throw new NoSuchElementException("Room does not exist.");
		}
		var xroom = this.roomTable.get(room);
		var res = xroom.join(name);
		xroom.sendHeartbeat();
		return res;
	}

	public synchronized void leave(String room, ClientID id) {
		this.clean();
		if (!this.roomTable.containsKey(room)) {
			throw new NoSuchElementException("Room does not exist.");
		}
		var xroom = this.roomTable.get(room);
		xroom.leave(id);
		xroom.sendHeartbeat();
	}

	public synchronized void send(String room, ClientID id, String cipherText) {
		this.clean();
		if (!this.roomTable.containsKey(room)) {
			throw new NoSuchElementException("Room does not exist.");
		}
		var xroom = this.roomTable.get(room);
		xroom.send(id, cipherText);
		xroom.sendHeartbeat();
	}

	public synchronized List<EventPackage> read(String room, ClientID id) {
		this.clean();
		if (!this.roomTable.containsKey(room)) {
			throw new NoSuchElementException("Room does not exist.");
		}
		var xroom = this.roomTable.get(room);
		var res = xroom.read(id);
		xroom.sendHeartbeat();
		return res;
	}

}