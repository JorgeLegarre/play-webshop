package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public final class ShoppingCartDetail {
	@Embeddable
	public static class Key implements Serializable {

		private static final long serialVersionUID = 20140318;

		private int shoppingCartId;
		private int productId;

		public Key() {
		}

		public Key(int shoppingCartId, int productId) {
			this.shoppingCartId = shoppingCartId;
			this.productId = productId;
		}

		public int getShoppingCartId() {
			return shoppingCartId;
		}

		public void setShoppingCartId(int userId) {
			this.shoppingCartId = userId;
		}

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + productId;
			result = prime * result + shoppingCartId;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (productId != other.productId)
				return false;
			if (shoppingCartId != other.shoppingCartId)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Key [shoppingCartId=" + shoppingCartId + ", productId="
					+ productId + "]";
		}

	}

	@EmbeddedId
	private Key id;

	@ManyToOne
	@MapsId("productId")
	private Product product;

	@Column(nullable = false)
	private int quantity;

	public ShoppingCartDetail() {
	}

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getSubTotal() {
		return quantity * product.getCost();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShoppingCartDetail other = (ShoppingCartDetail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ShoppingCartDetail [id=" + id + ", quantity=" + quantity + "]";
	}

}
