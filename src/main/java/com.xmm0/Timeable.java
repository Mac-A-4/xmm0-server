package com.xmm0;

import java.time.Instant;
import java.time.Duration;

public class Timeable {
	
	private Instant startTime;
	private Instant lastHeartbeat;
	private double lifeDuration;

	public Timeable(double lifeDuration) {
		this.lifeDuration = lifeDuration;
		this.startTime = Instant.now();
		this.lastHeartbeat = this.startTime;
	}

	public Instant getStartTime() {
		return this.startTime;
	}

	public void sendHeartbeat() {
		this.lastHeartbeat = Instant.now();
	}

	private double getTimeDifference() {
		var diff = Duration.between(this.lastHeartbeat, Instant.now());
		return (double)(diff.toMillis()) / 1000.0;
	}

	public boolean isAlive() {
		return getTimeDifference() <= this.lifeDuration;
	}

	public boolean isDead() {
		return !this.isAlive();
	}

}