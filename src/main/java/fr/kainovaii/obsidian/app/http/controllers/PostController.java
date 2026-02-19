package fr.kainovaii.obsidian.app.http.controllers;

import fr.kainovaii.obsidian.app.domain.post.Post;
import fr.kainovaii.obsidian.app.domain.post.PostRepository;
import fr.kainovaii.obsidian.app.utils.TextUtile;
import fr.kainovaii.obsidian.database.DB;
import fr.kainovaii.obsidian.http.controller.BaseController;
import fr.kainovaii.obsidian.http.controller.annotations.Controller;
import fr.kainovaii.obsidian.routing.methods.GET;
import fr.kainovaii.obsidian.routing.methods.POST;
import fr.kainovaii.obsidian.security.csrf.annotations.CsrfProtect;
import fr.kainovaii.obsidian.security.role.HasRole;
import fr.kainovaii.obsidian.validation.RequestValidator;
import fr.kainovaii.obsidian.validation.ValidationResult;
import spark.Request;
import spark.Response;

import java.text.Normalizer;
import java.util.Map;

@Controller
public class PostController extends BaseController
{
    @HasRole("DEFAULT")
    @GET(value = "/posts/new", name = "post.create")
    private Object postCreate() { return render("post/new.html", Map.of()); }

    @CsrfProtect
    @POST(value = "/posts/new")
    private Object postCreateBack(Request req, Response res, PostRepository postRepository)
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
            Boolean creatingPost = DB.withConnection(() -> postRepository.create(
                    TextUtile.slugify(req.queryParams("title")),
                    req.queryParams("title"),
                    req.queryParams("content"),
                    getLoggedUser(req).getUsername()
            ));
            if (creatingPost) return redirectWithFlash(req, res, "info","Creating successfully", "/");
        }
        return redirectWithFlash(req, res, "info","You have been redirected", "/");
    }

    @HasRole("DEFAULT")
    @GET(value = "/posts/edit/:slug", name = "post.create")
    private Object postEdit(Request req, PostRepository postRepository)
    {
        Post post = DB.withConnection(() -> postRepository.findBySlug(req.params("slug")));
        return render("post/edit.html", Map.of("post", post));
    }

    @CsrfProtect
    @POST(value = "/posts/edit")
    private Object postEditBack(Request req, Response res, PostRepository postRepository)
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
            Boolean creatingPost = DB.withConnection(() -> postRepository.update(
                req.queryParams("slug"),
                TextUtile.slugify(req.queryParams("title")),
                req.queryParams("title"),
                req.queryParams("content"),
                getLoggedUser(req).getUsername()
            ));
            if (creatingPost) return redirectWithFlash(req, res, "info","Creating successfully", "/");
        }
        return redirectWithFlash(req, res, "info","You have been redirected", "/");
    }

    @GET(value = "/posts/s/:slug", name = "post.single")
    private Object postSingle(Request req, PostRepository postRepository)
    {
        Post post = DB.withConnection(() -> postRepository.findBySlug(req.params("slug")));
        System.out.println("user: " + post.getString("user"));
        return render("post/single.html", Map.of( "post", post ));
    }

    @HasRole("DEFAULT")
    @POST(value = "/posts/delete")
    public Object delete(Request req, Response res, PostRepository postRepository)
    {
        boolean query = DB.withConnection(() -> postRepository.delete(req.queryParams("slug")));
        if (query) return redirectWithFlash(req, res, "info","Deleting successfully", "/");
        return redirectWithFlash(req, res, "info","You have been redirected", "/");
    }
}