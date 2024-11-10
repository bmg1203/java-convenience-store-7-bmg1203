package store.controller;

import camp.nextstep.edu.missionutils.Console;
import java.io.IOException;
import java.util.Map;
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

    private final InitService initService;
    private final PromotionService promotionService;
    private final CartService cartService;
    private final InputView inputView;
    private final OutputView outputView;

    public StoreController(InitService initService, PromotionService promotionService, CartService cartService,
                           InputView inputView, OutputView outputView) {
        this.initService = initService;
        this.promotionService = promotionService;
        this.cartService = cartService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    // 정적 메소드로 전체 실행 흐름을 관리
    public static void runStore() throws IOException {
        InitService initService = new InitService();
        PromotionService promotionService = new PromotionService(new InputView());
        CartService cartService = new CartService();
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        StoreController storeController = new StoreController(initService, promotionService, cartService, inputView, outputView);
        storeController.run();
    }

    public void run() throws IOException {
        String next = "Y";
        initStock();
        while(next.equals("Y")) {
            start();
            checkProducts();
            result();
            products = cartService.productsReduce(products, cart);
            next = inputView.checkMorePurchase();
        }
        Console.close();
    }

    public void initStock() throws IOException {
        products = initService.saveInitProducts();
        promotions = initService.saveInitPromotions();
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
