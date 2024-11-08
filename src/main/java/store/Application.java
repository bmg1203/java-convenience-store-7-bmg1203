package store;

import store.constants.ErrorMessage;
import store.controller.StoreController;

public class Application {
    public static void main(String[] args) {
        try {
            StoreController.runStore();
        } catch (Exception e) {
            System.out.println(ErrorMessage.SYSTEM_ERROR.getMessage());
        }
    }
}
