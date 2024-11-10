package store.controller;

import camp.nextstep.edu.missionutils.Console;
import java.io.IOException;
import java.util.Map;
import store.constants.ErrorMessage;
import store.constants.StringConstants;
import store.domain.Cart;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.Purchase;
import store.domain.TotalPrice;
import store.service.CartService;
import store.service.InitService;
import store.service.PromotionService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private Products products;
    private Promotions promotions;
    private Cart cart;
    private TotalPrice totalPrice;

    private final InitService initService = new InitService();
    private final PromotionService promotionService = new PromotionService();
    private final CartService cartService = new CartService();
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        String next = StringConstants.YES.getString();
        initStock();
        while(next.equals(StringConstants.YES.getString())) {
            start();
            checkProducts();
            result();
            products = cartService.productsReduce(products, cart);
            next = inputView.checkMorePurchase();
        }
        Console.close();
    }

    public void initStock() {
        try {
            products = initService.saveInitProducts();
            promotions = initService.saveInitPromotions();
        } catch (IOException e) {
            System.out.println(ErrorMessage.SYSTEM_ERROR.getMessage());
        }
    }

    public void start() {
        outputView.productsOutput(this.products);
        cart = inputView.readItem(products);
    }

    public void checkProducts() {
        for (Purchase purchase : cart.getItems()) {
            promotionService.setPromotion(purchase, products);
            if (!purchase.getPromotion().equals(StringConstants.NO_PROMOTION.getString())) {
                promotionService.promotionsAll(purchase, promotions, products, cart);
            }
        }
        addCart();
    }

    private void addCart() {
        promotionService.applyAddPurchaseToCart(cart);
    }

    public void result() {
        //영수증 출력
        Map<String, Purchase> items = cart.mergeItems(cart.getItems());
        totalPrice = new TotalPrice(cart.getItems(), products);
        totalPrice.setPromotionPrice(totalPrice.getPromotionSalePrice(cart.getItems(), promotions, products));
        String answer = inputView.checkMemberShipSale();
        if (answer.equals(StringConstants.YES.getString())) {
            totalPrice.setMemberShipPrice(totalPrice.getMembershipSalePrice(cart.getItems(), products));
        }
        outputView.printReceipt(cart, products, promotions, items, totalPrice);
    }
}
