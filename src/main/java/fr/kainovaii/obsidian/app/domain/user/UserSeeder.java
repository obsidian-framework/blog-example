package fr.kainovaii.obsidian.app.domain.user;

import fr.kainovaii.obsidian.database.DB;
import fr.kainovaii.obsidian.database.seeder.annotations.Seeder;

@Seeder(priority = 10)
public class UserSeeder
{
    public static void seed()
    {
        UserRepository userRepository = new UserRepository();

        DB.withConnection(() -> {
            if (userRepository.findAll().isEmpty()) {
                userRepository.create("admin", "admin@local.dev", "$2a$12$7ZXd5t3DlUtcfCJGs52R7uDINIbqsGa5D82uFTmdXk5PEeJu3uZnu", "ADMIN");
            }
            return null;
        });
    }
}