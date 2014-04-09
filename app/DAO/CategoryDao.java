package DAO;

import models.Category;

public final class CategoryDao extends GenericJpaDao<Integer, Category> {
	@Override
	public Category save(Category categoryToSave) {

		if (isNewCategory(categoryToSave)) {
			super.save(new Category(categoryToSave.getName()));
		} else {
			Category category = findById(categoryToSave.getId());
			category.setName(categoryToSave.getName());
		}

		return null;
	}

	private static boolean isNewCategory(Category category) {
		return category.getId() == Category.DEFAULT_ID;
	}
}
