package com.obsidian.blog.app.http.controllers;

import com.obsidian.blog.app.security.AppUserDetails;
import com.obsidian.core.http.controller.BaseController;
import com.obsidian.core.http.controller.annotations.GlobalAdvice;
import spark.Request;
import spark.Response;

import static com.obsidian.core.template.TemplateManager.setGlobal;

@GlobalAdvice
public class GlobalAdviceController extends BaseController
{
    public static void applyGlobals(Request req, Response res)
    {
        if (isLogged()) {
            AppUserDetails appUserDetails = getLoggedUser();
            setGlobal("loggedUser", appUserDetails);
        }
    }
}