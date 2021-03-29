package com.xmm0;

public class Message extends Event {
	
	private Client sender;
	private String cipherText;

	public Message(Client sender, String cipherText) {
		super();
		this.sender = sender;
		this.cipherText = cipherText;
	}

	public Client getSender() {
		return this.sender;
	}

	public String getText() {
		return this.cipherText;
	}

	public class Package extends EventPackage {
	
		public Package(Message message) {
			super("Message.Package");
			this.addInfo("client", message.getSender().getName());
			this.addInfo("value", message.getText());
			this.addInfo("time", message.getStartTime().toString());
		}

	}

	@Override
	public boolean shouldRead(Client client) {
		return client != this.sender;
	}

	@Override
	public EventPackage getPackage() {
		return new Package(this);
	}

}