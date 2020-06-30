package se.lexicon.data;

import se.lexicon.model.Person;
import se.lexicon.model.ToDo;
import java.util.List;

public interface ToDoItems {

    ToDo create(ToDo todo);
    List<ToDo> findAll();
    ToDo findById(int id);
    List<ToDo> findByDoneStatus(boolean status);
    List<ToDo> findByAssignee(Person person);
    List<ToDo> findByUnassignedToDoItems();
    ToDo upDate(ToDo todo);
    boolean deleteById(int id);

}
