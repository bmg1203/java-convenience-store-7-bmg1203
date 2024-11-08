package store.constants;

public enum OutputPrompts {
    WELCOME_MESSAGE("안녕하세요. W편의점입니다.\n"
            + "현재 보유하고 있는 상품입니다.\n"),

    PRODUCTS_HAVE_PROMOTION("- %s %s원 %s개 %s"),
    PRODUCTS_NO_PROMOTION("- %s %s원 %s개"),
    RECEIPT_HEADER("\n==============W 편의점================\n"
                 + "상품명\t\t수량\t금액"),
    RECEIPT_PRODUCTS("%s\t\t%d\t%s\n"),
    RECEIPT_PROMOTION_HEADER("=============증\t정==============="),
    RECEIPT_PROMOTION_PRODUCTS("%s\t\t%d\n"),
    RECEIPT_TOTAL_PRICE_HEADER("====================================\n"),
    RECEIPT_TOTAL_PRICE("총구매액\t\t%d\t%s\n"),
    RECEIPT_PROMOTION_DISCOUNT("행사할인\t\t\t-%s\n"),
    RECEIPT_MEMBERSHIP_DISCOUNT("멤버십할인\t\t\t-%s\n"),
    RECEIPT_FINAL_PRICE("내실돈\t\t\t %s\n\n");

    private final String prompts;

    OutputPrompts(String prompts) {
        this.prompts = prompts;
    }

    public String getPrompts() {
        return prompts;
    }
}
