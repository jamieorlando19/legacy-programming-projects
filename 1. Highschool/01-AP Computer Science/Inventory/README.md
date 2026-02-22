# Inventory

> **README generated:** 2026-02-22. This program was written in 1998-1999 as part of an AP Computer Science high school course.

## What It Does

A menu-driven inventory management system with file persistence. Stores up to 100 items, each with an item number, quantity, and description. The user can initialize a new inventory log, print all items to screen (paginated at 20 per page), adjust item quantities (add or subtract with validation), and quit. Data is saved to and loaded from a binary random-access file (`Inventory.dat`).

## Tech Stack

- **Language:** C++
- **I/O:** `fstream` with random access (`seekg`/`seekp`) for binary file storage
- **Compiler artifacts present:** `.obj`, `.exe` (Borland/Turbo C++ era toolchain)

## Building & Running

The precompiled `.exe` is a 16-bit DOS executable and will not run natively on 64-bit Windows. Use **DOSBox** to run it:

1. Install [DOSBox](https://www.dosbox.com/)
2. Mount the project folder and run the executable:
   ```
   mount c c:\path\to\Inventory
   c:
   Inventory.exe
   ```

To recompile with a modern toolchain (MinGW g++):
```bash
g++ -o Inventory.exe Inventory.cpp
```
No external dependencies — standard C++ file I/O only. Should compile cleanly with a modern compiler.

## Usage

After launching, a menu is presented:

| Key | Action |
|-----|--------|
| `N` | Initialize a **new** inventory log (caution: overwrites existing data) |
| `P` | **Print** all inventory items (paginated, 20 per screen) |
| `C` | **Change** the quantity of an item (enter item number, then add or subtract amount) |
| `Q` | **Quit** the program |

- On first run, choose `N` to create `Inventory.dat` before using other options
- Item descriptions are limited to 15 characters

## What Was Done Well

- Uses random access file I/O rather than reading the entire file into memory — appropriate for a record-based data model
- Paginated output prevents the screen from being flooded when there are many items
- Quantity change validation prevents stock from going negative

## What Could Be Improved

- Description field is capped at 15 characters with no truncation warning to the user
- No ability to add or delete items after the log is initialized — only quantities can be changed
- Initializing a new log destroys existing data without any confirmation prompt
