package se.lexicon.data;

import se.lexicon.entity.DbSource;
import se.lexicon.model.Person;
import se.lexicon.model.ToDo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoItemsRep implements ToDoItems{

    /** SQL queries (Command lines) **/
    private static final String FIND_ALL        = "SELECT * FROM todo_item";
    private static final String FIND_BY_ID      = "SELECT * FROM todo_item WHERE todo_id = ?";
    private static final String FIND_ALL_DONE   = "SELECT * FROM todo_item WHERE done = ?";
    private static final String FIND_ASSIGNEE   = "SELECT * FROM todo_item WHERE assignee_id = ?";
    private static final String FIND_UNASSIGNED = "SELECT * FROM todo_item WHERE assignee_id IS NULL";
    private static final String UPDATE          = "UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?";
    private static final String DELETE          = "DELETE FROM todo_item WHERE todo_id = ?";
    private static final String CREATE_TODO     = "INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES (?,?,?,?,?)";

    @Override
    public ToDo create(ToDo todo) {

        if (todo.getId() != 0){
            throw new IllegalArgumentException("Use update instead");
        }

        ToDo todos = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet keySet = null;

        try {
            connection = DbSource.getConnection();
            statement = connection.prepareStatement(CREATE_TODO, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, todo.getTitle());
            statement.setString(2, todo.getDescription());
            statement.setDate(3, Date.valueOf(todo.getDeadLine()));
            statement.setBoolean(4, todo.isDone());
            statement.setObject(5,todo.getAssigneeId(), java.sql.Types.INTEGER);
            statement.execute();

            keySet = statement.getGeneratedKeys();
            while (keySet.next()) {
                todos = new ToDo(
                        keySet.getInt(1),
                        todo.getTitle(),
                        todo.getDescription(),
                        todo.getDeadLine(),
                        todo.isDone(),
                        todo.getAssigneeId()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, statement, keySet);
        }
        return todos == null ? todo : todos;
    }

    @Override
    public List<ToDo> findAll() {
        List<ToDo> todoList = new ArrayList<>();

        try(Connection connection = DbSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                todoList.add(createTodoResultSet(resultSet));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todoList;
    }

    @Override
    public ToDo findById(int id) {
        ToDo todo = null;

        try(Connection connection = DbSource.getConnection();
            PreparedStatement statement = createFindIdStat(connection, id);
            ResultSet resultSet = statement.executeQuery()){

            while(resultSet.next()){
                todo = createTodoResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todo;
    }

    @Override
    public List<ToDo> findByDoneStatus(boolean status) {
        List<ToDo> todoList = new ArrayList<>();

        try(Connection connection = DbSource.getConnection();
            PreparedStatement statement = createFindDone(connection,status);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                todoList.add(createTodoResultSet(resultSet));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todoList;
    }

    @Override
    public List<ToDo> findByAssignee(Person person) {

        int getPerson = person.getId();
        List<ToDo> toDoList = new ArrayList<>();

        try(Connection connection = DbSource.getConnection();
            PreparedStatement statement = createFindAssignee(connection, getPerson);
            ResultSet resultSet = statement.executeQuery()){

            while(resultSet.next()){
                toDoList.add(createTodoResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toDoList;
    }

    @Override
    public List<ToDo> findByUnassignedToDoItems() {
        List<ToDo> toDoList = new ArrayList<>();

        try(Connection connection = DbSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_UNASSIGNED);
            ResultSet resultSet = statement.executeQuery()){

            while(resultSet.next()){
                toDoList.add(createTodoResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toDoList;
    }

    @Override
    public ToDo upDate(ToDo todo) {

        if(todo.getId() == 0){
            throw new IllegalArgumentException("Cannot update, there is no person by that ID");
        }
        try(Connection connection = DbSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE)){

            statement.setString(1,todo.getTitle());
            statement.setString(2,todo.getDescription());
            statement.setDate(3, Date.valueOf(todo.getDeadLine()));
            statement.setBoolean(4,todo.isDone());
            statement.setObject(5,todo.getAssigneeId(), java.sql.Types.INTEGER);
            statement.setInt(6,todo.getId());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todo;
    }

    @Override
    public boolean deleteById(int id) {

        boolean deleted = false;

        try(Connection connection = DbSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setInt(1,id);

                if(statement.executeUpdate() != 0){
                    System.out.println("ToDo deleted from database");
                    deleted = true;
                } else {
                    System.out.println("Cannot find todo with that ID");
                    deleted = false;
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    /** Create queries sets **/
    public ToDo createTodoResultSet(ResultSet resultSet) throws SQLException {
        return new ToDo(
                resultSet.getInt("todo_id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getDate("deadline").toLocalDate(),
                resultSet.getBoolean("done"),
                resultSet.getInt("assignee_id")
        );
    }
    public PreparedStatement createFindIdStat(Connection connection, int id) throws SQLException {
            PreparedStatement statement =connection.prepareStatement(FIND_BY_ID);
            statement.setInt(1,id);
            return statement;
    }
    public PreparedStatement createFindDone(Connection connection, boolean status) throws SQLException {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_DONE);
            statement.setBoolean(1,status);
            return statement;
    }
    public PreparedStatement createFindAssignee(Connection connection, int id) throws SQLException {
            PreparedStatement statement = connection.prepareStatement(FIND_ASSIGNEE);
            statement.setInt(1,id);
            return statement;
    }

    /**  Close all connections to DB  **/
    public static void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet){
        try{
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
