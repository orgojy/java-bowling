# 볼링 게임 점수판

## 요구 사항

### 기능 요구사항

* 최종 목표는 볼링 점수를 계산하는 프로그램을 구현한다.
    * 1단계 목표는 점수 계산을 제외한 볼링 게임 점수판을 구현하는 것이다.
    * 2단계 목표는 사용자 1명의 볼링 게임 점수를 관리할 수 있는 프로그램을 구현한다.
* 각 프레임이 스트라이크이면 "X", 스페어이면 "9 | /", 미스이면 "8 | 1", 과 같이 출력하도록 구현한다.
    * 스트라이크(strike) : 프레임의 첫번째 투구에서 모든 핀(10개)을 쓰러트린 상태
    * 스페어(spare) : 프레임의 두번재 투구에서 모든 핀(10개)을 쓰러트린 상태
    * 미스(miss) : 프레임의 두번재 투구에서도 모든 핀이 쓰러지지 않은 상태
    * 거터(gutter) : 핀을 하나도 쓰러트리지 못한 상태. 거터는 "-"로 표시
* 스트라이크는 다음 2번의 투구까지 점수를 합산해야 한다. 스페어는 다음 1번의 투구까지 점수를 합산해야 한다.
* 10 프레임은 스트라이크이거나 스페어이면 한 번을 더 투구할 수 있다.
* 1명 이상의 사용자가 사용할 수 있는 볼링게임 점수판을 구현한다.

### 구현 시작 방법

* 볼링 게임의 점수 계산 방식 아는 사람은 바로 구현을 시작한다.
* 점수 계산 방식을 모르는 사람은 구글에서 "볼링 점수 계산법"과 같은 키워드로 검색해 볼링 게임의 점수 계산 방식을 학습한 후 구현을 시작한다.

### 프로그램 실행 결과

