# CISC 220 — Data Structures

> **README generated:** 2026-02-22. This project was completed in 2000-2001 as part of CISC 220 at the University of Delaware.

## Course Overview

Data structures course covering abstract data types, recursion, hash tables, trees, and algorithm analysis. The primary artifact from this course is a sophisticated custom hash table implementation in C++.

---

## Project — Hash Table with SQL-like Query Interface (`shaktable.h`)

### What It Does

A custom in-memory database table implementation. Supports multiple field types (integer, string, float) and provides SQL-inspired query operations:

- **`insert()`** — Add a new record
- **`print_all()`** — Display all records
- **`print_where()`** — Filter and display records matching a condition
- **`join_all()`** — Join two tables on a matching field
- **`order_by()`** — Sort and display records by a given field

Collision handling uses **chained linked lists**. A **binary search tree** provides secondary indexing for efficient ordered access.

### Tech Stack

- **Language:** C++
- **Data structures:** Hash table with chained collision lists, binary search tree index
- **Libraries:** STL `list`
- **Custom types:** `Cstring` struct for fixed-length string fields, `FieldEntry` struct with forward/backward pointers

### Building & Running

This is a header-only class definition. A `main.cpp` driver is required to instantiate and use the table. Include the header and compile:

```bash
g++ -o shaklist main.cpp
./shaklist
```

### Usage

Instantiate a `shaktable`, define its fields, then call the query methods:

```cpp
shaktable t;
t.insert(...);
t.print_all();
t.print_where("fieldname", value);
t.join_all(otherTable, "fieldname");
t.order_by("fieldname");
```

### What Was Done Well

- Building a hash table from scratch with chained collision resolution is a genuine implementation of a core CS data structure
- Adding a secondary BST index demonstrates understanding that different data structures serve different access patterns
- The SQL-inspired interface (`print_where`, `join_all`, `order_by`) shows design thinking well beyond a basic assignment
- Supporting multiple field types in a single generic structure is non-trivial

### What Could Be Improved

- The `Cstring` struct reimplements what `std::string` already provides; was likely a constraint of the era or course
- No dynamic resizing of the hash table — performance degrades as load factor grows
- `join_all` is likely O(n²) with no indexing optimization on the join key
