using System;
using System.Collections.Generic;
using System.Data;

public static class DataUtil
{

    static Random rnd = new Random();

    static string getRandomString(string[] values)
    {
        int i = rnd.Next(0, values.Length);
        return values[i];
    }

    public static DataTable GetTable()
    {
        DataTable table = new DataTable();

        table.Columns.Add("FirstName", typeof(string));
        table.Columns.Add("LastName", typeof(string));
        table.Columns.Add("FullName", typeof(string));
        table.Columns.Add("FavoriteColors", typeof(string));
        table.Columns.Add("BirthDate", typeof(DateTime));

        for (int i = 0; i < 100; i++)
        {
            string firstName = getRandomString(firstNames);
            string lastName = getRandomString(lastNames);
            string fullName = firstName + " " + lastName;

            List<string> favoriteColors = new List<string>();
            int c = rnd.Next(1, 5);

            DateTime birthDate = DateTime.Now.AddDays(rnd.Next(365 * 18, 368 * 70) * -1).Date;

            while (favoriteColors.Count < c)
            {
                string color = getRandomString(colors);
                if (!favoriteColors.Contains(color))
                    favoriteColors.Add(color);
            }

            table.Rows.Add(
                firstName,
                lastName,
                fullName,
                string.Join(",", favoriteColors),
                birthDate
            );

        }

        return table;
    }

    static string[] colors = {
          	"red",
          	"orange",
          	"yellow",
          	"green",
          	"blue",
          	"indigo",
          	"violet",
          	"\"blue-green\"",
          	"\"orange-red\"" 	
   	};

    static string[] firstNames = {
          	"Aiden",
          	"Jackson",
          	"Ethan",
          	"Liam",
          	"Mason",
          	"Noah",
          	"Lucas",
          	"Jacob",
          	"Jayden",
          	"Jack",
          	"Logan",
          	"Ryan",
          	"Caleb",
          	"Benjamin",
          	"William",
          	"Michael",
          	"Alexander",
          	"Elijah",
          	"Matthew",
          	"Dylan",
          	"James",
          	"Owen",
          	"Connor",
          	"Brayden",
          	"Carter",
          	"Landon",
          	"Joshua",
          	"Luke",
          	"Daniel",
          	"Gabriel",
          	"Nicholas",
          	"Nathan",
          	"Oliver",
          	"Henry",
          	"Andrew",
          	"Gavin",
          	"Cameron",
          	"Eli",
          	"Max",
          	"Isaac",
          	"Evan",
          	"Samuel",
          	"Grayson",
          	"Tyler",
          	"Zachary",
          	"Wyatt",
          	"Joseph",
          	"Charlie",
          	"Hunter"
   	};

    static string[] lastNames = {
          	"Smith",
          	"Johnson",
          	"Williams",
          	"Brown",
          	"Jones",
            "Miller",
          	"Davis",
          	"Wilson",
          	"Anderson",
          	"Taylor",
          	"Thomas",
          	"Moore",
          	"Martin",
          	"Jackson",
          	"Thompson",
          	"White",
          	"Lee",
          	"Harris",
          	"Clark",
          	"Lewis",
          	"Robinson",
          	"Walker",
          	"Hall",
          	"Young",
          	"Allen",
          	"Wright",
          	"King",
          	"Scott",
          	"Green",
          	"Baker",
          	"Adams",
          	"Nelson",
          	"Hill",
          	"Campbell",
          	"Mitchell",
          	"Roberts",
          	"Carter",
          	"Phillips",
          	"Evans",
          	"Turner"
   	};

}