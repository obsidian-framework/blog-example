package com.obsidian.blog.app.domain.post;

import com.obsidian.core.database.orm.repository.BaseRepository;
import com.obsidian.core.di.annotations.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class PostRepository extends BaseRepository<Post>
{
    public PostRepository() { super(Post.class); }

    public Post findBySlug(String slug) {
        return findBy("slug", slug);
    }

    public List<Post> findByUser(String user) {
        return Collections.singletonList(findBy("user", user));
    }

    public boolean delete(String slug)
    {
        Post post = findBySlug(slug);
        return post.delete();
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