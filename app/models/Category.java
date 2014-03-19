package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public final class Category {
	public static final int DEFAULT_ID = -1;

	@Id
	private final int id;
	private final String name;
	private final int staff_responsible;

	public Category(int id, String name, int staff_responsible) {
		this.id = id;
		this.name = name;
		this.staff_responsible = staff_responsible;
	}

	public Category(String name, int staff_responsible) {
		this(DEFAULT_ID, name, staff_responsible);
	}

	public Category(int id, Category other) {
		this(id, other.name, other.staff_responsible);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getStaff_responsible() {
		return staff_responsible;
	}

	@Override
	public int hashCode() {
		return 31 * id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Category) {
			Category other = (Category) obj;
			return id == other.id;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("Id: %s, Name: %s, Straff_responsible: %s", id,
				name, staff_responsible);
	}

}
