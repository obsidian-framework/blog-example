package fr.kainovaii.obsidian.app.domain.post;

import org.javalite.activejdbc.Model;

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

    public String getCreatedAt() { return getString("created_at"); }

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
