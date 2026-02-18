package fr.kainovaii.obsidian.app.domain.user;

import fr.kainovaii.obsidian.di.annotations.Repository;
import org.javalite.activejdbc.LazyList;

@Repository
public class UserRepository
{
    public LazyList<User> findAll() {
        return User.findAll();
    }

    public User findByUsername(String username) {
        return User.findFirst("username = ?", username);
    }

    public static boolean userExist(String username) {
        return User.findFirst("username = ?", username) != null;
    }

    public void create(String username, String email, String password, String role)
    {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.saveIt();
    }
}