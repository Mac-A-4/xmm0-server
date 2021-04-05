package com.xmm0;

public class Client extends Timeable {
	
	private static final double LIFETIME = 1200.0;

	private ClientID id;
	private String name;

	public Client(String name, ClientID id) {
		super(LIFETIME);
		this.name = name;
		this.id = id;
	}

	public Client(String name) {
		super(LIFETIME);
		this.name = name;
		this.id = new ClientID();
	}

	public ClientID getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (!(other instanceof Client)) {
			return false;
		}
		var client = (Client)other;
		return this.getId() == client.getId();
	}

}