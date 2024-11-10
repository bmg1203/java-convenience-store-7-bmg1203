package store;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.constants.ErrorMessage;
import store.constants.StringConstants;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static store.constants.StringConstants.NO;
import static store.constants.StringConstants.YES;

class ApplicationTest extends NsTest {

    @AfterEach
    void consoleClose() {
        Console.close();
    }

    @Test
    void 파일에_있는_상품_목록_출력() {
        assertSimpleTest(() -> {
            run("[물-1]", NO.getString(), NO.getString());
            assertThat(output()).contains(
                "- 콜라 1,000원 10개 탄산2+1",
                "- 콜라 1,000원 10개",
                "- 사이다 1,000원 8개 탄산2+1",
                "- 사이다 1,000원 7개",
                "- 오렌지주스 1,800원 9개 MD추천상품",
                "- 오렌지주스 1,800원 재고 없음",
                "- 탄산수 1,200원 5개 탄산2+1",
                "- 탄산수 1,200원 재고 없음",
                "- 물 500원 10개",
                "- 비타민워터 1,500원 6개",
                "- 감자칩 1,500원 5개 반짝할인",
                "- 감자칩 1,500원 5개",
                "- 초코바 1,200원 5개 MD추천상품",
                "- 초코바 1,200원 5개",
                "- 에너지바 2,000원 5개",
                "- 정식도시락 6,400원 8개",
                "- 컵라면 1,700원 1개 MD추천상품",
                "- 컵라면 1,700원 10개"
            );
        });
    }

    @Test
    void 여러_개의_일반_상품_구매() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", NO.getString(), NO.getString());
            assertThat(output().replaceAll("\\s", "")).contains("내실돈18,300");
        });
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용() {
        assertNowTest(() -> {
            run("[감자칩-2]", NO.getString(), NO.getString());
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("[컵라면-12]", NO.getString(), NO.getString());
            assertThat(output()).contains(ErrorMessage.NOT_ENOUGH_QUANTITY_ERROR.getMessage());
        });
    }

    //구매항목 관련 테스트
    @ParameterizedTest
    @ValueSource(strings = {"콜라-5", "[콜라-5],[콜라-5]", "[콜라,5]", "[한우-2]", "[콜라-100]"})
    void 구매항목_입력_예외_테스트(String input) {
        //when, then
        assertSimpleTest(() -> {
            try {
                runException(input, NO.getString(), NO.getString());
                assertThat(output()).contains(ErrorMessage.PRODUCT_BUY_FORM_ERROR.getMessage());
            } finally {
                Console.close();
            }
        });
    }

    //프로모션 관리 관련 테스트
    @Test
    void 프로모션_조건_충족_혜택_적용_테스트() {
        assertSimpleTest(() -> {
            runException("[콜라-3]", NO.getString(), NO.getString());
            assertThat(output().replaceAll("\\s", "")).contains("내실돈2,000");
        });
    }

    @Test
    void 프로모션_조건_미충족_혜택_미적용_테스트() {
        assertSimpleTest(() -> {
            runException("[콜라-1]", NO.getString(), NO.getString(), NO.getString());
            assertThat(output().replaceAll("\\s", "")).contains("내실돈1,000");
        });
    }

    @Test
    void 프로모션_기간_만료_혜택_미적용_테스트() {
        assertNowTest(() -> {
            run("[감자칩-2]", NO.getString(), NO.getString());
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,000");
        }, LocalDate.of(2024, 12, 31).atStartOfDay());
    }

    @Test
    void 프로모션_재고_부족시_일부_일반_재고_처리_테스트() {
        assertSimpleTest(() -> {
            run("[콜라-15]", YES.getString(), NO.getString(), NO.getString());  // 프로모션 10개, 일반 10개
            assertThat(output().replaceAll("\\s", ""))
                    .contains("내실돈12,000");  // 프로모션 9개, 6개 일반 가격
        });
    }

    @Test
    void 프로모션_조건_미충족시_개수_추가_허용_테스트() {
        assertSimpleTest(() -> {
            run("[사이다-2]", YES.getString(), NO.getString(), NO.getString());
            assertThat(output().replaceAll("\\s", ""))
                    .contains("내실돈2,000");  // 프로모션 적용됨
        });
    }

    @Test
    void 프로모션_조건_미충족시_추가_거부_테스트() {
        assertSimpleTest(() -> {
            run("[사이다-2]", NO.getString(), NO.getString(), NO.getString());
            assertThat(output().replaceAll("\\s", ""))
                    .contains("내실돈2,000");  // 정상가로 2개 구매
        });
    }

    //가격 및 할인 관련 테스트
    @Test
    void 멤버십_할인_적용_테스트() {
        assertSimpleTest(() -> {
            run("[비타민워터-2]", YES.getString(), NO.getString());
            assertThat(output().replaceAll("\\s", ""))
                    .contains("내실돈2,100");
        });
    }

    @Test
    void 프로모션_멤버십_할인_겹침_테스트() {
        assertSimpleTest(() -> {
            run("[감자칩-2]", YES.getString(), NO.getString());
            assertThat(output().replaceAll("\\s", ""))
                    .contains("내실돈1,500"); // 반짝할인과 멤버십 할인을 모두 적용한 가격 확인
        });
    }

    //추가 주문시 재고 관련 테스트
    @Test
    void 추가_주문시_재고_감소_출력() {
        assertSimpleTest(() -> {
            run("[콜라-3],[사이다-3],[오렌지주스-2]", NO.getString(), YES.getString(),
                    "[콜라-3]", NO.getString(), NO.getString());
            assertThat(output()).contains(
                    "- 콜라 1,000원 7개 탄산2+1",
                    "- 사이다 1,000원 5개 탄산2+1",
                    "- 오렌지주스 1,800원 7개 MD추천상품"
            );
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
