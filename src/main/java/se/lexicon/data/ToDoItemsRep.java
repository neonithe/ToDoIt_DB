package se.lexicon.data;

import se.lexicon.model.Person;
import se.lexicon.model.ToDo;
import java.util.List;

public class ToDoItemsRep implements ToDoItems{
    @Override
    public ToDo create(ToDo todo) {
        return null;
    }

    @Override
    public List<ToDo> findAll() {
        return null;
    }

    @Override
    public ToDo findById(int id) {
        return null;
    }

    @Override
    public List<ToDo> findByDoneStatus(boolean status) {
        return null;
    }

    @Override
    public List<ToDo> findByAssignee(Person person) {
        return null;
    }

    @Override
    public List<ToDo> findByUnassignedToDoItems() {
        return null;
    }

    @Override
    public ToDo upDate(ToDo todo) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
