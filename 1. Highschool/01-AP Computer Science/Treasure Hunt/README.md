# Treasure Hunt

> **README generated:** 2026-02-22. This program was written in 1998-1999 as part of an AP Computer Science high school course.

## What It Does

A dungeon exploration game played on a 10×10 grid. Treasures and hazards are randomly placed at the start of each game. The player begins at the bottom-right corner and navigates using cardinal directions (N/S/E/W). The goal is to collect all 6 treasures (Gold, Diamonds, Silver) before hitting a Trapdoor or Pit. After each game, an ASCII map of the cave is revealed and the program tracks cumulative wins and losses across multiple playthroughs.

- **Treasures (6):** Gold (G), Diamonds (D), Silver (S)
- **Hazards (3):** Trapdoor (T), Pit (P)

## Tech Stack

- **Language:** C++
- **Compiler artifacts present:** `.obj`, `.exe` (Borland/Turbo C++ era toolchain)

## Building & Running

The precompiled `.exe` is a 16-bit DOS executable and will not run natively on 64-bit Windows. Use **DOSBox** to run it:

1. Install [DOSBox](https://www.dosbox.com/)
2. Mount the project folder and run the executable:
   ```
   mount c c:\path\to\Treasure Hunt
   c:
   treasure.exe
   ```

To recompile with a modern toolchain (MinGW g++):
```bash
g++ -o treasure.exe treasure.cpp
```
No external dependencies — standard C++ only. Should compile cleanly with a modern compiler.

## Usage

1. Run the program — the cave is randomly populated and you start at position `[9,9]` (bottom-right)
2. Each turn, your current coordinates are displayed and you choose a direction:
   | Input | Action |
   |-------|--------|
   | `N`   | Move north (decrease row) |
   | `S`   | Move south (increase row) |
   | `E`   | Move east (increase column) |
   | `W`   | Move west (decrease column) |
3. When you enter a room, its contents are revealed:
   - Treasure room: item collected, treasure count updated
   - Hazard room: game over (you lose)
   - Empty room: keep exploring
4. **Win** by collecting all 6 treasures; **lose** by stepping on a Trapdoor or Pit
5. After each game, the full ASCII map is shown and you can play again
6. Running totals of wins and losses are displayed between games

## What Was Done Well

- Post-game map reveal is a satisfying feature that lets the player see where everything was hidden
- Persistent win/loss tracking across multiple games in the same session adds replayability
- Boundary checking prevents the player from walking off the edge of the grid

## What Could Be Improved

- No adjacency hints (e.g., "you sense danger nearby") — the game is entirely random with no skill element beyond luck
- The grid size is set by constants (`MAX_NS`, `MAX_EW`) but requires a recompile to change; a runtime prompt would be more user-friendly
- Random placement doesn't guarantee a winnable game — hazards could theoretically block all paths to remaining treasures
