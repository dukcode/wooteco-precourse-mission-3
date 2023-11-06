package lotto.controller;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.LottoBonusNumber;
import lotto.domain.LottoPurchase;
import lotto.domain.LottoWinningNumber;
import lotto.domain.LottoWinningResult;
import lotto.service.LottoGameService;
import lotto.view.dto.LottoBonusNumberCreateRequest;
import lotto.view.dto.LottoPurchaseRequest;
import lotto.view.dto.LottoResponse;
import lotto.view.dto.LottoWinningNumberCreateRequest;
import lotto.view.dto.LottoWinningStatistics;

public class LottoGameController {
    private final LottoGameService lottoGameService;
    private final LottoGameView lottoGameView;
    private final ExceptionHandler exceptionHandler;

    public LottoGameController(LottoGameService lottoGameService, LottoGameView lottoGameView,
                               ExceptionHandler exceptionHandler) {
        this.lottoGameService = lottoGameService;
        this.lottoGameView = lottoGameView;
        this.exceptionHandler = exceptionHandler;
    }

    public void run() {
        LottoPurchase lottoPurchase = purchaseLotto();
        List<Lotto> lottoTickets = createLotto(lottoPurchase);
        LottoWinningNumber lottoWinningNumber = createLottoWinningNumber();
        LottoBonusNumber lottoBonusNumber = createLottoBonusNumber(lottoWinningNumber);
        printLottoWinningStatistics(lottoTickets, lottoPurchase, lottoWinningNumber, lottoBonusNumber);
    }

    private List<Lotto> createLotto(LottoPurchase lottoPurchase) {
        List<Lotto> lottoTickets = lottoGameService.purchaseLotto(lottoPurchase);
        List<LottoResponse> lottoResponses = lottoTickets.stream()
                .map(LottoResponse::from)
                .toList();
        lottoGameView.printPurchasedTickets(lottoResponses);
        return lottoTickets;
    }

    private void printLottoWinningStatistics(
            List<Lotto> lottoTickets,
            LottoPurchase lottoPurchase,
            LottoWinningNumber lottoWinningNumber,
            LottoBonusNumber lottoBonusNumber) {
        LottoWinningResult lottoWinningResult = lottoGameService.calculateLottoWinningResult(
                lottoTickets, lottoPurchase, lottoWinningNumber, lottoBonusNumber);
        lottoGameView.printWinningStatistics(LottoWinningStatistics.from(lottoWinningResult));
    }

    private LottoBonusNumber createLottoBonusNumber(LottoWinningNumber lottoWinningNumber) {
        return (LottoBonusNumber) exceptionHandler.applyFunction(lottoGameView,
                view -> {
                    LottoBonusNumberCreateRequest lottoBonusNumberCreateRequest =
                            view.inputLottoBonusNumberCreateRequest();
                    return new LottoBonusNumber(lottoWinningNumber,
                            lottoBonusNumberCreateRequest.getBonusNumber());
                });
    }

    private LottoWinningNumber createLottoWinningNumber() {
        return (LottoWinningNumber) exceptionHandler.applyFunction(lottoGameView,
                view -> {
                    LottoWinningNumberCreateRequest lottoWinningNumberCreateRequest =
                            view.inputLottoWinningNumberCreateRequest();
                    return new LottoWinningNumber(lottoWinningNumberCreateRequest.getNumbers());
                });
    }


    private LottoPurchase purchaseLotto() {
        return (LottoPurchase) exceptionHandler.applyFunction(lottoGameView,
                view -> {
                    LottoPurchaseRequest lottoPurchaseRequest = view.inputPurchaseRequest();
                    return new LottoPurchase(lottoPurchaseRequest.getPurchaseAmount());
                });
    }

}
