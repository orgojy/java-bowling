package bowling.domain.frame;

import bowling.domain.FrameIndex;
import bowling.domain.Pins;
import bowling.domain.PinsTest;
import bowling.domain.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class LastFrameTest {

    @DisplayName("ready")
    @Test
    void ready() {
        verify(Collections.emptyList(), false, 1, "", Score.INCOMPUTABLE_SCORE_VALUE);
    }

    @DisplayName("5 pins")
    @Test
    void fivePins() {
        verify(Collections.singletonList(5), false, 1, "5", Score.INCOMPUTABLE_SCORE_VALUE);
    }

    @DisplayName("miss, gutter")
    @Test
    void missWithGutter() {
        verify(Arrays.asList(5, 0), true, 1, "5|-", 5 + 0);
    }

    @DisplayName("miss")
    @Test
    void miss() {
        verify(Arrays.asList(5, 4), true, 1, "5|4", 5 + 4);
    }

    @DisplayName("1 strike, miss")
    @Test
    void oneStrikeMiss() {
        verify(Arrays.asList(10, 5, 3), true, 2, "X|5|3", 10 + 5 + 3);
    }

    @DisplayName("1 spare, 5 pins")
    @Test
    void oneSpareFivePins() {
        verify(Arrays.asList(5, 5, 5), true, 2, "5|/|5", 5 + 5 + 5);
    }

    @DisplayName("1 strike, spare")
    @Test
    void oneStrikeSpare() {
        verify(Arrays.asList(10, 5, 5), true, 2, "X|5|/", 10 + 5 + 5);
    }

    @DisplayName("2 strike")
    @Test
    void twoStrike() {
        verify(Arrays.asList(10, 10), false, 2, "X|X", Score.INCOMPUTABLE_SCORE_VALUE);
    }

    @DisplayName("2 strike, 5 pins")
    @Test
    void twoStrikeFivePins() {
        verify(Arrays.asList(10, 10, 5), true, 3, "X|X|5", 10 + 10 + 5);
    }

    @DisplayName("3 strike")
    @Test
    void threeStrike() {
        verify(Arrays.asList(10, 10, 10), true, 3, "X|X|X", 10 + 10 + 10);
    }

    @DisplayName("LastFrame 생성")
    @Test
    void initialize() {
        // when & then
        assertThat(LastFrame.initialize()).isNotNull();
    }

    @DisplayName("2번 투구 후, miss인 상태에서 추가 투구시 예외")
    @Test
    void extraPitchAfter2PitchWithMiss() {
        // given
        LastFrame lastFrame = LastFrame.initialize();
        Frame lastFrameWithMiss = lastFrame.bowl(PinsTest.FIVE).bowl(PinsTest.FOUR);
        // when & then
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> lastFrameWithMiss.bowl(PinsTest.FOUR));
    }

    @DisplayName("LastFrame 의 index 검증")
    @Test
    void getIndex() {
        // given
        LastFrame lastFrame = LastFrame.initialize();
        // when & then
        assertThat(lastFrame.getIndex()).isEqualTo(FrameIndex.MAX_INDEX);
    }

    @DisplayName("추가 점수 계산이 가능한 시점에 추가 점수 계산")
    @Test
    void calculateAdditionalScore() {
        // given
        LastFrame lastFrame = LastFrame.initialize();
        lastFrame.bowl(Pins.create(10));
        lastFrame.bowl(Pins.create(10));
        // when & then
        assertThat(lastFrame.scoreAfter(Score.of(10, 2))).isEqualTo(30);
    }

    @DisplayName("추가 점수를 계산할 시점이 아니어서 계산할 수 없는 경우")
    @Test
    void calculateAdditionalScoreInvalid() {
        // given
        LastFrame lastFrame = LastFrame.initialize();
        // when & then
        assertThat(lastFrame.scoreAfter(Score.of(10, 2))).isEqualTo(Score.INCOMPUTABLE_SCORE_VALUE);
    }

    private void verify(List<Integer> pinNumbers, boolean isEnd, int stateSize, String symbol, int score) {
        // given
        LastFrame lastFrame = LastFrame.initialize();
        // when
        for (int pinNumber : pinNumbers) {
            lastFrame.bowl(Pins.create(pinNumber));
        }
        // then
        assertAll(
                () -> assertThat(lastFrame.isEnd()).isEqualTo(isEnd),
                () -> assertThat(lastFrame.size()).isEqualTo(stateSize),
                () -> assertThat(lastFrame.symbol()).isEqualTo(symbol),
                () -> assertThat(lastFrame.score()).isEqualTo(score)
        );
    }
}
