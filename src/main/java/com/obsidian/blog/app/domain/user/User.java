package com.obsidian.blog.app.domain.user;

import com.obsidian.blog.app.security.AppUserDetails;
import com.obsidian.core.database.orm.model.Model;

public class User extends Model implements AppUserDetails
{
    // Getters
    public String getUsername() {
        return getString("username");
    }

    public String getEmail() {
        return getString("email");
    }

    public String getPassword() {
        return getString("password");
    }

    public String getRole() {
        return getString("role");
    }

    // Setters
    public void setUsername(String username) {
        set("username", username);
    }

    public void setEmail(String email) {
        set("email", email);
    }

    public void setPassword(String password) {
        set("password", password);
    }

    public void setRole(String role) {
        set("role", role);
    }
}
