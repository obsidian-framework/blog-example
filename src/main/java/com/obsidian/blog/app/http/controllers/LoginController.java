package com.obsidian.blog.app.http.controllers;

import com.obsidian.core.http.controller.BaseController;
import com.obsidian.core.http.controller.annotations.Controller;
import com.obsidian.core.routing.methods.GET;
import com.obsidian.core.routing.methods.POST;
import com.obsidian.core.security.auth.Auth;
import com.obsidian.core.security.csrf.annotations.CsrfProtect;
import com.obsidian.core.security.role.HasRole;
import com.obsidian.core.security.user.RequireLogin;
import spark.Request;
import spark.Response;

import java.util.Map;

@Controller("users")
public class LoginController extends BaseController
{
    @GET("/login")
    private Object login(Request req, Response res)
    {
        return render("user/login.html", Map.of());
    }

    @CsrfProtect
    @POST("/login")
    private Object loginBack(Request req, Response res)
    {
        String username = req.queryParams("username");
        String password = req.queryParams("password");

        if (Auth.login(username, password)) {
            return redirectWithSuccess("Welcome back", "/");
        }

        return redirectWithError("Incorrect login", "/users/login");
    }

    @RequireLogin
    @GET("/logout")
    private Object logout(Request req, Response res)
    {
        logout();
        return redirectWithInfo("Logged out", "/users/login");
    }
}