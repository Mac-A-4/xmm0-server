package com.xmm0;

import java.util.UUID;

public class ClientID {
	
	private String value;

	public ClientID() {
		this.value = UUID.randomUUID().toString();
	}

	public ClientID(String value) {
		this.value = value;
	}

	public String get() {
		return this.value;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (!(other instanceof ClientID)) {
			return false;
		}
		var id = (ClientID)other;
		return this.get().equals(id.get());
	}

	@Override
	public String toString() {
		return this.get();
	}

	@Override
	public int hashCode() {
		return this.get().hashCode();
	}

}