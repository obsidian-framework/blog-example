package com.obsidian.blog.app.domain.user;

import com.obsidian.core.database.Migration;

public class CreateUserTable extends Migration
{
    @Override
    public void up()
    {
        createTable("users", table -> {
            table.id();
            table.string("username").notNull();
            table.string("email").notNull();
            table.string("password").notNull();
            table.string("role").notNull();
            table.timestamps();
        });
    }

    @Override
    public void down() {
        dropTable("users");
    }
}
