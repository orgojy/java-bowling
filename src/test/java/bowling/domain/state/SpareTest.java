package bowling.domain.state;

import static org.assertj.core.api.Assertions.*;

import bowling.domain.PinsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import bowling.domain.Pins;

class SpareTest {

	private ThrowingState spare;

	@BeforeEach
	void beforeEach() {
		spare = Spare.create(PinsTest.FIVE, PinsTest.FIVE);
	}

	@DisplayName("한 프레임에서 Spare 후, 투구하는 경우 예외")
	@Test
	void bowl() {
		// when & then
		assertThatExceptionOfType(UnsupportedOperationException.class)
			.isThrownBy(() -> spare.bowl(PinsTest.FIVE));
	}

	@DisplayName("한 프레임에서 Spare 후, 종료된 상태 여부 확인")
	@Test
	void isEnd() {
		// when & then
		assertThat(spare.isEnd()).isTrue();
	}

	@DisplayName("Spare 표기법 확인")
	@Test
	void symbol() {
		// when & then
		assertThat(spare.symbol()).isEqualTo(String.format("%s|/", PinsTest.FIVE.getValue()));
	}
}