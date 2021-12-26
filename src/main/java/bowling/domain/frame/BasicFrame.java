package bowling.domain.frame;

import bowling.domain.FrameIndex;
import bowling.domain.Pins;
import bowling.domain.state.Ready;
import bowling.domain.state.ThrowingState;

import java.util.Objects;

public class BasicFrame implements Frame {
    private final FrameIndex index;
    private ThrowingState state;

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
            return createNextFrame();
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

    private void validate(FrameIndex index, ThrowingState state) {
        if(Objects.isNull(index)) {
            throw new IllegalArgumentException("프레임을 셋팅할 인덱스가 null입니다.");
        }
        if(Objects.isNull(state)) {
            throw new IllegalArgumentException("프레임을 셋팅할 투구 상태가 null입니다.");
        }
    }

    private Frame createNextFrame() {
        if (index.next().isMax()) {
            return LastFrame.initialize();
        }
        return create(index.next());
    }
}