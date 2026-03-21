package com.obsidian.blog.app.http.controllers;

import com.obsidian.blog.app.domain.user.UserRepository;
import com.obsidian.core.security.auth.Auth;
import spark.Request;
import spark.Response;
import spark.Session;
import com.obsidian.core.http.controller.BaseController;
import com.obsidian.core.http.controller.annotations.Controller;
import com.obsidian.core.routing.methods.GET;
import com.obsidian.core.routing.methods.POST;
import com.obsidian.core.security.csrf.annotations.CsrfProtect;
import com.obsidian.core.validation.RequestValidator;
import com.obsidian.core.validation.ValidationResult;

import java.util.Map;

@Controller
public class RegisterController extends BaseController
{
    @GET("/users/register")
    private Object register() { return render("user/register.html", Map.of()); }

    @CsrfProtect
    @POST("/users/register")
    private Object registerBack(Request req, Response res, UserRepository userRepository)
    {
        String username = req.queryParams("username");
        String email = req.queryParams("email");
        String password = req.queryParams("password");
        Session session = req.session(true);

        ValidationResult result = RequestValidator.validateSafe(req, Map.of(
            "username", "required|min:5",
            "email", "email|required|min:5",
            "password", "required|min:8"
        ));

        if (result.fails()) {
            return render("user/register.html", Map.of(
                "errors", result.getErrors(),
                "old", req.queryMap().toMap()
            ));
        }

        if (result.isValid())
        {
            if (userRepository.userExist(username)) {
                return redirectWithError("User currently exist", "/users/register");
            }

            boolean creating = userRepository.create( username, email, hashPassword(password), "DEFAULT" );
            if (creating && Auth.login(username, password)) {
                return redirectWithSuccess("Register successfully", "/");
            }
        }
        return redirectWithInfo("You have been redirected", "/");
    }
}