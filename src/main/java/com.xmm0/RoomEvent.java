package com.xmm0;

public class RoomEvent extends Event {
	
	private Client client;
	private String action;

	public RoomEvent(Client client, String action) {
		this.client = client;
		this.action = action;
	}

	public static RoomEvent join(Client client) {
		return new RoomEvent(client, "Join");
	}

	public static RoomEvent leave(Client client) {
		return new RoomEvent(client, "Leave");
	}

	public Client getClient() {
		return this.client;
	}

	public String getAction() {
		return this.action;
	}

	public class Package extends EventPackage {
	
		public Package(RoomEvent event) {
			super("RoomEvent.Package");
			this.addInfo("client", event.getClient().getName());
			this.addInfo("action", event.getAction());
			this.addInfo("time", event.getStartTime().toString());
		}

	}

	@Override
	public boolean shouldRead(Client client) {
		return client != this.client;
	}

	@Override
	public EventPackage getPackage() {
		return new Package(this);
	}

}