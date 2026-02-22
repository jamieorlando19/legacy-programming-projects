using System;
using System.Data;
using System.IO;
using System.Text;

// A class to generate a CSV file
// Written by: Jamie Orlando - jamie@jamieorlando.com
public class CsvUtil
{
    /// <summary>
    /// Write a CSV file using a DataTable as the data source
    /// </summary>
    /// <param name="table">A DataTable from which the CSV will the generated</param>
    /// <param name="outputFile">The path to which the CSV will be written</param>
    public static void WriteCsv (DataTable table, string outputFile)
    {
        try
        {
            File.WriteAllText(outputFile, convertDataTableToString(DataUtil.GetTable()));
        }
        catch (Exception ex)
        {
            Console.WriteLine(string.Format("An exception has occured: {0}", ex));
        }
    }

    #region Helper Methods
    /// <summary>
    /// Takes in a DataTable and returns a string in .csv format
    /// </summary>
    /// <param name="table">A DataTable from which the string will the generated</param>
    private static string convertDataTableToString (DataTable table)
    {
        StringBuilder stringBuilder = new StringBuilder();

        // Add columns
        foreach (DataColumn column in table.Columns)
        {
            stringBuilder.Append(string.Format("\"{0}\",", column.ColumnName.Replace("\"", "\"\"")));
        }

        stringBuilder.Append(Environment.NewLine);

        // Add rows
        foreach (DataRow row in table.Rows)
        {
            for (int i = 0; i < table.Columns.Count; i++)
            {
                // Enclose formatted cell in double quotes and add a comma
                stringBuilder.Append(string.Format("\"{0}\",", formatCell(row[i].ToString(), table.Columns[i].DataType)));
            }

            stringBuilder.Append(Environment.NewLine);
        }

        return stringBuilder.ToString();
    }

    /// <summary>
    /// Takes a row cell and if it is a date, the date gets formatted.  Also replaces all quotes with 2 quotes
    /// </summary>
    /// <param name="cell">A string representing the data in the cell</param>
    /// <param name="columnType">The type of the column of the cell</param>
    private static string formatCell(string cell, Type columnType)
    {
        // If column is DateTime, format ex: January 2, 1992	
        if (columnType == typeof(DateTime))
        {
            cell = DateTime.Parse(cell).ToString("MMMM d, yyyy");
        }

        // Return cell data (replace all double quotes with two double quotes)
        return cell.Replace("\"", "\"\"");
    }
    #endregion
}