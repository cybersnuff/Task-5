package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    public User getUserModelSeries(String model, int series) {
        String hql = "FROM User u WHERE u.car.model = :model AND u.car.series = :series";
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("model", model);
        query.setParameter("series", series);
        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            System.out.println("Юзер с моделью " + model + " и серией " + series + " - не найден");
            return null;
        } else {
            User user = users.get(0);
            System.out.println("Пользователь с моделью " + model + " и серией " + series +
                    " - " + user.getFirstName() + " "+
                    " - "+ user.getLastName() +
                    " -  " + user.getEmail());
            return user;
        }

    }
}
