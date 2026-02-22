# CISC 479 — Topics in Web Application Development

> **README generated:** 2026-02-22. This project was completed in 2002-2003 as part of CISC 479 at the University of Delaware.

## Course Overview

CISC 479 is a variable-topics course at the University of Delaware. Based on the content of this folder, the section taken focused on advanced web application development, extending the band/musician platform begun in CISC 475 with additional features and a more mature data access layer.

## What It Does

A set of JavaBean and DAO (Data Access Object) components that extend the band platform from CISC 475. This folder appears to represent continued development of that system with a cleaner layered architecture.

**Components:**

| Class | What It Models |
|-------|----------------|
| `ShowBean.java` | A single concert/show — username, band name, date, venue, city, state, setlist, band members, journal notes |
| `ShowDataBean.java` | DAO for loading and saving `ShowBean` data to the database |
| `BandsMusiciansBean.java` | The relationship between a band and its individual musicians |
| `BandsMusiciansDataBean.java` | DAO for band/musician relationship persistence |
| `MuscLogUsersBean.java` | Tracks user login activity and session logging |
| `MuscLogUsersDataBean.java` | DAO for user log persistence |

## Tech Stack

- **Language:** Java
- **Pattern:** JavaBeans + Data Access Objects (DAO) — a precursor to modern repository/service patterns
- **Persistence:** JDBC (SQL database)
- **Web layer:** JSP (JavaServer Pages)
- **Build era:** Java 1.3–1.4

## Building & Running

These classes are components of a larger web application and require the servlet container and database setup from CISC 475 to function. See the [CISC-475 README](../CISC-475/README.md) for the deployment context.

To compile the beans standalone:
```bash
javac *.java
```
A servlet container (Tomcat) and configured JDBC datasource are required to run them in context.

## What Was Done Well

- The DAO pattern cleanly separates business logic (bean properties) from database access — each domain object has a dedicated data bean, making the persistence layer easy to swap out
- `ShowBean` captures a rich set of concert details (setlist, journal, members) showing thoughtful domain modeling
- The pattern used here (bean + data bean) directly maps to the modern Service + Repository pattern

## What Could Be Improved

- Getter/setter JavaBeans are verbose boilerplate by modern standards — a modern equivalent would use records or Lombok annotations
- No connection pooling is apparent — each DAO likely opens a new JDBC connection per operation
- The `MuscLogUsersBean` stores login activity but the security model (password hashing, session tokens) is unclear from the bean definitions alone
