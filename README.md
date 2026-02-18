# Blog Example — Obsidian Framework

A fully functional blog application built with [Obsidian](https://github.com/obsidian-framework/obsidian), demonstrating how to build a real-world Java web app with authentication, post management, form validation, and more — all with minimal boilerplate.

## ✨ Concepts Demonstrated

| Concept | Where |
|---------|-------|
| 🛣️ **Annotation routing** | `@GET`, `@POST` on controller methods |
| 🔐 **Auth + roles** | `LoginController`, `AppUserDetailsService`, `@HasRole` |
| ✅ **Validation** | `RequestValidator.validateSafe()` in `PostController` |
| 🛡️ **CSRF** | `@CsrfProtect` + `{{ csrf_field() }}` in forms |
| 💬 **Flash messages** | `redirectWithFlash(req, res, "success", "...", "/")` |
| 💉 **Dependency injection** | `@Repository` auto-injected into controller methods |
| 🗃️ **Migrations** | `CreatePostTable`, `CreateUserTable` |
| 🎨 **Templating** | `render("post/new.html", Map.of(...))` |

## 🚀 Quick Start

```bash
git clone https://github.com/obsidian-framework/blog-example.git
cd blog-example
mvn compile exec:java
```

→ The app runs on `http://localhost:4567`

## 🛣️ Routes

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| `GET` | `/` | — | List all posts |
| `GET` | `/users/login` | — | Login page |
| `POST` | `/users/login` | CSRF | Authenticate |
| `GET` | `/users/logout` | `DEFAULT` | Logout |
| `GET` | `/posts/new` | `DEFAULT` | New post form |
| `POST` | `/posts/new` | CSRF | Create a post |
| `GET` | `/posts/edit/:slug` | `DEFAULT` | Edit post form |
| `POST` | `/posts/edit` | CSRF | Update a post |
| `GET` | `/posts/s/:slug` | — | View a single post |

## 📖 Documentation

Full Obsidian documentation is available at [obsidian.kainovaii.dev](https://obsidian.kainovaii.dev).

## 📝 License

[MIT](LICENSE)