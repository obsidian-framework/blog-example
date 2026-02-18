package fr.kainovaii.obsidian.app.domain.post;

import fr.kainovaii.obsidian.di.annotations.Repository;
import org.javalite.activejdbc.LazyList;

@Repository
public class PostRepository
{
    public LazyList<Post> findAll() {
        return Post.findAll();
    }

    public Post findBySlug(String slug) {
        return Post.findFirst("slug = ?", slug);
    }

    public boolean create(String slug, String title, String content, String user)
    {
        Post post = new Post();
        post.setSlug(slug);
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);
        return post.saveIt();
    }

    public boolean update(String currentSlug, String slug, String title, String content, String user)
    {
        Post post = findBySlug(currentSlug);
        post.setSlug(slug);
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);
        return post.saveIt();
    }
}