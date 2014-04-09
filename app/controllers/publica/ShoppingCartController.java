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

		ShoppingCart cart = shoppingCartManager.findById(getCurrentUser()
				.getId());

		if (cart == null) {
			cart = new ShoppingCart();
		}

		List<ShoppingCartDetail> detailsToRemove = new ArrayList<>();
		for (int i = 0; i < quantities.size(); i++) {
			int newQuantity = quantities.get(i);
			ShoppingCartDetail detail = cart.getShoppingCartDetails().get(i);

			detail.setQuantity(newQuantity);

			if (newQuantity <= 0) {
				detailsToRemove.add(detail);
			}

		}
		cart.getShoppingCartDetails().removeAll(detailsToRemove);

		if (cart.getShoppingCartDetails() == null) {
			cart.setShoppingCartDetails(new ArrayList<ShoppingCartDetail>());
		}

		session().put("items", cart.getTotalItems() + "");

		return redirect(routes.ShoppingCartController.showCart());
	}

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result addToCart() {

		int productId = getParamInt("productId");
		int quantity = getParamInt("quantity");

		ShoppingCartDetail detail = createDetail(getCurrentUser().getId(),
				productId, quantity);

		ShoppingCart cart = getCurrentUser().getShoppingCart();

		if (isNewShoppingCart(cart)) {
			cart = createShoppingCart(getCurrentUser(), detail);
			shoppingCartManager.save(cart);

		} else {
			if (detailExist(detail, cart)) {
				ShoppingCartDetail oldDetail = getOldDetail(detail, cart);
				oldDetail.setQuantity(oldDetail.getQuantity()
						+ detail.getQuantity());

			} else {
				cart.getShoppingCartDetails().add(detail);
			}
		}

		session().put("items", cart.getTotalItems() + "");

		return redirect(routes.ShoppingCartController.showCart());
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
