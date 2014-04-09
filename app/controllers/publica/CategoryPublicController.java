package controllers.publica;

import java.util.List;

import manager.CategoryManager;
import models.Category;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.publica.category.listAll;
import views.html.publica.category.showCategory;
import DAO.CategoryDao;

public class CategoryPublicController extends Controller {
	private final static CategoryManager categoryManager = new CategoryManager(
			new CategoryDao());

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result listAllCategories() {
		List<Category> categories = categoryManager.listAll();

		return ok(listAll.render(categories));
	}

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result showCategory(int id) {
		Category category = categoryManager.findById(id);

		return ok(showCategory.render(category));
	}
}
