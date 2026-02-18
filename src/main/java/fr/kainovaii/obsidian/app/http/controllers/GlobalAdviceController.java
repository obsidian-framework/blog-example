package fr.kainovaii.obsidian.app.http.controllers;

import fr.kainovaii.obsidian.app.security.AppUserDetails;
import fr.kainovaii.obsidian.http.controller.BaseController;
import fr.kainovaii.obsidian.http.controller.annotations.GlobalAdvice;
import spark.Request;
import spark.Response;

import static fr.kainovaii.obsidian.template.TemplateManager.setGlobal;

@GlobalAdvice
public class GlobalAdviceController extends BaseController
{
    public static void applyGlobals(Request req, Response res)
    {
        AppUserDetails appUserDetails = getLoggedUser(req);
        setGlobal("loggedUser", appUserDetails);
    }
}
