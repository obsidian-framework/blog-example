package fr.kainovaii.obsidian.app.http.controllers;

import fr.kainovaii.obsidian.app.domain.post.Post;
import fr.kainovaii.obsidian.app.domain.post.PostRepository;
import fr.kainovaii.obsidian.database.DB;
import fr.kainovaii.obsidian.http.controller.BaseController;
import fr.kainovaii.obsidian.http.controller.annotations.Controller;
import fr.kainovaii.obsidian.routing.methods.GET;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController extends BaseController
{
    @GET(value = "/", name = "site.home")
    private Object homepage(PostRepository postRepository)
    {
        List<Post> posts = DB.withConnection(() -> postRepository.findAll().stream().toList());
        return render("home.html", Map.of("posts", posts));
    }
}