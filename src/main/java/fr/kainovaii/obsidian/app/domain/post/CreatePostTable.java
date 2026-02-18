package fr.kainovaii.obsidian.app.domain.post;

import fr.kainovaii.obsidian.database.Migration;

public class CreatePostTable extends Migration
{
    @Override
    public void up()
    {
        createTable("posts", table -> {
            table.id();
            table.string("slug").notNull();
            table.string("title").notNull();
            table.string("content").notNull();
            table.string("user").notNull();
            table.timestamps();
        });
    }

    @Override
    public void down() {
        dropTable("posts");
    }
}
