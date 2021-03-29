# xmm0-server
Backend for anonymous non-persistent chat application written in Java using Spring Boot.

Simple API and interface, with no user account registration or persistent database storage required.
Chat information can be transmitted encrypted or unencrypted, based upon the protocol of API consumer.

## Designed for anonymous/open communication:
- No account registration.
- end-to-end encryption possible, if implemented in clientside.
- Nothing recorded by server.
  - Chat and room history stored in memory, never on disk.
  - All history disposed of once room expires.
  - Client history disposed of once client disconnects/expires.

## Downsides/Vulnerabilities:
- Heavy use of synchronization, low parallelism.
- Open to bot spam, no captcha support.
- Potentially high memory use, depending on traffic.

## API:

### Creating Room:

POST to ```hostname.com:port/create``` (no body required).
Response:
```json
{
  "id": "the_room_id"
}
```

(By default, chat rooms will be disposed of 1 hour after they have not been accessed)

### Joining Room:

POST to ```hostname.com:port/room/{the_room_id}/join```.
Request:
```json
{
  "name": "desired_nickname"
}
```
Response:
```json
{
  "id": "your_client_id"
}
```

(By default, clients will be disposed of 32 seconds after not pinging the server)

### Leaving Room:

POST to ```hostname.com:port/room/{the_room_id}/leave```.
Request:
```json
{
  "id": "your_client_id"
}
```

### Sending Message:

POST to ```hostname.com:port/room/{the_room_id}/send```.
Request:
```json
{
  "id": "your_client_id",
  "content": "Hello World! This is my message to the chat room."
}
```

### Receiving Messages/Events:

POST to ```hostname.com:port/room/{the_room_id}/read```.
Request:
```json
{
  "id": "your_client_id"
}
```
Response:
```
[
  /* An Array of com.xmm0.EventPackage */
]
```
EventPackages received from the server can be either Messages, Join Events, or Leave Events.
(Array is in chronological order, though all events package a timestamp with them.)

Message EventPackage:
```json
{
  "event": "message",
  "info": {
    "client": "name_of_sender",
    "content": "content_of_their_message",
    "time": "2021-03-29T22:56:31Z"
  }
}
```

Join EventPackage:
```json
{
  "event": "room",
  "info": {
    "client": "name_of_person_joining",
    "action": "join",
    "time": "2021-03-29T22:56:31Z"
  }
}
```

Leave EventPackage:
```json
{
  "event": "room",
  "info": {
    "client": "name_of_person_leaving",
    "action": "leave",
    "time": "2021-03-29T22:56:31Z"
  }
}
```
