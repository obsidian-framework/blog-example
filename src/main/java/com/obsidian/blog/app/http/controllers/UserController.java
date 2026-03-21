package com.obsidian.blog.app.http.controllers;

import com.obsidian.blog.app.domain.post.Post;
import com.obsidian.blog.app.domain.post.PostRepository;
import com.obsidian.blog.app.domain.user.User;
import com.obsidian.blog.app.domain.user.UserRepository;
import com.obsidian.blog.app.security.AppUserDetails;
import com.obsidian.core.di.annotations.Inject;
import com.obsidian.core.http.controller.BaseController;
import com.obsidian.core.http.controller.annotations.Controller;
import com.obsidian.core.routing.methods.GET;
import com.obsidian.core.routing.methods.POST;
import com.obsidian.core.security.csrf.annotations.CsrfProtect;
import com.obsidian.core.security.user.CurrentUser;
import com.obsidian.core.security.user.RequireLogin;
import com.obsidian.core.validation.RequestValidator;
import com.obsidian.core.validation.ValidationResult;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Map;

@Controller("users")
public class UserController extends BaseController
{
    @Inject
    UserRepository userRepository;

    @GET(value = "/s/:username", name = "user.profile")
    private Object profile(Request req, PostRepository postRepository)
    {
        String username = req.params("username");
        User user = userRepository.findByUsername(username);
        List<Post> posts = postRepository.findByUser(username).stream().toList();

        return render("user/profile.html", Map.of(
            "user", user,
            "posts", posts
        ));
    }

    @RequireLogin
    @GET(value = "/settings", name = "user.settings")
    private Object settings(Request req, Response res)
    {
        return render("user/settings.html", Map.of());
    }

    @CsrfProtect
    @RequireLogin
    @POST(value = "/update", name = "user.update")
    private Object update(Request req, Response res)
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
            userRepository.update(
                getLoggedUser().getUsername(),
                req.queryParams("username"),
                req.queryParams("email"),
                getLoggedUser().getPassword(),
                getLoggedUser().getRole()
            );
            return redirectWithSuccess("Update successfully", "/users/settings");
        }
        return redirectWithInfo("You have been redirected", "/");
    }

    @CsrfProtect
    @RequireLogin
    @POST(value = "/update/password", name = "user.update.password")
    private Object updatePassword(Request req, Response res, @CurrentUser AppUserDetails user)
    {

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
            userRepository.update(
                    user.getUsername(),
                    user.getUsername(),
                    user.getEmail(),
                    hashPassword(req.queryParams("password")),
                    user.getRole()
            );
            return redirectWithSuccess("Update successfully", "/users/settings");
        }
        return redirectWithInfo("You have been redirected", "/");
    }

    @RequireLogin
    @POST(value = "/delete")
    public Object deleteUser(Request req, Response res, @CurrentUser AppUserDetails user)
    {
        boolean query =  userRepository.delete(user.getUsername());

        if (query) return redirectWithSuccess("Deleting successfully", "/users/login");

        return redirectWithInfo("You have been redirected", "/");
    }
}