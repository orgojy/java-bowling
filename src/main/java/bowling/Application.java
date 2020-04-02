package bowling;


import bowling.application.BowlingService;
import bowling.application.Request;
import bowling.application.Response;
import bowling.ui.BowlingController;
import bowling.view.InputView;
import bowling.view.ResultView;

public class Application {

    public static void main(String[] args) {
        Request request = new Request(InputView.inputName());

        BowlingController bowlingController = new BowlingController(new BowlingService());

        Response response = bowlingController.bowl(request);
        ResultView.view(response);

        while (!response.isLastFrame()) {
            request = request.bowlFallenPins(InputView.inputBowl(response.getFrameNumber()));
            response = bowlingController.bowl(request);
            ResultView.view(response);
        }
    }
}