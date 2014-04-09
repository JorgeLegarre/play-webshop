package controllers.publica;

import java.util.ArrayList;
import java.util.List;

import manager.ProductManager;
import manager.ShoppingCartManager;
import manager.UserManager;
import models.ShoppingCart;
import models.ShoppingCartDetail;
import models.ShoppingCartDetail.Key;
import models.User;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import views.html.publica.cart.listCart;
import DAO.ProductDao;
import DAO.ShoppingCartDao;
import DAO.UserDao;
import controllers.GeneralController;

public class ShoppingCartController extends GeneralController {
	private final static ShoppingCartManager shoppingCartManager = new ShoppingCartManager(
			new ShoppingCartDao());
	private final static UserManager userManager = new UserManager(
			new UserDao());
	private final static ProductManager productManager = new ProductManager(
			new ProductDao());

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result showCart() {
		ShoppingCart cart = shoppingCartManager.findById(getCurrentUser()
				.getId());

		if (cart == null) {
			cart = new ShoppingCart();
		}

		if (cart.getShoppingCartDetails() == null) {
			cart.setShoppingCartDetails(new ArrayList<ShoppingCartDetail>());
		}
		return ok(listCart.render(cart));
	}

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result updateCart() {
		List<Integer> quantities = getListInt("quantity");

		ShoppingCart cart = getCart();

		updateQuantities(cart, quantities);

		removeDeletedProducts(cart);

		session().put("items", cart.getTotalItems() + "");

		return redirect(routes.ShoppingCartController.showCart());
	}

	private static void removeDeletedProducts(ShoppingCart cart) {
		List<ShoppingCartDetail> detailsToRemove = new ArrayList<>();
		for (ShoppingCartDetail detail : cart.getShoppingCartDetails()) {
			if (detail.getQuantity() <= 0) {
				detailsToRemove.add(detail);
			}

		}
		cart.getShoppingCartDetails().removeAll(detailsToRemove);

		if (cart.getShoppingCartDetails() == null) {
			cart.setShoppingCartDetails(new ArrayList<ShoppingCartDetail>());
		}
	}

	private static void updateQuantities(ShoppingCart cart,
			List<Integer> quantities) {
		for (int i = 0; i < quantities.size(); i++) {
			int newQuantity = quantities.get(i);
			ShoppingCartDetail detail = cart.getShoppingCartDetails().get(i);

			detail.setQuantity(newQuantity);

		}

	}

	private static ShoppingCart getCart() {
		ShoppingCart cart = getCurrentUser().getShoppingCart();

		if (cart == null) {
			cart = new ShoppingCart();
		}

		return cart;
	}

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result addToCart() {
		ShoppingCartDetail detail = getDetail();

		ShoppingCart cart = addDetailToCart(detail, getCurrentUser()
				.getShoppingCart());

		session().put("items", cart.getTotalItems() + "");

		return redirect(routes.ShoppingCartController.showCart());
	}

	private static ShoppingCartDetail getDetail() {
		int productId = getParamInt("productId");
		int quantity = getParamInt("quantity");

		return createDetail(getCurrentUser().getId(), productId, quantity);
	}

	private static ShoppingCart addDetailToCart(ShoppingCartDetail detail,
			ShoppingCart cart) {

		if (isNewShoppingCart(cart)) {
			cart = createShoppingCart(getCurrentUser(), detail);
			shoppingCartManager.save(cart);

		} else {
			if (detailExist(detail, cart)) {
				addQuantityToOldDetail(detail, cart);

			} else {
				cart.getShoppingCartDetails().add(detail);
			}
		}

		return cart;
	}

	private static void addQuantityToOldDetail(ShoppingCartDetail detail,
			ShoppingCart cart) {
		ShoppingCartDetail oldDetail = getOldDetail(detail, cart);
		oldDetail.setQuantity(oldDetail.getQuantity() + detail.getQuantity());

	}

	private static ShoppingCartDetail getOldDetail(ShoppingCartDetail detail,
			ShoppingCart cart) {
		return cart.getShoppingCartDetails().get(
				cart.getShoppingCartDetails().indexOf(detail));
	}

	private static boolean detailExist(ShoppingCartDetail detail,
			ShoppingCart cart) {
		return cart.getShoppingCartDetails().indexOf(detail) >= 0;
	}

	private static ShoppingCart createShoppingCart(User currentUser,
			ShoppingCartDetail detail) {

		ShoppingCart cart = new ShoppingCart();
		cart.setUser(getCurrentUser());
		cart.getShoppingCartDetails().add(detail);

		return cart;
	}

	private static ShoppingCartDetail createDetail(int id, int productId,
			int quantity) {
		ShoppingCartDetail detail = new ShoppingCartDetail();

		Key key = new Key();
		key.setProductId(productId);
		key.setUserId(getCurrentUser().getId());

		detail.setId(key);
		detail.setProduct(productManager.findById(productId));
		detail.setQuantity(quantity);

		return detail;
	}

	private static boolean isNewShoppingCart(ShoppingCart cart) {
		return cart == null;
	}

	private static User getCurrentUser() {
		String email = session().get("username");
		return userManager.findByEmail(email);
	}
}
