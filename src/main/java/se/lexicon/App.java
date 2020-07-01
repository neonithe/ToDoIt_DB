package se.lexicon;

import se.lexicon.data.*;

import java.sql.SQLException;
import java.util.Scanner;

public class App 
{
    private static People runPerson = new PeopleRep();
    private static ToDoItems runTodo = new ToDoItemsRep();


    public static void main( String[] args ) throws SQLException {

        /** Get (test) connection to database START**/
        Functions.connection();
        /** Get (test) connection to database END**/

        boolean runUntil = true;
        String selection ="";

        do{
            System.out.println("--------------------------- Menu ---------------------------");
            System.out.println("1: Create Person             |  6: Change todo to done or not\n"+
                               "2: Create Todo               |  7: Show all Persons\n"+
                               "3: Add assignee              |  8: Show all todos\n"+
                               "4: Change assignee           |  9: Find person or todo \n"+
                               "5: Update (todos or persons) |  0: Delete (todos or persons)\n"+
                               "Q: quit");
            System.out.print("Selection:>");
            selection = input();
            switch (selection){
                case "1":
                    Functions.createPerson();
                    break;
                case "2":
                    Functions.createTodo();
                    break;
                case "3":
                    Functions.addPersonToDo();
                    break;
                case "4":
                    Functions.changePersonId();
                    break;
                case "5":
                    Functions.updateToDoPerson();
                    break;
                case "6":
                    Functions.setToDoneOrNot();
                    break;
                case "7":
                    runPerson.findAll().forEach(System.out::println);
                    break;
                case "8":
                    runTodo.findAll().forEach(System.out::println);
                    break;
                case "9":
                    Functions.findPersonToDo();
                    break;
                case "0":
                    Functions.deleteToDoPerson();
                    break;
                case "Q":
                    System.out.println("Application Terminated");
                    runUntil = false;
                    break;

                default:
                    System.out.println("Unknown selection, please try again");
            }

        }while(runUntil);

    }

    public static String input(){
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

}
