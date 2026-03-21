package com.obsidian.blog.app.security;

import com.obsidian.blog.app.domain.user.User;
import com.obsidian.blog.app.domain.user.UserRepository;
import com.obsidian.core.di.annotations.Inject;
import com.obsidian.core.security.user.UserDetailsService;
import com.obsidian.core.security.user.UserDetailsServiceImpl;

@UserDetailsServiceImpl
public class AppUserDetailsService implements UserDetailsService
{
    @Inject
    UserRepository userRepository;

    @Override
    public AppUserDetails loadByUsername(String username)
    {
        if (!userRepository.userExist(username)) return null;
        User user = userRepository.findByUsername(username);
        return adapt(user);
    }

    @Override
    public AppUserDetails loadById(Object id)
    {
        User user = userRepository.findById(id);
        return adapt(user);
    }

    private AppUserDetails adapt(User user) {
        if (user == null) return null;
        return new AppUserDetails() {
            public Object getId() { return user.getId(); }
            public String getUsername() { return user.getUsername(); }
            public String getPassword() { return user.getPassword(); }
            public String getRole() { return user.getRole(); }
            public String getEmail() { return user.getEmail(); }
        };
    }
}