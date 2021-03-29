package com.xmm0;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ClientTable {
	
	private Map<ClientID, Client> idTable;
	private Map<String, Client> nameTable;

	public ClientTable() {
		this.idTable = new HashMap<ClientID, Client>();
		this.nameTable = new HashMap<String, Client>();
	}

	public synchronized void add(Client client) {
		this.idTable.put(client.getId(), client);
		this.nameTable.put(client.getName(), client);
	}

	public synchronized boolean contains(ClientID id) {
		return this.idTable.containsKey(id);
	}

	public synchronized boolean contains(String name) {
		return this.nameTable.containsKey(name);
	}

	public synchronized Client get(ClientID id) {
		if (!this.contains(id)) {
			throw new NoSuchElementException("Client not found in ClientTable.");
		}
		return this.idTable.get(id);		
	}

	public synchronized Client get(String name) {
		if (!this.contains(name)) {
			throw new NoSuchElementException("Client not found in ClientTable.");
		}
		return this.nameTable.get(name);	
	}

	public synchronized void remove(ClientID id) {
		if (!this.contains(id)) {
			throw new NoSuchElementException("Client not found in ClientTable.");
		}
		var name = this.get(id).getName();
		this.idTable.remove(id);
		this.nameTable.remove(name);
	}

	public synchronized void remove(String name) {
		if (!this.contains(name)) {
			throw new NoSuchElementException("Client not found in ClientTable.");
		}
		var id = this.get(name).getId();
		this.idTable.remove(id);
		this.nameTable.remove(name);
	}

	public synchronized List<ClientID> shouldClean() {
		var output = new ArrayList<ClientID>();
		for (var x : this.idTable.entrySet()) {
			if (x.getValue().isDead()) {
				output.add(x.getKey());
			}
		}
		return output;
	}

}