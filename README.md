🏪 편의점
=========

💡 목표
-------
### 학습 목표
* 관련 함수를 묶어 클래스를 만들고, 객체들이 협력하여 하나의 큰 기능을 수행하도록 한다.
* 클래스와 함수에 대한 단위 테스트를 통해 의도한 대로 정확하게 작동하는 영역을 확보한다.
* [3주 차 공통 피드백](https://docs.google.com/document/d/1MsfVKpgiDyhq6ArbTwsf9EEqDBD85vyt9Oj25i0zkEM/edit?tab=t.0)을 최대한 반영한다.
* [비공개 저장소 과제 진행 가이드](https://docs.google.com/document/d/1cmg0VpPkuvdaetxwp4hnyyFC_G-1f2Gr8nIDYIWcKC8/preview?tab=t.0#heading=h.1x8npe961lrb)를 참고하여 새로운 방식으로 과제 제출물을 제출한다.

### 회고
아래 질문에 대한 최종 회고를 진행하고 소감에 구체적인 결과를 작성한다. 소감은 텍스트로 작성해야 하며 외부 링크는 허용하지 않는다.
* 지원서나 중간 회고에서 현실적인 목표를 설정하고 이를 달성했다고 생각하나요? 그 이유는 무엇인가요?
* 중간 회고에서 조정한 목표가 실제 목표 달성에 도움이 되었나요? 목표를 달성하는 데 어떤 점이 효과적이었다고 생각하나요?
* 각 미션의 목표를 달성하기 위해 세운 계획을 잘 이행했나요? 그 과정에서 어떤 전략이 효과가 있었나요?
* 몰입하고 함께 성장하는 과정을 통해 인상 깊었던 경험이나 변화가 있었나요?

*** 
️💡 개요
------
### 편의점 프로그램
편의점에서 물건을 구매하는 프로그램입니다. 재고와 프로모션 등을 적용해 물건을 구매하고 멤버쉽으로도 할인을 받을 수 있습니다. 구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고 안내하는 결제 시스템 입니다. 

### 주요 기능
* 재고 출력
  * 현재 편의점이 가지고 있는 재품과 수량 프로모션을 출력합니다. 
* 구입할 물품 및 수량 입력
  * 구입할 물품과 수량을 입력합니다. 
  * 입력이 잘못될 경우 다시 입력 받습니다. 
* 재고와 프로모션 등의 할인을 적용 후 결제 금액 출력
  * 구입할 물품의 재고를 확인합니다. 
  * 프로모션이 있는지 체크합니다. 
  * 멤버쉽 할인이 있는지 확인합니다. 
***

ℹ️ 과제 진행 요구사항
-------

* 미션은 [편의점](https://github.com/woowacourse-precourse/java-convenience-store-7) 저장소를 생성하는 것으로 시작한다.
* **기능을 구현하기 전 `README.md`에 구현할 기능 목록을 정리**해 추가한다.
* Git의 커밋 단위는 앞 단계에서 README.md에 정리한 기능 목록 단위로 추가한다.
    * [AngularJS Git Commit Message Conventions](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)을 참고해 커밋 메시지를 작성한다.
* 자세한 과제 진행 방법은 프리코스 진행 가이드 문서를 참고한다.

📍 기능 요구 사항
-------

구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고 안내하는 결제 시스템을 구현한다.

* 사용자가 입력한 상품의 가격과 수량을 기반으로 최종 결제 금액을 계산한다.
  * 총구매액은 상품별 가격과 수량을 곱하여 계산하며, 프로모션 및 멤버십 할인 정책을 반영하여 최종 결제 금액을 산출한다.
* 구매 내역과 산출한 금액 정보를 영수증으로 출력한다.
* 영수증 출력 후 추가 구매를 진행할지 또는 종료할지를 선택할 수 있다.
* 사용자가 잘못된 값을 입력할 경우 `IllegalArgumentException`을 발생시키고, "[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.
    * `Exception`이 아닌 `IllegalArgumentException`, `IllegalStateException` 등과 같은 명확한 유형을 처리한다.

### 재고 관리
* 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
* 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감하여 수량을 관리한다.
* 재고를 차감함으로써 시스템은 최신 재고 상태를 유지하며, 다음 고객이 구매할 때 정확한 재고 정보를 제공한다.

### 프로모션 할인
* 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용한다.
* 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태로 진행된다.
* 1+1 또는 2+1 프로모션이 각각 지정된 상품에 적용되며, 동일 상품에 여러 프로모션이 적용되지 않는다.
* 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.
* 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.
* 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
* 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.

### 멤버십 할인
* 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
* 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
* 멤버십 할인의 최대 한도는 8,000원이다.

### 영수증 출력
* 영수증은 고객의 구매 내역과 할인을 요약하여 출력한다.
* 영수증 항목은 아래와 같다.
  * 구매 상품 내역: 구매한 상품명, 수량, 가격
  * 증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록
  * 금액 정보
    * 총구매액: 구매한 상품의 총 수량과 총 금액
    * 행사할인: 프로모션에 의해 할인된 금액
    * 멤버십할인: 멤버십에 의해 추가로 할인된 금액
    * 내실돈: 최종 결제 금액
* 영수증의 구성 요소를 보기 좋게 정렬하여 고객이 쉽게 금액과 수량을 확인할 수 있게 한다.

## 입출력 요구 사항

### 입력

* 구현에 필요한 상품 목록과 행사 목록을 파일 입출력을 통해 불러온다.
  * `src/main/resources/products.md`과 `src/main/resources/promotions.md` 파일을 이용한다.
  * 두 파일 모두 내용의 형식을 유지한다면 값은 수정할 수 있다. 
* 구매할 상품과 수량을 입력 받는다. 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분한다.
```
    [콜라-10],[사이다-3]
```

* 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 입력받는다.
  * Y: 증정 받을 수 있는 상품을 추가한다.
  * N: 증정 받을 수 있는 상품을 추가하지 않는다.
```
    Y
```

* 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부를 입력받는다.
  * Y: 일부 수량에 대해 정가로 결제한다.
  * N: 정가로 결제해야하는 수량만큼 제외한 후 결제를 진행한다.
```
    Y
```

* 멤버십 할인 적용 여부를 입력 받는다.
  * Y: 멤버십 할인을 적용한다.
  * N: 멤버십 할인을 적용하지 않는다.
```
    Y
```

* 추가 구매 여부를 입력 받는다.
  * Y: 재고가 업데이트된 상품 목록을 확인 후 추가로 구매를 진행한다.
  * N: 구매를 종료한다.
```
    Y
```

### 출력
* 환영 인사와 함께 상품명, 가격, 프로모션 이름, 재고를 안내한다. 만약 재고가 0개라면 `재고 없음`을 출력한다.
```
    안녕하세요. W편의점입니다.
    현재 보유하고 있는 상품입니다.
    
    - 콜라 1,000원 10개 탄산2+1
    - 콜라 1,000원 10개
    - 사이다 1,000원 8개 탄산2+1
    - 사이다 1,000원 7개
    - 오렌지주스 1,800원 9개 MD추천상품
    - 오렌지주스 1,800원 재고 없음
    - 탄산수 1,200원 5개 탄산2+1
    - 탄산수 1,200원 재고 없음
    - 물 500원 10개
    - 비타민워터 1,500원 6개
    - 감자칩 1,500원 5개 반짝할인
    - 감자칩 1,500원 5개
    - 초코바 1,200원 5개 MD추천상품
    - 초코바 1,200원 5개
    - 에너지바 2,000원 5개
    - 정식도시락 6,400원 8개
    - 컵라면 1,700원 1개 MD추천상품
    - 컵라면 1,700원 10개
    
    구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
```

* 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량만큼 가져오지 않았을 경우, 혜택에 대한 안내 메시지를 출력한다.
```
    현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
```

* 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부에 대한 안내 메시지를 출력한다.
```
    현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
```

* 멤버십 할인 적용 여부를 확인하기 위해 안내 문구를 출력한다.
```
    멤버십 할인을 받으시겠습니까? (Y/N)
```

* 구매 상품 내역, 증정 상품 내역, 금액 정보를 출력한다.
```
    ===========W 편의점=============
    상품명		수량	금액
    콜라		3 	3,000
    에너지바 		5 	10,000
    ===========증	정=============
    콜라		1
    ==============================
    총구매액		8	13,000
    행사할인			-1,000
    멤버십할인			-3,000
    내실돈			 9,000
```

* 추가 구매 여부를 확인하기 위해 안내 문구를 출력한다.
```
    감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
```

* 사용자가 잘못된 값을 입력했을 때, "[ERROR]"로 시작하는 오류 메시지와 함께 상황에 맞는 안내를 출력한다.
  * 구매할 상품과 수량 형식이 올바르지 않은 경우: `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`
  * 존재하지 않는 상품을 입력한 경우: `[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.`
  * 구매 수량이 재고 수량을 초과한 경우: `[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.`
  * 기타 잘못된 입력의 경우: `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`

### 실행 결과 예시
```
    안녕하세요. W편의점입니다.
    현재 보유하고 있는 상품입니다.
    
    - 콜라 1,000원 10개 탄산2+1
    - 콜라 1,000원 10개
    - 사이다 1,000원 8개 탄산2+1
    - 사이다 1,000원 7개
    - 오렌지주스 1,800원 9개 MD추천상품
    - 오렌지주스 1,800원 재고 없음
    - 탄산수 1,200원 5개 탄산2+1
    - 탄산수 1,200원 재고 없음
    - 물 500원 10개
    - 비타민워터 1,500원 6개
    - 감자칩 1,500원 5개 반짝할인
    - 감자칩 1,500원 5개
    - 초코바 1,200원 5개 MD추천상품
    - 초코바 1,200원 5개
    - 에너지바 2,000원 5개
    - 정식도시락 6,400원 8개
    - 컵라면 1,700원 1개 MD추천상품
    - 컵라면 1,700원 10개
    
    구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
    [콜라-3],[에너지바-5]
    
    멤버십 할인을 받으시겠습니까? (Y/N)
    Y 
    
    ===========W 편의점=============
    상품명		수량	금액
    콜라		3 	3,000
    에너지바 		5 	10,000
    ===========증	정=============
    콜라		1
    ==============================
    총구매액		8	13,000
    행사할인			-1,000
    멤버십할인			-3,000
    내실돈			 9,000
    
    감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
    Y
    
    안녕하세요. W편의점입니다.
    현재 보유하고 있는 상품입니다.
    
    - 콜라 1,000원 7개 탄산2+1
    - 콜라 1,000원 10개
    - 사이다 1,000원 8개 탄산2+1
    - 사이다 1,000원 7개
    - 오렌지주스 1,800원 9개 MD추천상품
    - 오렌지주스 1,800원 재고 없음
    - 탄산수 1,200원 5개 탄산2+1
    - 탄산수 1,200원 재고 없음
    - 물 500원 10개
    - 비타민워터 1,500원 6개
    - 감자칩 1,500원 5개 반짝할인
    - 감자칩 1,500원 5개
    - 초코바 1,200원 5개 MD추천상품
    - 초코바 1,200원 5개
    - 에너지바 2,000원 재고 없음
    - 정식도시락 6,400원 8개
    - 컵라면 1,700원 1개 MD추천상품
    - 컵라면 1,700원 10개
    
    구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
    [콜라-10]
    
    현재 콜라 4개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
    Y
    
    멤버십 할인을 받으시겠습니까? (Y/N)
    N
    
    ===========W 편의점=============
    상품명		수량	금액
    콜라		10 	10,000
    ===========증	정=============
    콜라		2
    ==============================
    총구매액		10	10,000
    행사할인			-2,000
    멤버십할인			-0
    내실돈			 8,000
    
    감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
    Y
    
    안녕하세요. W편의점입니다.
    현재 보유하고 있는 상품입니다.
    
    - 콜라 1,000원 재고 없음 탄산2+1
    - 콜라 1,000원 7개
    - 사이다 1,000원 8개 탄산2+1
    - 사이다 1,000원 7개
    - 오렌지주스 1,800원 9개 MD추천상품
    - 오렌지주스 1,800원 재고 없음
    - 탄산수 1,200원 5개 탄산2+1
    - 탄산수 1,200원 재고 없음
    - 물 500원 10개
    - 비타민워터 1,500원 6개
    - 감자칩 1,500원 5개 반짝할인
    - 감자칩 1,500원 5개
    - 초코바 1,200원 5개 MD추천상품
    - 초코바 1,200원 5개
    - 에너지바 2,000원 재고 없음
    - 정식도시락 6,400원 8개
    - 컵라면 1,700원 1개 MD추천상품
    - 컵라면 1,700원 10개
    
    구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
    [오렌지주스-1]
    
    현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
    Y
    
    멤버십 할인을 받으시겠습니까? (Y/N)
    Y
    
    ===========W 편의점=============
    상품명		수량	금액
    오렌지주스		2 	3,600
    ===========증	정=============
    오렌지주스		1
    ==============================
    총구매액		2	3,600
    행사할인			-1,800
    멤버십할인			-0
    내실돈			 1,800
    
    감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
    N
```

💻 프로그래밍 요구 사항 1
-------

* JDK 21 버전에서 실행 가능해야 한다.
* 프로그램 실행의 시작점은 `Application`의 `main()`이다.
* `build.gradle` 파일은 변경할 수 없으며, **제공된 라이브러리 이외의 외부 라이브러리는 사용하지 않는다.**
* 프로그램 종료 시 `System.exit()`를 호출하지 않는다.
* 프로그래밍 요구 사항에서 달리 명시하지 않는 한 파일, 패키지 등의 이름을 바꾸거나 이동하지 않는다.
* 자바 코드 컨벤션을 지키면서 프로그래밍한다.
    * 기본적으로 [Java Style Guide](https://github.com/woowacourse/woowacourse-docs/tree/main/styleguide/java)를 원칙으로 한다.

💻 프로그래밍 요구 사항 2
-------
* indent(인덴트, 들여쓰기) depth를 3이 넘지 않도록 구현한다. 2까지만 허용한다.
    * 예를 들어 while문 안에 if문이 있으면 들여쓰기는 2이다.
    * 힌트: indent(인덴트, 들여쓰기) depth를 줄이는 좋은 방법은 함수(또는 메서드)를 분리하면 된다.
* 3항 연산자를 쓰지 않는다.
* 함수(또는 메서드)가 한 가지 일만 하도록 최대한 작게 만들어라.
* JUnit 5와 AssertJ를 이용하여 정리한 기능 목록이 정상적으로 작동하는지 테스트 코드로 확인한다.
    * 테스트 도구 사용법이 익숙하지 않다면 아래 문서를 참고하여 학습한 후 테스트를 구현한다.
        * [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
        * [AssertJ User Guide](https://assertj.github.io/doc/)
        * [AssertJ Exception Assertions](https://www.baeldung.com/assertj-exception-assertion)
        * [Guide to JUnit 5 Parameterized Tests](https://www.baeldung.com/parameterized-tests-junit-5)

💻 프로그래밍 요구 사항 3
-------

* else 예약어를 쓰지 않는다.
    * else를 쓰지 말라고 하니 switch/case로 구현하는 경우가 있는데 switch/case도 허용하지 않는다.
    * 힌트: if 조건절에서 값을 return하는 방식으로 구현하면 else를 사용하지 않아도 된다.
* Java Enum을 적용하여 프로그램을 구현한다.
* 구현한 기능에 대한 단위 테스트를 작성한다. 단, UI(System.out, System.in, Scanner) 로직은 제외한다.

💻 프로그래밍 요구 사항 4
-------

* 함수(또는 메서드)의 길이가 10라인을 넘어가지 않도록 구현한다.
  * 함수(또는 메서드)가 한 가지 일만 잘 하도록 구현한다.
  * main()도 포함된다. 
* 입출력을 담당하는 클래스를 별도로 구현한다.
  * 아래 `InputView`, `OutputView` 클래스를 참고하여 입출력 클래스를 구현한다.
  * 클래스 이름, 메소드 반환 유형, 시그니처 등은 자유롭게 수정할 수 있다.

```
    public class InputView {
        public String readItem() {
            System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
            String input = Console.readLine();    
            // ...
        }
        // ...
    }
```
```
    public class OutputView {
        public void printProducts() {
            System.out.println("- 콜라 1,000원 10개 탄산2+1");
            // ...
        }
        // ...
    }
```

## 라이브러리
* `camp.nextstep.edu.missionutils`에서 제공하는 `DateTimes` 및 `Console` API를 사용하여 구현해야 한다.
    * 현재 날짜와 시간을 가져오려면 `camp.nextstep.edu.missionutils.DateTimes`의 `now()`를 활용한다.
    * 사용자가 입력하는 값은 `camp.nextstep.edu.missionutils.Console`의 `readLine()`을 활용한다.

***
📍 과제 목표
=======
~~~
* 요구 사항에서 놓친 것이 없도록 꼼꼼히 체크하기 
* 단위테스트 -> 통합테스트로 간단한 것부터 테스트 하기
* 객체지향을 활용하기 위해서 클래스 사용하기
* else문 없이 if문만을 이용해 조건 처리하기
* Enum을 활용해 상수를 하드코딩하지 않기
* 예외를 특정해서 사용하기
* 메소드를 하나의 기능만 하도록 분리하기
* mvc 사용해보기
~~~

📍 기능 목록 체크리스트
=======

> **기능 목록**

### ☀️ 입력
* `camp.nextstep.edu.missionutils`에서 제공하는 `Console` API를 사용

#### 구매할 상품과 수량
* 구매할 상품과 수량을 입력 받는다. 
* 상품명, 수량은 하이픈(-)으로, 개발 상품은 대괄호([])로 묶어 쉼표(,)로 구분한다. 

#### 프로모션 적용이 가능한 수량보다 적은 경우 수량 추가 여부
* Y: 증정 받을 수 있는 상품을 추가한다.
* N: 증정 받을 수 있는 상품을 추가하지 않는다.

#### 프로모션 재고가 부족하여 혜택 없이 결제해야하는 경우 결제 여부
* Y: 일부 수량에 대해 정가로 결제한다.
* N: 정가로 결제해야하는 수량만큼 제외한 후 결제를 진행한다.

#### 멤버십 할인 적용 여부
* Y: 멤버십 할인을 적용한다.
* N: 멤버십 할인을 적용하지 않는다.

#### 추가 구매 여부
* Y: 재고가 업데이트된 상품 목록을 확인 후 추가로 구매를 진행한다.
* N: 구매를 종료한다.

### ✨ 입력값 검사
> 사용자가 잘못된 값을 입력할 경우 예외를 발생시키고 그 부분부터 다시 입력받는다.
> 예외 발생시 "[ERROR]"로 시작하는 에러 메시지를 출력한다.

#### 구매할 상품과 수량
* 대괄호([]), 하이픈(-)과 쉼표(,) 외의 특수문자가 있는 경우
* 재고에 구매할 상품이 없는 경우
* 수량만큼 재고가 없는 경우
* 입력이 없거나 공란 경우

#### 여부를 묻는 입력
* Y 또는 N이 아닌 입력인 경우
* 입력이 없거나 공란 경우

### ✨ 재고 관리

#### 재고 수량 확인 기능
* 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다. 
  * 재고가 없는 경우 다시 입력 받는다. 

#### 재고 수량 차감 기능
* 상품을 구매할 때마다, 결제된 수량만큼 재고에서 차감한다.

### ✨ 프로모션

#### 프로모션 기간 확인 기능
* 적용되는 프로모션의 기간이 현재 시점에서 기간 내인지 확인한다. 

#### 프로모션 재고 확인 기능
* 프로모션이 있는 경우 프로모션 재고를 확인한다. 
* 부족한 경우 일반 재고를 사용한다. 
  * 재고가 없는 경우 다시 입력 받는다.

#### 프로모션 적용 개수 확인
* 프로모션에 해당하는 개수를 구매하는지 확인합니다. 
  * 추가 구매 여부를 묻는다. 

#### 프로모션 별 증정 기능
* 1+1
* 2+1
* 반짝할인
* MD추천상품

### ✨ 멤버쉽 할인 기능
* 프로모션 미적용 금액의 30%를 할인받는다. 
  * 프로모션 적용 후 남은 금액에 대해 멤버쉽 할인을 적용한다.
  * 프로모션이 적용되지 않는 상품 가격에 한해 할인이 적용된다. 
* 멤버십 할인의 최대 한도는 8,000원이다. 

### 💭 영수증 출력
> 출력 예시의 구조를 적용한다. 
* 구매 내역과 할인을 요약하여 출력한다. 

#### 구매 상품 내역
* 구매한 상품명
* 수량
* 가격

#### 증정 상품 내역
* 프로모션에 따라 무료로 제공된 증정 상품의 목록

#### 금액 정보
* 총 구매액
  * 구매한 상품의 총 수량과 총 금액
* 행사할인
  * 프로모션에 의해 할인된 금액
* 멤버십할인
  * 멤버십에 의해 추가로 할인된 금액
* 내실돈
  * 최종 결제 금액

공통 피드백
===
[공통 피드백](https://docs.google.com/document/d/1MsfVKpgiDyhq6ArbTwsf9EEqDBD85vyt9Oj25i0zkEM/edit?tab=t.0)
* 예외 상황에 대해 고민한다. 
  * 코드 작성 시 예상되는 예외를 미리 고려하여, 프로그램이 잘못된 결과를 내지 않도록 한다. 
* 비지니스 로직과 UI 로직은 분리한다. 
  * 비지니스 로직 : 데이터 처리 및 도메인 규칙 담당
  * UI 로직 : 화면에 데이터를 표시하거나 입력을 받는 역할
* 연관성 있는 상수는 static final 대신 enum 활용
  * 로또의 당첨 등수의 경우 RANK 열거형은 각 등수마다 일치하는 숫자 개수와 상금을 정의할 수 있다.
* final 키워드를 사용해 값의 변경을 막는다.
  * 값의 변경을 방지하기 위해서 `final` 키워드를 사용하는 것이 좋다. 
* 객체의 상태 접근을 제한한다. 
  * 객체의 상태 접근을 제한하는 것은 캡슐화의 중요한 원칙이다. 
  * private로 설정하면 객체 내에서만 관리될 수 있다. 
* 객체는 객체답게 사용한다. 
  * 로직 구현없이 getter만 가지거나 하지 않는다. 
  * 객체 내에서 get으로 가져오지 않는다. 데이터를 가진 객체가 일하도록 한다. 
* 필드(인스턴스 변수)의 수를 줄이기 위해 노력한다. 
  * 로또의 경우 result와 수익률, 이익 이렇게 나누지 않아도 result만으로 계산 가능
* 테스트
  * 예외도 테스트한다. 
  * 테스트를 위한 코드는 구현코드에서 분리되어야 한다. 
    * 테스트를 위해 접근 제어자를 바꾸거나 테스트 코드에서만 사용되는 경우를 주의하자
  * 단위 테스트하기 어려운 코드를 단위 테스트하기
    * 테스트하기 어려운 코드를 쉽게 만드는 방법
      * 어려운 의존성을 외부에서 주입하거나 분리하여 테스트 한다. 
  * private 함수 테스트는 클래스(객체) 분리를 고려한다. 
    * 중요한 역할일 경우에는 클래스 분리는 고려할 필요가 있다. 
  * 테스트 코드도 코드다. 
    * 중복을 줄이고 유지보수성을 높이고 가독성을 향상시켜야한다. 
  * 파라미터화된 테스트를 통해 중복을 줄인다. 
    * ```
        @DisplayName("천원 미만의 금액에 대한 예외 처리")
        @ValueSource(strings = {"999", "0", "-123"})
        @ParameterizedTest
        void 테스트코드() {}
      ```
* static import 하지 않기
* 추가 학습 자료
  * TDD 방식
  * [자동차 경주 피드백](https://drive.google.com/file/d/1LqABX_kG5GHRMd1iD_gm8dSJjVpuxRdz/view)