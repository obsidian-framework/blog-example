package com.obsidian.blog.app.domain.post;


import com.obsidian.core.database.orm.model.Model;

import java.util.Date;

public class Post extends Model
{
    // Getters
    public String getSlug() {
        return getString("slug");
    }

    public String getTitle() {
        return getString("title");
    }

    public String getContent() {
        return getString("content");
    }

    public String getUser() {
        return getString("user");
    }

    public Date createdAt() {
        long timestamp = getLong("created_at");
        return new Date(timestamp);
    }

    // Setters
    public void setSlug(String slug) {
        set("slug", slug);
    }

    public void setTitle(String title) { set("title", title); }

    public void setContent(String content) {
        set("content", content);
    }

    public void setUser(String user) {
        set("user", user);
    }
}
