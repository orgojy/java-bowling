package bowling.domain.state;

import bowling.domain.PinsTest;
import bowling.domain.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class MissTest {

    @DisplayName("Miss가 아닌 두 번의 투구로 Miss를 생성하는 경우")
    @Test
    void createFailed() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Miss.create(PinsTest.FIVE, PinsTest.FIVE));
    }

    @DisplayName("한 프레임 종료 후, 재투구시 예외 확인")
    @Test
    void bowlFailed() {
        // given
        ThrowingState miss = Miss.create(PinsTest.FOUR, PinsTest.FIVE);
        // when & then
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> miss.bowl(PinsTest.ZERO));
    }

    @DisplayName("한 프레임에서 Miss 후, 종료된 상태 여부 확인")
    @Test
    void isEnd() {
        // given
        ThrowingState miss = Miss.create(PinsTest.FOUR, PinsTest.FIVE);
        // when & then
        assertThat(miss.isEnd()).isTrue();
    }

    @DisplayName("현재 상태의 symbol 검증")
    @Test
    void symbol() {
        // given
        ThrowingState miss = Miss.create(PinsTest.FIVE, PinsTest.FOUR);
        ThrowingState missWithGutter = Miss.create(PinsTest.FIVE, PinsTest.ZERO);
        // when & then
        assertAll(
                () -> assertThat(miss.symbol()).isEqualTo(String.format("%s|%s", PinsTest.FIVE.getValue(), PinsTest.FOUR.getValue())),
                () -> assertThat(missWithGutter.symbol()).isEqualTo(String.format("%s|-", PinsTest.FIVE.getValue()))
        );
    }

    @DisplayName("현재 Score 반환 확인")
    @Test
    void score() {
        // given
        ThrowingState miss = Miss.create(PinsTest.FIVE, PinsTest.FOUR);
        // when & then
        assertThat(miss.score()).isEqualTo(Score.withNonRemainingPitches(9));
    }

    @DisplayName("1 Strike 1 Miss 이후, Score 반환 확인")
    @Test
    void scoreAfter() {
        // given
        ThrowingState miss = Miss.create(PinsTest.FIVE, PinsTest.FOUR);
        Score strike = Score.of(10, 2);
        // when & then
        assertThat(miss.scoreAfter(strike)).isEqualTo(Score.withNonRemainingPitches(19));
    }
}
