package com.xmm0;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.NoSuchElementException;
import java.util.List;

@RestController
public class ServerController {
	
	static class CreateResponse {

		public String id;

		public CreateResponse(String id) {
			this.id = id;
		}

	}

	@CrossOrigin
	@PostMapping("/create")
	public @ResponseBody CreateResponse create() {
		return new CreateResponse(Server.get().create());
	}

	static class JoinRequest {

		public String name;

	}

	static class JoinResponse {

		public String id;

		public JoinResponse(Client client) {
			this.id = client.getId().get();
		}

	}

	private boolean isValidName(String name) {
		if (name == null) {
			return false;
		}
		if (name.length() < 4 || name.length() > 32) {
			return false;
		}
		if (!name.chars().allMatch(Character::isLetterOrDigit)) {
			return false;
		}
		return true;
	}

	@CrossOrigin
	@PostMapping("/room/{room}/join")
	public @ResponseBody JoinResponse join(@PathVariable String room, @RequestBody JoinRequest request) {
		if (!this.isValidName(request.name)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Name.");
		}
		try {
			return new JoinResponse(Server.get().join(room, request.name));
		}
		catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	static class LeaveRequest {

		public String id;

	}

	@CrossOrigin
	@PostMapping("/room/{room}/leave")
	public void leave(@PathVariable String room, @RequestBody LeaveRequest request) {
		try {
			var id = new ClientID(request.id);
			Server.get().leave(room, id);
		}
		catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	static class SendRequest {

		public String id;
		public String content;

	}

	@CrossOrigin
	@PostMapping("/room/{room}/send")
	public void send(@PathVariable String room, @RequestBody SendRequest request) {
		try {
			var id = new ClientID(request.id);
			Server.get().send(room, id, request.content);
		}
		catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	static class ReadRequest {

		public String id;

	}

	@CrossOrigin
	@PostMapping("/room/{room}/read")
	public @ResponseBody List<EventPackage> read(@PathVariable String room, @RequestBody ReadRequest request) {
		try {
			var id = new ClientID(request.id);
			return Server.get().read(room, id);
		}
		catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}