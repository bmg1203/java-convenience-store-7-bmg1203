package store.constants;

public enum InputPrompts {

    START_PROMPTS("안녕하세요. W편의점입니다. \n현재 보유하고 있는 상품입니다. \n"),
    ENTER_PURCHASE_PRODUCTS("구매하실 상품명과 수량을 입력해주세요. (예: [사이다-2],[감자칩-1])"),
    PROMOTION_QUANTITY_ADD("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n"),
    NOT_ENOUGH_PROMOTION_QUANTITY("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n"),
    MEMBERSHIP_DISCOUNT("멤버십 할인을 받으시겠습니까? (Y/N)"),
    MORE_PURCHASE("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");

    private final String prompt;

    InputPrompts(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }
}
