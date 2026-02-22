# CsvDemo

> **README generated:** 2026-02-22. This project was created in July 2014 as a coding exercise during a job search.

## Company

The `AssemblyInfo.cs` metadata references ABB:

```
[assembly: AssemblyCompany("ABB")]
[assembly: AssemblyCopyright("Copyright © ABB 2014")]
```

This is because **ABB** was the employer at the time and the Visual Studio license was registered to them — not because this exercise was made for ABB. The actual company this was submitted to is unknown. ABB is a multinational corporation specializing in robotics, automation, power, and electrical equipment, headquartered in Zurich, Switzerland.

## What It Does

A small Windows Forms demo application that generates a CSV file from an in-memory data table of randomly generated people records. Clicking the single button in the UI produces a 100-row CSV file at a hardcoded output path (`D:\deleteme.csv`) — the "deleteme" name making clear this was intended as throwaway demonstration code.

Each generated record contains:
- First name, last name, full name
- Favorite color
- Date of birth (random age between 18–70)

The CSV utility handles proper formatting: fields are quoted, embedded quotes are escaped, and dates are written in a human-readable format (e.g., `January 2, 1992`).

## Tech Stack

- **Language:** C#
- **Framework:** .NET Framework 4.5
- **UI:** Windows Forms (WinForms)
- **Data:** `System.Data.DataTable` (in-memory, no database)
- **IDE:** Visual Studio 2012 (`.v11.suo` solution user options file present)
- **Build:** MSBuild

No third-party NuGet packages — standard .NET libraries only.

## Project Structure

| File | Purpose |
|------|---------|
| `Program.cs` | Application entry point, bootstraps WinForms |
| `Form1.cs` | Main UI — a single "GenerateCsv" button |
| `Form1.Designer.cs` | Auto-generated designer layout code |
| `CsvUtil.cs` | CSV generation utility — converts a `DataTable` to a properly escaped CSV file |
| `DataUtil.cs` | Test data generator — produces 100 random people records |

## Building & Running

Open `CsvDemo.sln` in **Visual Studio 2012** (or any later version of Visual Studio with .NET Framework 4.5 support) and build the solution.

Alternatively, from the command line with the .NET SDK or MSBuild:
```bash
msbuild CsvDemo.sln /p:Configuration=Debug
```

The compiled executable will be in `bin\Debug\CsvDemo.exe`.

> **Note:** The output path is hardcoded to `D:\deleteme.csv`. Either ensure a `D:\` drive exists before running, or change the path in `Form1.cs` before building.

## Usage

1. Run `CsvDemo.exe`
2. A small window (roughly 180×60 pixels) appears with a single button labeled **GenerateCsv**
3. Click the button
4. A CSV file is written to `D:\deleteme.csv` containing 100 randomly generated person records
5. Open the file in Excel or any text editor to inspect the output

## What Was Done Well

- `CsvUtil.cs` is a clean, reusable utility — properly handles the two tricky parts of CSV generation: quoting fields that contain commas, and escaping embedded quotation marks as double-quotes
- Good separation of concerns: data generation (`DataUtil`), CSV logic (`CsvUtil`), and UI (`Form1`) are kept in separate classes
- Exception handling is present in the CSV writer, which would surface file I/O errors gracefully

## What Could Be Improved

- The output path (`D:\deleteme.csv`) is hardcoded — a file save dialog would make this usable outside of a demo context
- `DataUtil.cs` generates data from small hardcoded arrays (49 first names, 40 last names), making the output feel repetitive across 100 rows
- No unit tests for the CSV escaping logic — the most likely place for subtle bugs
- The UI is minimal to the point of being a stub; a real version would let the user configure record count, choose columns, or preview data before export
