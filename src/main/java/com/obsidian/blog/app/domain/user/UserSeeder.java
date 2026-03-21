package com.obsidian.blog.app.domain.user;

import com.obsidian.core.database.seeder.SeederInterface;
import com.obsidian.core.database.seeder.annotations.Seeder;
import com.obsidian.core.di.annotations.Inject;

@Seeder(priority = 10)
public class UserSeeder implements SeederInterface
{
    @Inject
    private UserRepository userRepository;

    @Override
    public void seed()
    {
        if (userRepository.findAll().isEmpty()) {
            userRepository.create("admin", "admin@local.dev", "$2a$12$7ZXd5t3DlUtcfCJGs52R7uDINIbqsGa5D82uFTmdXk5PEeJu3uZnu", "ADMIN");
        }
    }
}