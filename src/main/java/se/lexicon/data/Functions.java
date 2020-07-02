package se.lexicon.data;

import se.lexicon.entity.DbSource;
import se.lexicon.model.Person;
import se.lexicon.model.ToDo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Functions {

    private static People runPerson = new PeopleRep();
    private static ToDoItems runTodo = new ToDoItemsRep();
    private static boolean runUntil = true;
    private static String selection ="";

    /** Get (test) connection to database **/
    public static void connection() throws SQLException {
        System.out.println("Database connection established");
        DbSource.getConnection();
    }

    /** Get Input (String) **/
    public static String input(){
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    /** Menu functions **/
    public static ToDo getToDo(int id){
        return runTodo.findById(id);
    }
    public static void setToDoneOrNot(){

        System.out.println("Change status-------------------");
        System.out.print("Enter todo ID:");
            int id = Integer.parseInt(input());
        System.out.print("Enter status (true / false):");
            String status = input();

            boolean done = Boolean.parseBoolean(status);
            getToDo(id).setDone(done);
        System.out.println("Status changed");
    }
    public static void addPersonToDo(){

        System.out.print("Get Todo, enter ID:");
            int id = Integer.parseInt(input());
        System.out.print("Set assignee, enter ID:");
            int personId = Integer.parseInt(input());

        if(getToDo(id) != null){
            getToDo(id).setAssigneeId(personId);
            System.out.println("Person ID: "+personId+" added to todo ID: "+id);
        } else {
            System.out.println("Cannot find that todo ID: "+id);
        }
    }
    public static void changePersonId(){
        java.lang.Integer personIn;

        System.out.print("Get Todo, enter ID:");
            int id = Integer.parseInt(input());
        System.out.println("Set assignee [ Leave empty press enter = no assignee ] [ enter number person ID ]");
        System.out.print("Set assignee :");
            String person = input();

        if(person.isEmpty()){
            personIn = null;
        } else {
            java.lang.Integer i = Integer.parseInt(person);
            personIn = i;
        }

        ToDo todoId = runTodo.findById(id);
        todoId.setAssigneeId(personIn);
        runTodo.upDate(todoId);
        System.out.println("Person changed");
    }
    public static void deleteToDoPerson(){

        do{
            System.out.println("Delete --------------------\n"+
                    "1: Todo\n"+
                    "2: Person\n"+
                    "Q: quit");
            System.out.print("Selection:>");
            selection = input();

            switch (selection.toLowerCase()){
                case "1":
                    System.out.print("Enter todo ID:");
                    int todoId = Integer.parseInt(input());
                    runTodo.deleteById(todoId);
                    System.out.println("Todo ID "+todoId+" deleted");
                    break;
                case "2":
                    System.out.print("Enter person ID:");
                    int personId = Integer.parseInt(input());
                    runPerson.deleteById(personId);
                    System.out.println("Person ID "+personId+" deleted");
                    break;
                case "q":
                    runUntil = false;
                    break;
                case "t":
                    runTodo.findAll().forEach(System.out::println);
                    break;
                case "p":
                    runPerson.findAll().forEach(System.out::println);
                    break;
                case "id":
                    findOnOnlyId();
                    break;
                default:
                    System.out.println("Unknown selection");
                    break;
            }
        }while(runUntil);
    }

    /** Menu functions - Update person and todo **/
    public static void updateToDo(int findId, String title, String desc, LocalDate date, boolean done, java.lang.Integer id)  {

        ToDo todoId = runTodo.findById(findId);

        todoId.setTitle(title);
        todoId.setDescription(desc);
        todoId.setDeadLine(date);
        todoId.setDone(done);
        todoId.setAssigneeId(id);

        runTodo.upDate(todoId);
        System.out.println("ToDo updated");
    }
    public static void update(String fName, String lName) {
        Person person = runPerson.findById(4);

        person.setFirstName(fName);
        person.setLastName(lName);

        System.out.println(runPerson.upDate(person));
        System.out.println("Person updated");
    }
    public static void updateToDoPerson() {
        java.lang.Integer personIn;

        do{
            System.out.println("------------- Update ------------\n"+
                    "1: Update todo\n"+
                    "2: Update person\n"+
                    "Q: quit");
            System.out.print("Selection:>");
            selection = input();

            switch (selection.toLowerCase()){
                case "1":
                    System.out.println("Enter todo ID to change:");
                    int findId = Integer.parseInt(input());
                    ToDo todo = runTodo.findById(findId);
                    System.out.println("---------------------------");
                    System.out.println("Current title: "+todo.getTitle());
                    System.out.print("Enter new title:");
                    String title = input();
                    System.out.println("Current description: "+todo.getDescription());
                    System.out.print("Enter new description:");
                    String desc = input();
                    System.out.println("Current deadline: "+todo.getDeadLine());
                    System.out.print("Enter new deadline (0000-00-00):");
                    String date = input();
                    System.out.println("Current status: "+todo.isDone());
                    System.out.print("Enter new status:");
                    String doneIn = input();
                    System.out.println("Current assignee: "+todo.getAssigneeId());
                    System.out.print("Enter new assignee:");
                    String person = input();

                    if(person.isEmpty()){
                        personIn = null;
                    } else {
                        java.lang.Integer i = Integer.parseInt(person);
                        personIn = i;
                    }
                    boolean done = Boolean.parseBoolean(doneIn);
                    LocalDate newDate = LocalDate.parse(date);
                    updateToDo(findId,title,desc,newDate,done,personIn);

                    System.out.println("Todo changed in database");
                    break;
                case "2":
                    System.out.println("Enter person ID:");
                    int findId2 = Integer.parseInt(input());
                    Person person2 = runPerson.findById(findId2);
                    System.out.println("---------------------------");
                    System.out.println("Current FirstName: "+person2.getFirstName());
                    System.out.print("Enter new FirstName:");
                    String fName = input();
                    System.out.println("Current LastName: "+person2.getLastName());
                    System.out.print("Enter last name:");
                    String lName = input();

                    update(fName,lName);
                    System.out.println("Person changed in database");
                    break;
                case "q":
                    runUntil = false;
                    break;
                case "t":
                    runTodo.findAll().forEach(System.out::println);
                    break;
                case "p":
                    runPerson.findAll().forEach(System.out::println);
                    break;
                case "id":
                    findOnOnlyId();
                    break;
                default:
                    System.out.println("Not valid selection");
                    break;
            }

        }while(runUntil);
    }

    /** Menu functions - Create person and todo **/
    public static void createPerson() {
        System.out.print("Enter first name:");
        String fName = input();
        System.out.print("Enter last name:");
        String lName = input();

        Person person = new Person(fName,lName);
        runPerson.create(person);
        System.out.println("Person created to database");
    }
    public static void createTodo() {
        java.lang.Integer personIn;

        System.out.print("Enter title:");
        String title = input();
        System.out.print("Enter description:");
        String desc = input();
        System.out.print("Enter date (0000-00-00):");
        String date = input();
        System.out.print("Enter done or not (true or false):");
        String doneIn = input();
        System.out.print("Enter assignee (ID or leave empty):");
        String person = input();

        if(person.isEmpty()){
            personIn = null;
        } else {
            java.lang.Integer i = Integer.parseInt(person);
            personIn = i;
        }

        // Convert from string to boolean, integer and localdate
        boolean done = Boolean.parseBoolean(doneIn);
        LocalDate newDate = LocalDate.parse(date);

        ToDo todo = new ToDo(title, desc, newDate, done, personIn);
        runTodo.create(todo);
        System.out.println("Todo created to database");
    }

    /** Menu functions - Find persons or todos **/
    public static void findPersonToDo() {
        do {
            System.out.println("FIND ------------------------------[T = All todo][P = All person]\n" +
                    "1: Todo: Find by ID             | 5: Person: Find by ID\n" +
                    "2: Todo: Find by done status    | 6: Person: Find by Firstname\n" +
                    "3: Todo: Find by assignee       | 7: Person: Find by Lastname\n" +
                    "4: Todo: Find all unassigned    | Q: Quit");
            System.out.print("Selection:>");
            selection = input();

            switch (selection.toLowerCase()){
                case "1":
                    System.out.print("Enter todo ID:");
                    int sel1 = Integer.parseInt(input());
                    findByIdTodo(sel1);
                    break;
                case "2":
                    System.out.print("Enter status [ True or False ]:");
                    boolean sel2 = Boolean.parseBoolean(input());
                    runTodo.findByDoneStatus(sel2).forEach(System.out::println);
                    break;
                case "3":
                    System.out.print("Enter person ID:");
                    int sel3 = Integer.parseInt(input());
                    runTodo.findByAssignee(findById(sel3)).forEach(System.out::println);
                    break;
                case "4":
                    runTodo.findByUnassignedToDoItems().forEach(System.out::println);
                    break;
                case "5":
                    System.out.print("Enter person ID:");
                    int sel4 = Integer.parseInt(input());
                    System.out.println(findById(sel4));
                    break;
                case "6":
                    System.out.print("Enter person first name:");
                    String sel5 = input();
                    runPerson.findByName(sel5).forEach(System.out::println);
                    break;
                case "7":
                    System.out.print("Enter person last name:");
                    String sel6 = input();
                    runPerson.findByLName(sel6).forEach(System.out::println);
                    break;
                case "q":
                    runUntil = false;
                    break;
                case "t":
                    runTodo.findAll().forEach(System.out::println);
                    break;
                case "p":
                    runPerson.findAll().forEach(System.out::println);
                    break;
                case "id":
                    findOnOnlyId();
                    break;
                default:
                    System.out.println("Not valid selection");
                    break;
            }

        } while(runUntil);
    }
    public static void findByIdTodo(int id) {

        if(runTodo.findById(id) != null){
            System.out.println(runTodo.findById(id));
        } else {
            System.out.println("Cannot find person by id");
        }
    }
    public static Person findById(int id) {
        Person person;

        if(runPerson.findById(id) != null){
            person = runPerson.findById(id);
        } else {
            person = null;
            System.out.println("Cannot find person by id");
        }
        return person;
    }

    /** FIND AND PRINT ONLY ID (Console command "id") **/
    public static void findOnOnlyId(){
        System.out.println("---------------------------------------------------------------");
        System.out.print("TODO ID: \t\t\t");
        for(ToDo i : runTodo.findAll()){
            System.out.print(i.getId()+"|");
        }
        System.out.println("");
        System.out.print("PERSON ID: \t\t\t");
        for(Person i : runPerson.findAll()){
            System.out.print(i.getId()+"|");
        }
        System.out.println("");
        System.out.print("DONE ID: \t\t\t");
        for(ToDo i : runTodo.findByDoneStatus(true)){
            System.out.print(i.getId()+"|");
        }
        System.out.println("");
        System.out.print("NOT DONE ID: \t\t");
        for(ToDo i : runTodo.findByDoneStatus(false)){
            System.out.print(i.getId()+"|");
        }
        System.out.println("");
        System.out.print("UNASSIGNED TODO ID: ");
        for(ToDo i : runTodo.findByUnassignedToDoItems()){
            System.out.print(i.getId()+"|");
        }
        System.out.println("");
        System.out.print("ASSIGNED TODO ID: \t");
        for(ToDo i : runTodo.findByAssigned()){
            System.out.print(i.getId()+"|");
        }
        System.out.println("");
    }

}
