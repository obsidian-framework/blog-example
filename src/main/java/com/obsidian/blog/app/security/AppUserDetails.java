package com.obsidian.blog.app.security;


import com.obsidian.core.security.user.UserDetails;

public interface AppUserDetails extends UserDetails
{
    String getEmail();
}
