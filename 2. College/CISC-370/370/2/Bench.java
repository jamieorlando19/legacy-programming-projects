/** Test Bench program for the classes:
    Employee, Manager, Engineer, Senior Engineer, Executive and Secretary
    @author Jamie Orlando
    @version 1.0
    @since 1.0
    @see <a href="http://udel.edu/~orlandoj">Jamie's Page</a>
*/
public class Bench
{  
   public static void main(String[] args)
   {  
      //Employee Test
      System.out.println("***Employee Test***");
      Employee employee = new Employee("Joe Average", 30000, 1998, 5, 6);      
      System.out.println("Name: " + employee.getName());
      System.out.println("Salary: " + employee.getSalary());
      employee.raiseSalary(5.7);
      System.out.println("Salary w/ Raise: " + employee.getSalary());
      System.out.println("Hire Day: " + employee.getHireDay());
      
      //Manager Test
      System.out.println("\n***Manager Test***");
      Manager manager = new Manager("Joe Hot-Shot", 70000, 1985, 4, 13);
      System.out.println("Name: " + manager.getName());
      System.out.println("Salary: " + manager.getSalary());
      manager.raiseSalary(7);
      System.out.println("Salary w/ Raise: " + manager.getSalary());
      manager.setBonus(3000);
      System.out.println("Salary w/ Bonus: " + manager.getSalary());
      System.out.println("Hire Day: " + manager.getHireDay());
      
      //Engineer Test
      System.out.println("\n***Engineer Test***");
      Engineer engineer = new Engineer("Joe Geek", 60000, 1988, 1, 23);
      System.out.println("Name: " + engineer.getName());
      System.out.println("Salary: " + engineer.getSalary());
      engineer.raiseSalary(3);
      System.out.println("Salary w/ Raise: " + engineer.getSalary());
      System.out.println("Hire Day: " + engineer.getHireDay());

      //Senior Engineer
      System.out.println("\n***Senior Engineer Test***");
      SrEngineer srengineer = new SrEngineer("Joe Mondo-Geekus", 100000, 1980, 2, 11);
      System.out.println("Name: " + srengineer.getName());
      System.out.println("Salary: " + srengineer.getSalary());
      srengineer.raiseSalary(9.9);
      System.out.println("Salary w/ Raise: " + srengineer.getSalary());      
      srengineer.setBonus(5000);
      System.out.println("Salary w/ Bonus: " + srengineer.getSalary());
      System.out.println("Hire Day: " + srengineer.getHireDay());

      //Executive
      System.out.println("\n***Executive***");
      Executive executive = new Executive("Joe Important", 150000, 1981, 10, 10, "Pompous Inc.", 30);
      System.out.println("Name: " + executive.getName());
      System.out.println("Salary: " + executive.getSalary());
      executive.raiseSalary(20);
      System.out.println("Salary w/ Raise: " + executive.getSalary());
      executive.setBonus(10000);
      System.out.println("Salary w/ Bonus: " + executive.getSalary());
      System.out.println("Health Plan: " + executive.getHealthPlan());
      System.out.println("Stock Options: " + executive.getStockOptions() + "%");
      System.out.println("Hire Day: " + executive.getHireDay());

      //Secretary
      System.out.println("\n***Secretary***");
      Secretary secretary = new Secretary("Joe Busy", 30000, 1991, 5, 2, "Mediocre Health Inc.");
      System.out.println("Name: " + secretary.getName());
      System.out.println("Salary: " + secretary.getSalary());
      secretary.raiseSalary(3);
      System.out.println("Salary w/ Raise: " + secretary.getSalary());
      System.out.println("Health Plan: " + secretary.getHealthPlan());
      System.out.println("Hire Day: " + secretary.getHireDay());      
   }
}
