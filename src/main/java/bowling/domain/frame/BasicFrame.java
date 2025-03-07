package bowling.domain.frame;

import bowling.domain.FrameIndex;
import bowling.domain.Pins;
import bowling.domain.Score;
import bowling.domain.state.Ready;
import bowling.domain.state.ThrowingState;

import java.util.Objects;

public class BasicFrame implements Frame {
    private final FrameIndex index;
    private ThrowingState state;
    private Frame next;

    private BasicFrame(FrameIndex index, ThrowingState state) {
        validate(index, state);
        this.index = index;
        this.state = state;
    }

    public static BasicFrame create(FrameIndex index, ThrowingState state) {
        return new BasicFrame(index, state);
    }

    public static BasicFrame create(FrameIndex index) {
        return create(index, Ready.create());
    }

    public static BasicFrame initialize() {
        return create(FrameIndex.first());
    }

    public int getIndex() {
        return index.getValue();
    }

    @Override
    public Frame bowl(Pins pins) {
        this.state = state.bowl(pins);
        if (state.isEnd()) {
            this.next = nextFrame();
            return this.next;
        }
        return this;
    }

    @Override
    public boolean isEnd() {
        return state.isEnd();
    }

    @Override
    public String symbol() {
        return state.symbol();
    }

    @Override
    public int score() {
        if (!isEnd()) {
            return Score.INCOMPUTABLE_SCORE_VALUE;
        }
        Score score = state.score();
        if (score.hasFinalScore()) {
            return score.getValue();
        }
        return next.scoreAfter(score);
    }

    @Override
    public int scoreAfter(Score previousScore) {
        try {
            return finalScoreAfter(previousScore);
        } catch (IllegalStateException e) {
            return Score.INCOMPUTABLE_SCORE_VALUE;
        }
    }

    private int finalScoreAfter(Score previousScore) {
        Score score = state.scoreAfter(previousScore);
        if (score.hasFinalScore()) {
            return score.getValue();
        }
        if (Objects.isNull(next)) {
            throw new IllegalStateException("다음 프레임이 존재하지 않습니다.");
        }
        return next.scoreAfter(score);
    }

    private void validate(FrameIndex index, ThrowingState state) {
        if (Objects.isNull(index)) {
            throw new IllegalArgumentException("프레임을 셋팅할 인덱스가 null입니다.");
        }
        if (Objects.isNull(state)) {
            throw new IllegalArgumentException("프레임을 셋팅할 투구 상태가 null입니다.");
        }
    }

    private Frame nextFrame() {
        if (index.next().isMax()) {
            return nextLastFrame();
        }
        return nextBasicFrame();
    }

    private Frame nextLastFrame() {
        return LastFrame.initialize();
    }

    private Frame nextBasicFrame() {
        return create(index.next());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicFrame that = (BasicFrame) o;
        return Objects.equals(index, that.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
