package fr.kainovaii.obsidian.app.http.controllers;

import fr.kainovaii.obsidian.app.domain.user.UserRepository;
import fr.kainovaii.obsidian.database.DB;
import fr.kainovaii.obsidian.http.controller.BaseController;
import fr.kainovaii.obsidian.http.controller.annotations.Controller;
import fr.kainovaii.obsidian.routing.methods.GET;
import fr.kainovaii.obsidian.routing.methods.POST;
import fr.kainovaii.obsidian.security.csrf.annotations.CsrfProtect;
import fr.kainovaii.obsidian.validation.RequestValidator;
import fr.kainovaii.obsidian.validation.ValidationResult;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.Map;

@Controller
public class RegisterController extends BaseController
{
    @GET("/users/register")
    private Object register(Request req, Response res) { return render("user/register.html", Map.of()); }

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
            if (DB.withConnection(() -> UserRepository.userExist(username))) {
                return redirectWithFlash(req, res, "error","User currently exist", "/users/register");
            }

            boolean creating = DB.withConnection(() -> {
                return userRepository.create(
                    username,
                    email,
                    hashPassword(password),
                    "DEFAULT"
                );
            });
            if (creating && login(username, password, session)) {
                return redirectWithFlash(req, res, "success","Register successfully", "/");
            }
        }
        return redirectWithFlash(req, res, "info","You have been redirected", "/");
    }
}