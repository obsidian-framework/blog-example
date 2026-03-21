package com.obsidian.blog.app.domain.user;


import com.obsidian.core.database.orm.repository.BaseRepository;
import com.obsidian.core.di.annotations.Repository;

import java.util.List;

@Repository
public class UserRepository extends BaseRepository<User>
{
    public UserRepository () { super (User.class); }

    public User findByUsername(String username) {
        return findBy("username", username);
    }

    public boolean userExist(String username) {
        return existsWhere("username", username);
    }

    public boolean delete(String username)
    {
        User user = findByUsername(username);
        return user.delete();
    }

    public Boolean create(String username, String email, String password, String role)
    {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user.saveIt();
    }

    public Boolean update(String usernameCurrent, String username, String email, String password, String role)
    {
        User user = findByUsername(usernameCurrent);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user.saveIt();
    }
}