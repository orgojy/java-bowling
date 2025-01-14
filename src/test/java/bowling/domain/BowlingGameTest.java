package bowling.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingGameTest {
    public static final BowlingGame GAME_WITH_PLAYER_AYM = BowlingGame.create(PlayerTest.PLAYER_AYM);
    public static final BowlingGame GAME_WITH_PLAYER_KMS = BowlingGame.create(PlayerTest.PLAYER_KMS);

    BowlingGame bowlingGame;

    @BeforeEach
    void setUp() {
        bowlingGame = BowlingGame.create(PlayerTest.PLAYER_AYM);
    }

    @DisplayName("볼링 게임 생성")
    @Test
    void create() {
        // then
        assertThat(bowlingGame).isNotNull();
    }

    @DisplayName("스트라이크 피칭 후, 프레임 변화 확인")
    @Test
    void bowl() {
        // when
        bowlingGame.bowl(PinsTest.TEN);
        // then
        assertThat(bowlingGame.getCurrentFrameIndex()).isEqualTo(2);
    }

    @DisplayName("플레이어 이름 확인")
    @Test
    void getPlayerName() {
        // then
        assertThat(bowlingGame.getPlayerName()).isEqualTo(PlayerTest.PLAYER_AYM.getName());
    }

    @DisplayName("다음 피칭이 가능한 지 여부 확인")
    @Test
    void hasNextPitching() {
        // then
        assertThat(bowlingGame.hasNextPitching()).isTrue();
    }

    @DisplayName("현재 프레임의 인덱스 확인")
    @Test
    void getCurrentFrameIndex() {
        // then
        assertThat(bowlingGame.getCurrentFrameIndex()).isEqualTo(1);
        // when
        bowlingGame.bowl(PinsTest.TEN);
        // then
        assertThat(bowlingGame.getCurrentFrameIndex()).isEqualTo(2);
    }

    @DisplayName("현재 진행된 모든 프레임들 확인")
    @Test
    void getFrames() {
        // then
        assertThat(bowlingGame.getFrames()).hasSize(1);
    }

    @DisplayName("게임 진행 중에 최근 프레임 투구가 진행하는 경우 확인")
    @Test
    void hasFrameInProgress() {
        // when & then
        assertThat(bowlingGame.hasFrameInProgress(FrameIndexTest.FIRST_INDEX)).isTrue();
    }

    @DisplayName("게임 진행 중에 최근 프레임 투구가 종료된 경우 확인")
    @Test
    void hasFrameFinished() {
        // when
        bowlingGame.bowl(PinsTest.TEN);
        // then
        assertThat(bowlingGame.hasFrameInProgress(FrameIndexTest.FIRST_INDEX)).isFalse();
    }
}
