package com.xmm0;

import java.util.Map;
import java.util.HashMap;

public class EventPackage {
	
	public String event;
	public Map<String, Object> info;

	public EventPackage(String event) {
		this.event = event;
		this.info = new HashMap<String, Object>();
	}

	public void addInfo(String key, Object val) {
		this.info.put(key, val);
	}

}