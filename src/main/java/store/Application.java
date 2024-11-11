package store;

import store.constants.ErrorMessage;
import store.controller.StoreController;

public class Application {
    public static void main(String[] args) {
        final StoreController storeController = new StoreController();

        storeController.run();
    }
}