![bowling_execution_results](https://user-images.githubusercontent.com/15815583/147674355-e0bf45a6-2bad-4ec9-8dae-9ba52d7b3f2d.png)

## 프로그래밍 요구사항

* **객체지향 생활 체조 원칙을 지키면서 프로그래밍한다.**
* **객체지향 5원칙을 지키면서 프로그래밍한다.**

### 객체지향 생활 체조 원칙

* 규칙 1: 한 메서드에 오직 한 단계의 들여쓰기만 한다.
* 규칙 2: else 예약어를 쓰지 않는다.
* 규칙 3: 모든 원시값과 문자열을 포장한다.
* 규칙 4: 한 줄에 점을 하나만 찍는다.
* 규칙 5: 줄여쓰지 않는다(축약 금지).
* 규칙 6: 모든 엔티티를 작게 유지한다.
* 규칙 7: 3개 이상의 인스턴스 변수를 가진 클래스를 쓰지 않는다.
* 규칙 8: 일급 콜렉션을 쓴다.
* 규칙 9: 게터/세터/프로퍼티를 쓰지 않는다.

### 객체지향 5원칙(SOLID)

* SRP (단일책임의 원칙: Single Responsibility Principle)
    * 작성된 클래스는 하나의 기능만 가지며 클래스가 제공하는 모든 서비스는 그 하나의 책임(변화의 축: axis of change)을 수행하는 데 집중되어 있어야 한다
* OCP (개방폐쇄의 원칙: Open Close Principle)
    * 소프트웨어의 구성요소(컴포넌트, 클래스, 모듈, 함수)는 확장에는 열려있고, 변경에는 닫혀있어야 한다.
* LSP (리스코브 치환의 원칙: The Liskov Substitution Principle)
    * 서브 타입은 언제나 기반 타입으로 교체할 수 있어야 한다. 즉, 서브 타입은 언제나 기반 타입과 호환될 수 있어야 한다.
* ISP (인터페이스 분리의 원칙: Interface Segregation Principle)
    * 한 클래스는 자신이 사용하지 않는 인터페이스는 구현하지 말아야 한다.
* DIP (의존성역전의 원칙: Dependency Inversion Principle)
    * 구조적 디자인에서 발생하던 하위 레벨 모듈의 변경이 상위 레벨 모듈의 변경을 요구하는 위계관계를 끊는 의미의 역전 원칙이다.

## 힌트

* 객체 단위를 가장 작은 단위까지 극단적으로 분리하는 시도를 해본다.
* 1 ~ 9 프레임을 NormalFrame, 10 프레임을 FinalFrame과 같은 구조로 구현한 후 Frame을 추가해 중복을 제거해 본다.
* 다음 Frame을 현재 Frame 외부에서 생성하기 보다 현재 Frame에서 다음 Frame을 생성하는 방식으로 구현해 보고, 어느 구현이 더 좋은지 검토해 본다.
* 점수를 구하는 역할을 각 Frame이 담당할 수 있도록 구현해 본다. Frame이 자신의 점수를 구하려면 다음 Frame에 접근할 수 있어야 한다. Frame이 LinkedList와 같은 자료 구조 기반으로
  구현해 본다.
* Frame 외부에서 점수를 계산해 set하는 것이 아니라 Frame 자체가 점수를 계산할 수 있도록 한다.

```java
Frame frame = new Frame(frameNo);
frame.bowl(10);
frame.bowl(8);
frame.bowl(2);
int score=frame.getScore();
// score는 20을 반환해야 한다.
```

* 자바의 다형성을 적용해 로직 구현에서 발생하는 수 많은 if/else를 제거한다.
* 구현 중 해결책을 찾기 힘든 경우 볼링 점수판 리팩토링 힌트 문서를 참고해 구현해 본다.
    * 단, 정말 해결책을 찾으려다 포기하고 싶은 상황이 됐을 때 참고할 것을 추천한다.

## 볼링 계산기 참고 링크

https://www.bowlinggenius.com/

## 기능 목록

* `Player`
    * `Player` 생성
        * '영문 대소문', '3글자' 이름 조건 허용
* `Players`
    * `Players` 생성
      * List `Player`를 관리하기 위한 새로운 타입 일급 컬렉션
      * null 또는 비어있는 List `Player` 전달시 예외
* `Pins`
    * `Pins` 생성
        * 핀의 갯수는 0개에서 10개까지 허용
    * `Pins`의 `Score` 반환
    * 서로 다른 두 `Pins`의 `Score`의 합인 `Score` 반환
* `ThrowingState`
    * `RunningState` 한 프레임이 진행중인 볼링 투구 상태
        * `Ready` 한 프레임에서 투구를 던지기 전 준비 상태
        * `FirstBowl` 한 프레임에서 첫 번째 투구를 던지는 상태
    * `EndedState` 한 프레임이 끝난 볼링 투구 상태
        * `Miss` 한 프레임에서 더 이상 핀을 맞추지 못한 상태
        * `Spare` 한 프레임에서 두 번째 투구에서 모든 핀을 맞춘 상태
        * `Strike` 한 프레임에서 첫 번째 투구로 모든 핀을 맞춘 상태
* `FrameIndex`
    * `Frame`이 진행될 인덱스 정의
        * 1부터 시작해서 10까지 진행될 수 있다.
* `Frame`
    * `BasicFrame` 마지막 프레임을 제외한 최대 2개의 투구를 할 수 있는 상태의 프레임
        * 현재 1라운드부터 9라운드까지 프레임 인덱스와 투구 상태를 확인한다.
        * 9라운드의 다음 라운드는 `LastFrame`을 반환한다.
    * `LastFrame` 마지막 프레임
        * 기존 프레임과 마찬가지로 2회까지 기회가 있고, 추가 1번 총 3번까지 투구할 수 있다.
        * 추가 투구 기회는 앞 투구 2회에서 spare 또는 strike가 있어야한다.
* `Frames`
    * 볼링에 필요한 10프레임을 구성하기 위한 일급컬렉션 구현
    * 최근 진행하는 `Frame`이 투구가 진행중인지 확인하는 기능 구현
* `Score`
    * 투구 점수 생성 기능 구현
    * 투구 점수 생성 검증 구현
        * 추가 투구를 고려해서 투구 점수는 0점 이상, 30점 이하를 가진다.
        * 추가 투구는 없거나 최대 2회를 가질 수 있다.
    * 최종 점수 반환 기능 구현
    * 중간 점수를 확인하기 위한 반환 기능 구현
* `BowlingGame`
    * `Player`와 `Frames`를 이용한 볼링 게임 구현
    * `BowlingGame` 진행 중에 최근 `Frame` 투구가 진행중인지 확인하는 기능 구현
* `BowlingGames`
    * List `BowlingGame`을 관리할 일급 컬렉션 `BowlingGames` 생성 기능 구현
    * List `BowlingGame`을 관리할 일급 컬렉션 `BowlingGames` 생성 검증 구현
    * List `BowlingGame` 반환 기능 구현
    * 현재 `BowlingGames`의 `FrameIndex`를 반환하는 기능 구현
    * 다음 `BowlingGame`에서 투구를 할 수 있는지 확인 기능 구현
    * 현재 `BowlingGames`을 다음 `FrameIndex`로 증가시키는 기능 구현
* `BowlingGameController`
    * View와 Model을 분리하기 위한 Controller로 게임 구현
    * 다중 볼링 게임을 위해 `BowlingGames` 비즈니스 로직 구현
* `InputView`
    * `Player` 이름 입력 기능 구현
    * `Players` 다중 이름 입력 기능 구현
    * 다중 `Player` 정보 입력 기능 구현
        * 게임을 진행할 `Player` 수 입력
        * 게임을 진행할 `Player` 이름 입력
    * 투구 숫자 입력 기능 구현
* `OutputView`
    * 현재 볼링 점수판 출력 기능 구현
    * 다중 사용자 볼링 점수판 출력 기능 구현

* * *

# 질문 삭제하기 기능 리팩토링

## 질문 삭제하기 요구사항

* 질문 데이터를 완전히 삭제하는 것이 아니고, 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다.
* 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다.
* 답변이 없는 경우 삭제 가능하다.
* 질문자와 답변글의 모든 답변자같은 경우 삭제 가능하다.
* 질문자와 답변자가 다른 경우 답변 삭제할 수 없다.
* 질문을 삭제할 때 답변도 삭제한다.
    * 답변의 삭제 또한 삭제 상태(deleted)를 변경
    * 질문과 답변 삭제 이력에 대한 정보를 DeleteHistory로 남긴다.

## 구현 내용

* [X] Entity: `Question`의 Field: `List<Answer> answers`를 일급 컬랙션으로 변경
    * [X] `Question`에 속한 `List<Answer> answers` 삭제 메서드 `delete` 추가
    * [X] `delete` 메서드 호출 이후, 삭제 이력 `List<DeleteHistory> deleteHistories` 반환
* [X] Entity: `Answer`의 도메인 테스트 `AnswerTest` 추가
* [X] Entity: `Question`의 삭제 메서드 `delete` 추가
    * [X] VO: `Answers`의 삭제 여부 확인 메서드 `deletable` 추가
    * [X] `List<DeleteHistory>` 반환 방식으로 변경
* [X] Entity: `DeleteHistory`의 리스트 형태 사용 로직에 일급 컬렉션 `DeleteHistories` 적용
* [X] `DeleteHistory` 생성 관련 로직을 정적 팩토리 메서드 위임
* [X] Entity: `Answer`, `DeleteHistory`, `Question`, `User` 에 Field: `id` 기반 equals, hashCode 최적화
* [X] Entity: `Answer`, `DeleteHistory`, `Question`, `User`의 toString 재정의
* [X] Entity: `Answer` 삭제 로직 메서드로 위임
* [X] Entity: `User`, `Question`의 사용하지 않는 getter, seter 제거
* [X] Entity: `Question` 질문 삭제 테스트
    * [X] 질문 삭제 - 질문에 답변(들)이 있는데, 질문자와 답변자가 같은 경우
    * [X] 질문 삭제 예외 - 질문에 답변(들)이 있는데, 질문자와 답변자가 다른 경우

## 프로그래밍 요구사항

- qna.service.QnaService의 deleteQuestion()는 앞의 질문 삭제 기능을 구현한 코드이다. 이 메소드는 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드가 섞여 있다.
- 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드를 분리해 단위 테스트 가능한 코드 에 대해 단위 테스트를 구현한다.
