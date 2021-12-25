package bowling.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PinsTest {

	@DisplayName("Pin 생성")
    @ParameterizedTest
    @ValueSource(ints = {Pins.MIN_PINS, (Pins.MIN_PINS + Pins.MAX_PINS) / 2, Pins.MAX_PINS})
    void create(int pins) {
        // when & then
        assertThat(Pins.create(pins)).isNotNull();
    }

    @DisplayName("Pins 생성 예외 - 잘못된 생성 결과 전달")
    @ParameterizedTest
    @ValueSource(ints = {Pins.MIN_PINS - 1, Pins.MAX_PINS + 1})
    void createFailed(int invalidPins) {
        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Pins.create(invalidPins));
    }

    @DisplayName("Pins 의 값 반환")
    @Test
    void getValue() {
        // given
        Pins pins = Pins.create(Pins.MIN_PINS);
        // when & then
        assertThat(pins.getValue()).isEqualTo(Pins.MIN_PINS);
    }

    @DisplayName("Pins 의 strike 확인")
    @Test
    void isStrike() {
        // given
        Pins pins = Pins.create(Pins.MAX_PINS);
        // when & then
        assertThat(pins.isStrike()).isTrue();
    }

    @DisplayName("Pins 의 spare 확인")
    @Test
    void isSpare() {
        // given
        Pins first = Pins.create(7);
        Pins second = Pins.create(3);
        // when & then
        assertThat(first.isSpare(second)).isTrue();
    }

    @DisplayName("Pins 의 gutter 확인")
    @Test
    void isGutter() {
        // given
        Pins pins = Pins.create(0);
        // when & then
        assertThat(pins.isGutter()).isTrue();
    }
}
