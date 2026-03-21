package com.obsidian.blog.app.http.controllers;

import com.obsidian.blog.app.domain.post.Post;
import com.obsidian.blog.app.domain.post.PostRepository;
import com.obsidian.blog.app.utils.TextUtile;
import com.obsidian.core.di.annotations.Inject;
import com.obsidian.core.http.controller.BaseController;
import com.obsidian.core.http.controller.annotations.Controller;
import com.obsidian.core.routing.methods.GET;
import com.obsidian.core.routing.methods.POST;
import com.obsidian.core.security.csrf.annotations.CsrfProtect;
import com.obsidian.core.security.role.HasRole;
import com.obsidian.core.security.user.RequireLogin;
import com.obsidian.core.validation.RequestValidator;
import com.obsidian.core.validation.ValidationResult;
import spark.Request;
import spark.Response;

import java.util.Map;

@Controller("posts")
public class PostController extends BaseController
{
    @Inject
    PostRepository postRepository;

    @RequireLogin
    @GET(value = "/new", name = "post.create")
    private Object postCreate() { return render("post/new.html", Map.of()); }

    @CsrfProtect
    @POST(value = "/new")
    private Object postCreateBack(Request req)
    {
        ValidationResult result = RequestValidator.validateSafe(req, Map.of(
            "title", "required|min:20",
            "content", "required|min:100"
        ));

        if (result.fails()) {
            return render("post/new.html", Map.of(
                "errors", result.getErrors(),
                "old", req.queryMap().toMap()
            ));
        }

        if (result.isValid()) {
            boolean creatingPost = postRepository.create(
                TextUtile.slugify(req.queryParams("title")),
                req.queryParams("title"),
                req.queryParams("content"),
                getLoggedUser().getUsername()
            );

            if (creatingPost) return redirectWithInfo("Creating successfully", "/");
        }
        return redirectWithInfo("You have been redirected", "/");
    }

    @RequireLogin
    @GET(value = "/edit/:slug", name = "post.create")
    private Object postEdit(Request req)
    {
        Post post = postRepository.findBySlug(req.params("slug"));
        return render("post/edit.html", Map.of("post", post));
    }

    @CsrfProtect
    @POST(value = "/edit")
    private Object postEditBack(Request req)
    {
        ValidationResult result = RequestValidator.validateSafe(req, Map.of(
            "title", "required|min:20",
            "content", "required|min:100"
        ));

        if (result.fails()) {
            return render("post/new.html", Map.of(
                "errors", result.getErrors(),
                "old", req.queryMap().toMap()
            ));
        }

        if (result.isValid()) {
            String slug = req.queryParams("slug");

            boolean creatingPost = postRepository.update(
                slug,
                TextUtile.slugify(req.queryParams("title")),
                req.queryParams("title"),
                req.queryParams("content"),
                getLoggedUser().getUsername()
            );
            if (creatingPost) return redirectWithInfo("Updating successfully", "/posts/s/" + slug);
        }
        return redirectWithInfo("You have been redirected", "/");
    }

    @GET(value = "/s/:slug", name = "post.single")
    private Object postSingle(Request req)
    {
        Post post = postRepository.findBySlug(req.params("slug"));
        return render("post/single.html", Map.of( "post", post ));
    }

    @RequireLogin
    @POST(value = "/delete")
    public Object delete(Request req, Response res)
    {
        boolean query = postRepository.delete(req.queryParams("slug"));
        if (query) return redirectWithInfo("Deleting successfully", "/");
        return redirectWithInfo("You have been redirected", "/");
    }
}