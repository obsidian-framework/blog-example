package fr.kainovaii.obsidian.app.http.controllers;

import fr.kainovaii.obsidian.app.domain.post.Post;
import fr.kainovaii.obsidian.app.domain.post.PostRepository;
import fr.kainovaii.obsidian.app.domain.user.User;
import fr.kainovaii.obsidian.app.domain.user.UserRepository;
import fr.kainovaii.obsidian.app.security.AppUserDetails;
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
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends BaseController
{
    @GET(value = "/users/s/:username", name = "user.profile")
    private Object profile(Request req, UserRepository userRepository, PostRepository postRepository)
    {
        String username = req.params("username");
        User user = DB.withConnection(() -> userRepository.findByUsername(username));
        List<Post> posts = DB.withConnection(() -> postRepository.findByUser(username).stream().toList());

        return render("user/profile.html", Map.of(
                "user", user,
                "posts", posts
        ));
    }

    @HasRole("DEFAULT")
    @GET(value = "/users/settings", name = "user.settings")
    private Object settings(Request req, Response res)
    {
        return render("user/settings.html", Map.of());
    }

    @CsrfProtect
    @HasRole("DEFAULT")
    @POST(value = "/users/update", name = "user.update")
    private Object update(Request req, Response res, UserRepository userRepository)
    {
        ValidationResult result = RequestValidator.validateSafe(req, Map.of(
            "username", "required|min:5",
            "email", "email|required|min:5"
        ));

        if (result.fails()) {
            return render("user/settings.html", Map.of(
                "errors", result.getErrors(),
                "old", req.queryMap().toMap()
            ));
        }

        if (result.isValid()) {
            DB.withConnection(() -> {
                return userRepository.update(
                    getLoggedUser(req).getUsername(),
                    req.queryParams("username"),
                    req.queryParams("email"),
                    getLoggedUser(req).getPassword(),
                    getLoggedUser(req).getRole()
                );
            });
            return redirectWithFlash(req, res, "success","Update successfully", "/users/settings");
        }
        return redirectWithFlash(req, res, "info","You have been redirected", "/");
    }

    @CsrfProtect
    @HasRole("DEFAULT")
    @POST(value = "/users/update/password", name = "user.update.password")
    private Object updatePassword(Request req, Response res, UserRepository userRepository)
    {
        AppUserDetails user = getLoggedUser(req);

        ValidationResult result = RequestValidator.validateSafe(req, Map.of(
            "password", "required|min:8",
            "password_confirm", "required|min:8"
        ));

        if (result.fails()) {
            return render("user/settings.html", Map.of(
                "errors", result.getErrors(),
                "old", req.queryMap().toMap()
            ));
        }

        if (result.isValid()) {
            DB.withConnection(() -> {
                return userRepository.update(
                    user.getUsername(),
                    user.getUsername(),
                    user.getEmail(),
                    hashPassword(req.queryParams("password")),
                    user.getRole()
                );
            });
            return redirectWithFlash(req, res, "success","Update successfully", "/users/settings");
        }
        return redirectWithFlash(req, res, "info","You have been redirected", "/");
    }
}