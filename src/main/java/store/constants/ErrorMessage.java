package store.constants;

public enum ErrorMessage {

    PRODUCT_BUY_FORM_ERROR("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NO_EXSIST_PRODUCT_ERROR("존재하지 않는 상품 입니다. 다시 입력해 주세요"),
    NOT_ENOUGH_QUANTITY_ERROR("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    NOT_POSITIVE_INPUT_ERROR("구입할 수량은 1 이상이어야 합니다. 다시 입력해 주세요."),
    ETC_INPUT_ERROR("잘못된 입력입니다. 다시 입력해주세요."),
    DUPLICATE_PRODUCT_INPUT_ERROR("같은 상품은 한 번에 여러개 구입합니다. 다시 입력해 주세요."),
    PRODUCT_ERROR("잘못된 재고입니다."),
    SYSTEM_ERROR("실행 오류 입니다.");

    private final String prefix = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = prefix + message;
    }

    public String getMessage() {
        return message;
    }
}
