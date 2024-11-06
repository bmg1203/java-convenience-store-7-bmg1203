package store.constants;

public enum OutputPrompts {

    PRODUCTS_HAVE_PROMOTION("- %s %s원 %d개 %s\n"),
    PRODUCTS_NO_PROMOTION("- %s %s원 %d개\n"),
    RECEIPT_HEADER("==============W 편의점================\n"
                 + "상품명\t\t수량\t금액\n"),
    RECEIPT_PRODUCTS("%s\t\t%d\t%s\n"),
    RECEIPT_PROMOTION_HEADER("=============증\t정===============\n"),
    RECEIPT_PROMOTION_PRODUCTS("%s\t\t%d\n"),
    RECEIPT_TOTAL_PRICE_HEADER("====================================\n"),
    RECEIPT_TOTAL_PRICE("총구매액\t\t%d\t%s\n"),
    RECEIPT_PROMOTION_DISCOUNT("행사할인\t\t\t-%s\n"),
    RECEIPT_MEMBERSHIP_DISCOUNT("멤버십할인\t\t\t-%s\n"),
    RECEIPT_FINAL_PRICE("내실돈\t\t\t %s\n");

    private final String prompts;

    OutputPrompts(String prompts) {
        this.prompts = prompts;
    }

    public String getPrompts() {
        return prompts;
    }
}
