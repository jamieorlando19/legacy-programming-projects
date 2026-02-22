# CISC 475 — Object-oriented Software Engineering

> **README generated:** 2026-02-22. This project was completed in 2002-2003 as part of CISC 475 at the University of Delaware. Developed by **Team Warthog**.

## Course Overview

Software Engineering covers the full software development lifecycle — requirements analysis, design, implementation, testing, and maintenance — applied to a substantial team project. This course produced a complete web application for band/musician profile management.

## What It Does

A full-stack web platform for bands to manage their public presence online. Think of it as an early social networking site for musicians, built before such things were common.

**Features:**

| Feature | Description |
|---------|-------------|
| User registration & login | Account creation, authentication, session management |
| Band profile | Biography, member list, concert/show schedule |
| News management | Post, edit, and delete news items |
| Media gallery | Upload and display photos and MP3 files |
| Site customization | Bands choose layout schemes and color themes for their page |
| Billing/subscription | Account and credit card information management |
| Integrated chat | Real-time multi-user chat (see also the [475 Chat project](../475/)) |

## Architecture

The project follows an early **MVC-style** pattern using JavaBeans and JSP:

```
Browser → JSP pages → Handler classes → JavaBeans (domain model) → JDBC → Database
```

**Domain model (`beans/core/`):**

| Class | Represents |
|-------|-----------|
| `Account.java` | User account and billing info |
| `Band.java` | Band entity (name, URL, website, admin) |
| `BandAdministrator.java` | Admin credentials and permissions |
| `CreditCard.java` | Payment information |
| `WebSite.java` | Site configuration |
| `WebSiteLayout.java` | Layout/theme template |

**Content components (`beans/components/`):**

| Class | Represents |
|-------|-----------|
| `Bio.java` | Band biography text |
| `ImageFile.java` | Uploaded photo |
| `MP3File.java` | Uploaded audio file |
| `NewsItem.java` | News/blog post |
| `ShowDate.java` | Concert date and venue |

**Request handlers (`PROJECT/`):**

| Class | Action |
|-------|--------|
| `ValidateLogin.java` | Authenticate user |
| `ValidateLogout.java` | End session |
| `SignUpHandler.java` | Process registration form |
| `DbServices.java` | Database connection and queries |
| `UploadImage.java` | Handle photo uploads |
| `UploadMp3.java` | Handle audio uploads |
| `UpdateNews.java` / `DeleteNews.java` | Manage news items |
| `EditBio.java` | Update band biography |
| `EditScheme.java` | Change site layout theme |
| `LoadSiteHandler.java` | Load site configuration |
| `TextFormatter.java` | Text processing utility |

## Tech Stack

- **Language:** Java
- **Web layer:** JSP (JavaServer Pages), Java Servlets
- **Database:** SQL via JDBC (prepared statements, transactions)
- **File I/O:** Multipart form upload for images and MP3s
- **Networking:** Socket-based chat system
- **Build era:** Java 1.3–1.4, late 2002/early 2003

## Building & Running

This project requires a **servlet container** (e.g., Apache Tomcat) and a **SQL database** (e.g., MySQL). Exact setup depends on the original deployment configuration which is not fully preserved.

**General steps:**
1. Set up a Tomcat instance (version 4.x was typical for this era)
2. Configure a JDBC data source in `DbServices.java` with your database credentials
3. Compile all Java files and deploy the WAR/classes to Tomcat
4. Access via browser at `http://localhost:8080/`

> A modern equivalent would be built with Spring Boot and a React frontend, but the architecture concepts (MVC, DAO pattern, session management) map directly.

## What Was Done Well

- Substantial real-world application built as a team — covers the full stack from database to UI
- Clean separation of domain model (beans), request handling, and presentation (JSP) is solid early MVC
- JDBC prepared statements protect against SQL injection — good security practice even for the era
- Content upload (images, MP3s) is a non-trivial feature for a course project
- Integrated chat adds real-time capability to what is otherwise a request/response application

## What Could Be Improved

- Credit card data stored in plain JavaBeans with no encryption — a serious security concern by modern standards
- No ORM — all SQL is handwritten; schema changes require updating many query strings
- Session management is likely cookie-based with no CSRF protection
- The chat system is a separate socket server rather than integrated into the web server (no WebSockets in this era)
- No automated tests — typical for the era but a significant maintainability gap
