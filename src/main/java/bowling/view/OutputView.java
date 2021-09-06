package bowling.view;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bowling.model.Player;
import bowling.model.score.ScoreCalculator;
import bowling.model.frame.Frames;

public class OutputView {

	private static final String DELIMITER = "|";
	private static final String DOUBLE_INIT_EMPTY = "  ";
	private static final String INIT_EMPTY = " ";
	private static final int STRING_LENGTH = 4;
	private static final int MAX_PIN = 10;
	private static final int START_INCLUSIVE = 0;
	private static final int LIMIT_MAX_LENGTH = 5;

	public static void printScoreBoard(List<Player> players) {
		for (Player player : players) {
			System.out.print(DELIMITER + " NAME " + DELIMITER);
			System.out.print(getFrameNumbers(player.getFrames()) + DELIMITER + System.lineSeparator());
			System.out.print(DELIMITER + printFormatter(player.getPlayerName()) + DELIMITER);
			System.out.print(getFrameGameResult(player.getFrames()) + DELIMITER + System.lineSeparator());
			System.out.print(DELIMITER + printFormatter("SCORE") + DELIMITER);
			System.out.print(
				getFrameGameScore(player.getFrames().getScoreBoard()) + DELIMITER + System.lineSeparator());
		}
	}

	private static String getFrameGameScore(ScoreCalculator scoreCalculator) {
		return scoreCalculator.getScore().stream()
			.map(value -> printFormatter(convertScore(value)))
			.collect(Collectors.joining(DELIMITER));
	}

	private static String convertScore(int gameScore) {
		if (gameScore == -1) {
			return "";
		}
		return String.valueOf(gameScore);
	}

	private static String getFrameGameResult(Frames frames) {
		return frames.getFrames().stream()
			.map(frame -> printFormatter(frame.getGameStatus()))
			.collect(Collectors.joining(DELIMITER));
	}

	private static String getFrameNumbers(Frames frames) {
		return frames.getFrames().stream()
			.map(frame -> toString(frame.getFrameNumber()))
			.collect(Collectors.joining(DELIMITER));
	}

	private static String toString(int frameNumber) {
		if (frameNumber < MAX_PIN) {
			return printFormatter("0" + frameNumber);
		}
		return printFormatter(String.valueOf(frameNumber));
	}

	private static String printFormatter(String value) {
		if (value.length() == LIMIT_MAX_LENGTH) {
			return INIT_EMPTY + value;
		}
		return DOUBLE_INIT_EMPTY + value + createFormatEmpty(value.length());
	}

	private static String createFormatEmpty(int length) {
		return IntStream.range(START_INCLUSIVE, STRING_LENGTH - length)
			.mapToObj(empty -> " ")
			.collect(Collectors.joining());
	}
}
