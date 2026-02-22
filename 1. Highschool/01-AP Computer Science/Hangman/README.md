# Hangman

> **README generated:** 2026-02-22. This program was written in 1998-1999 as part of an AP Computer Science high school course.

## What It Does

An interactive console-based Hangman game. A word is randomly selected from an external word list file (`wordfile.dat`). The player guesses one letter at a time. The hangman figure is drawn progressively with each wrong guess using ASCII art loaded from `hangman.dat`. The player loses after 6 incorrect guesses; they win by uncovering all letters before that.

## Tech Stack

- **Language:** C++
- **Libraries:** AP Classes (`apstring`, `apvector`) — the College Board's AP CS standard library from the late 1990s
- **Console:** Borland-era `cprintf` / `gotoxy` for cursor-positioned output
- **Compiler artifacts present:** `.obj`, `.exe` (Borland/Turbo C++ era toolchain)

## Building & Running

The precompiled `.exe` is a 16-bit DOS executable and will not run natively on 64-bit Windows. Use **DOSBox** to run it:

1. Install [DOSBox](https://www.dosbox.com/)
2. Mount the project folder and run the executable:
   ```
   mount c c:\path\to\Hangman
   c:
   hangman.exe
   ```

Recompiling with a modern toolchain is non-trivial due to two dependencies:
- **AP Classes** (`apstring`, `apvector`) — these College Board libraries are no longer officially distributed but can be found archived online, or replaced with `std::string` and `std::vector`
- **Borland console functions** (`gotoxy`, `cprintf`) — Borland-specific; on Windows with MinGW you can substitute `<conio.h>` functions, but behavior may differ

## Usage

1. Ensure `wordfile.dat` and `hangman.dat` are in the same directory as the executable
2. Run the program — a word is chosen at random
3. Enter one letter at a time when prompted
4. The hangman figure draws incrementally with each wrong guess
5. Win by guessing all letters; lose after 6 wrong guesses

## What Was Done Well

- Separates data from code — the word list and hangman drawing are loaded from external files, making them easy to update without recompiling
- Tracks both guessed letters and correctly placed letters as distinct state
- Progressive ASCII art drawing adds meaningful visual feedback to the game loop

## What Could Be Improved

- Uses Borland-specific console functions (`gotoxy`, `cprintf`) which are not portable
- No duplicate guess detection — guessing the same letter twice wastes a turn
- The word list file is required at runtime with no graceful fallback if it's missing
