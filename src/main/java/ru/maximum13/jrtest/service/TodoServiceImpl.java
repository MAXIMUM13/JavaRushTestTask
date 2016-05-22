package ru.maximum13.jrtest.service;

import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Service;
import ru.maximum13.jrtest.model.*;
import ru.maximum13.jrtest.repository.HibernateUtil;

/**
 *
 * @author MAXIMUM13
 */
@Service("todoService")
public class TodoServiceImpl implements TodoService {

    private final Session session;

    public TodoServiceImpl() {
        this.session = HibernateUtil.getSession();
    }

    @Override
    public int count() {
        Criteria criteria = this.session.createCriteria(Todo.class)
                .setProjection(Projections.rowCount());
        return ((Number) criteria.uniqueResult()).intValue();
    }

    @Override
    public int count(Todo.Category category) {
        Criteria criteria = this.session.createCriteria(Todo.class);
        switch (category) {
            case ALL:
                return this.count();
            case DONE:
                criteria.add(Restrictions.eq("done", true));
                break;
            case NOT_DONE:
                criteria.add(Restrictions.eq("done", false));
                break;
        }
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.uniqueResult()).intValue();
    }

    @Override
    public void createTodo(String description) {
        this.session.beginTransaction();
        {
            this.session.save(new Todo(description));
        }
        this.session.getTransaction().commit();
    }

    @Override
    public Todo getTodoById(int id) {
        return this.session.get(Todo.class, id);
    }

    @Override
    public List<Todo> getAllTodos() {
        return this.session.createCriteria(Todo.class).list();
    }

    @Override
    public List<Todo> getTodos(int count) {
        return this.session.createCriteria(Todo.class).setMaxResults(count).list();
    }

    @Override
    public List<Todo> getTodos(Todo.Category category, int count) {
        Criteria criteria = this.session.createCriteria(Todo.class);
        switch (category) {
            case DONE:
                criteria.add(Restrictions.eq("done", true));
                break;
            case NOT_DONE:
                criteria.add(Restrictions.eq("done", false));
                break;
        }
        criteria.setMaxResults(count);
        return criteria.list();
    }

    @Override
    public List<Todo> getTodos(Todo.Property sortBy, SortOrder order, int count) {
        return this.getTodos(sortBy, order, 0, count);
    }

    @Override
    public List<Todo> getTodos(Todo.Category category,
            Todo.Property sortBy, SortOrder order, int count) {
        return this.getTodos(category, sortBy, order, 0, count);
    }

    @Override
    public List<Todo> getTodos(Todo.Property sortBy, SortOrder order, int startIndex, int count) {
        return this.getTodos(Todo.Category.ALL, sortBy, order, startIndex, count);
    }

    @Override
    public List<Todo> getTodos(Todo.Category category, Todo.Property sortBy,
            SortOrder order, int startIndex, int count) {
        Criteria criteria = this.session.createCriteria(Todo.class);

        switch (category) {
            case DONE:
                criteria.add(Restrictions.eq("done", true));
                break;
            case NOT_DONE:
                criteria.add(Restrictions.eq("done", false));
                break;
        }

        criteria.addOrder(
                (order == SortOrder.ASC)
                ? Order.asc(sortBy.getName())
                : Order.desc(sortBy.getName())
        );
        criteria.setFirstResult(startIndex);
        criteria.setMaxResults(count);
        return criteria.list();
    }

    @Override
    public void updateTodo(int id, String description) {
        Todo todo = this.getTodoById(id);
        this.updateTodo(todo, description);
    }

    @Override
    public void updateTodo(int id, boolean done) {
        Todo todo = this.getTodoById(id);
        this.updateTodo(todo, done);
    }

    @Override
    public void updateTodo(int id, String description, boolean done) {
        Todo todo = this.getTodoById(id);
        this.updateTodo(todo, description, done);
    }

    @Override
    public void updateTodo(Todo todo, String description) {
        this.session.beginTransaction();
        {
            todo.setDescription(description);
        }
        this.session.getTransaction().commit();
    }

    @Override
    public void updateTodo(Todo todo, boolean done) {
        this.session.beginTransaction();
        {
            todo.setDone(done);
        }
        this.session.getTransaction().commit();
    }

    @Override
    public void updateTodo(Todo todo, String description, boolean done) {
        this.session.beginTransaction();
        {
            todo.setDescription(description);
            todo.setDone(done);
        }
        this.session.getTransaction().commit();
    }

    @Override
    public Todo deleteTodo(int id) {
        Todo todo = this.getTodoById(id);
        this.deleteTodo(todo);
        return todo;
    }

    @Override
    public void deleteTodo(Todo todo) {
        this.session.beginTransaction();
        {
            this.session.delete(todo);
        }
        this.session.getTransaction().commit();
    }

    @Override
    public void deleteTodos(List<Integer> ids) {
        String idsSet = ids.toString().replace('[', '(').replace(']', ')');
        this.session.beginTransaction();
        {
            this.session.createQuery("DELETE FROM Todo WHERE id IN " + idsSet).executeUpdate();
        }
        this.session.getTransaction().commit();
    }

    @Override
    public String getAppName() {
        return Constants.APP_NAME;
    }

    @Override
    public String getTaskName() {
        return Constants.TASK_NAME;
    }
}
