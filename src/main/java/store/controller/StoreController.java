package store.controller;

import java.io.IOException;
import store.domain.Cart;
import store.domain.Products;
import store.domain.Promotions;
import store.service.InitService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private Products products;
    private Promotions promotions;
    private Cart cart;

    private final InitService initService;
    private final InputView inputView;
    private final OutputView outputView;

    public StoreController(InitService initService, InputView inputView, OutputView outputView) {
        this.initService = initService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() throws IOException {
        String next = "Y";
        while(!next.equals("N")) {
            start();
            checkProducts();
            result();
            next = inputView.checkMorePurchase();
        }
    }

    public void start() throws IOException {
        products = initService.saveInitProducts();
        promotions = initService.saveInitPromotions();
        cart = inputView.readItem();
        outputView.productsOutput(this.products);
    }

    public void checkProducts() {
        //각각 재고 확인
        //재고 부족시 다시 입력
        //프로모션 종류별 혜택 개수 맞는지 확인
        //프로모션 재고 부족시 일반 재고(정상가)로 구매건지 확인
        //나머진 그냥 구매로 굳히기(카트에 들어가는 것인 Purchase에도 프로모션 넣자)
    }

    public void result() {
        //영수증 출력
    }
}
