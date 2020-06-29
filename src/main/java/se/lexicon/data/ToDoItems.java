package se.lexicon.data;

import se.lexicon.model.Person;
import se.lexicon.model.ToDo;

import java.util.Collection;

public interface ToDoItems {

    ToDo create(ToDo todo);
    Collection<ToDo> findAll();
    ToDo findById(int id);
    Collection<ToDo> findByDoneStatus(boolean status);
    Collection<ToDo> findByAssignee(Person person);
    Collection<ToDo> findByUnassignedToDoItems();
    ToDo upDate(ToDo todo);
    boolean deleteById(int id);

}
