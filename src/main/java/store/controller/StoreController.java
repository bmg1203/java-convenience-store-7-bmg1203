package store.controller;

import java.io.IOException;
import java.util.Map;
import store.domain.Cart;
import store.domain.Products;
import store.domain.Promotion;
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
    private final InputView inputView;
    private final OutputView outputView;

    public StoreController(InitService initService, PromotionService promotionService,
                           InputView inputView, OutputView outputView) {
        this.initService = initService;
        this.promotionService = promotionService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    // 정적 메소드로 전체 실행 흐름을 관리
    public static void runStore() throws IOException {
        InitService initService = new InitService();
        PromotionService promotionService = new PromotionService(new InputView());
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        StoreController storeController = new StoreController(initService, promotionService, inputView, outputView);
        storeController.run();
    }

    public void run() throws IOException {
        String next = "Y";
        while(next.equals("Y")) {
            start();
            checkProducts();
            result();
            next = inputView.checkMorePurchase();
        }
    }

    public void start() throws IOException {
        products = initService.saveInitProducts();
        promotions = initService.saveInitPromotions();
        cart = inputView.readItem(products);
        outputView.productsOutput(this.products);
    }

    public void checkProducts() {
        for (Purchase purchase : cart.getItems()) {
            Promotion promotion = promotions.getPromotions().get(purchase.getName());
            purchase.setPromotion(promotion.getName());
            if (!purchase.getPromotion().equals("null")) {
                promotionService.promotionsAll(purchase, promotions, products, cart);
            }
        }
    }

    public void result() {
        //영수증 출력
        Map<String, Purchase> items = cart.mergeItems(cart.getItems());
        totalPrice = new TotalPrice(cart.getItems(), products);
        totalPrice.setPromotionPrice(totalPrice.getPromotionSalePrice(cart.getItems(), promotions, products));
        String answer = inputView.checkMemberShipSale();
        if (answer.equals("Y")) {
            totalPrice.setMemberShipPrice(totalPrice.getMembershipSalePrice(cart.getItems(), promotions, products));
        }
        outputView.printReceipt(cart, products, promotions, items, totalPrice);
    }
}
