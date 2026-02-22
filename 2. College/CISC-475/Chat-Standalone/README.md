# Chat Application — Team Warthog

> **README generated:** 2026-02-22. This project was completed in 2001-2003 as part of coursework at the University of Delaware. Based on the "Team Warthog" branding shared with the CISC 475 project, this is likely a standalone chat deliverable or an early prototype developed during CISC 475 — Object-oriented Software Engineering.

## What It Does

A multi-user real-time chat system with a client-server architecture. Multiple clients connect to a central server, authenticate with a username, and exchange messages in real time. Users are organized by a "band" or group association.

**Components:**

| File | Role |
|------|------|
| `ChatServer.java` | Central server — accepts connections, routes messages |
| `ChatClient.java` | Client GUI applet — connects to server, sends/receives messages |
| `ChatClientListener.java` | Background thread listening for incoming messages on the client |
| `ChatServerUsers.java` | Server-side user list management |
| `ChatUserConnection.java` | Per-user connection handler (one thread per client) |
| `ChatWindow.java` | Swing GUI window for the chat interface |
| `ClienttoServer.java` | Message object sent from client to server |
| `ServertoClient.java` | Message object sent from server to client |
| `ChatServerIP.java` | Server IP/port configuration |
| `JTextUtil.java` | Text utility helpers |

## Tech Stack

- **Language:** Java
- **Networking:** `java.net.Socket` / `ServerSocket`
- **Concurrency:** One `Thread` per connected client
- **Serialization:** `ObjectInputStream` / `ObjectOutputStream` for passing message objects over the socket
- **GUI:** Java Swing (`JFrame`, `JTextArea`, `JList`, `DefaultListModel`)

## Building & Running

Compile all source files together:
```bash
javac *.java
```

**Start the server first:**
```bash
java ChatServer
```

**Then launch one or more clients** (in separate terminals or machines):
```bash
java ChatClient
```

> The server IP/port is configured in `ChatServerIP.java`. If running everything locally, ensure the client points to `localhost` and the port matches the server.

## Usage

1. Start `ChatServer` — it listens for incoming connections
2. Launch `ChatClient` — a GUI window opens
3. Enter your **username** and **band/group name** when prompted
4. Type messages in the input field and press Enter to send
5. All connected users see messages in real time
6. The user list panel updates as people join and leave

## What Was Done Well

- Proper multi-threaded server design — each client gets its own thread, so one slow client doesn't block others
- Message objects are serialized and passed as structured data rather than raw strings, making the protocol extensible
- The GUI is decoupled from the networking logic via the listener thread

## What Could Be Improved

- No authentication beyond a username — anyone can claim any name
- No persistent message history — joining late means you missed everything said before
- The chat is a single global room per server; no private messaging or multiple channels
- Java Applet hosting for the client is now obsolete; a modern version would be a Swing app or web client
