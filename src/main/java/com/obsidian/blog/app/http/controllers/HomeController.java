package com.obsidian.blog.app.http.controllers;

import com.obsidian.blog.app.domain.post.Post;
import com.obsidian.blog.app.domain.post.PostRepository;
import com.obsidian.core.di.annotations.Inject;
import com.obsidian.core.http.controller.BaseController;
import com.obsidian.core.http.controller.annotations.Controller;
import com.obsidian.core.routing.methods.GET;
import spark.Response;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController extends BaseController
{
    @Inject
    PostRepository postRepository;

    @GET(value = "/", name = "site.home")
    private Object homepage()
    {
        List<Post> posts = postRepository.findAll().stream().toList();
        return render("home.html", Map.of("posts", posts));
    }

    @GET(value = "/login")
    private Object redirectLogin(Response res)
    {
        res.redirect("/users/login");
        return "";
    }
}