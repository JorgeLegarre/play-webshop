package forms;


public class CategoryForm {
	private int id;
	private String name;

	public CategoryForm() {
	}

	public CategoryForm(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CategoryForm [id=" + id + ", name=" + name + "]";
	}

}
