package lotto.service;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.LottoBonusNumber;
import lotto.domain.LottoPrize;
import lotto.domain.LottoPurchase;
import lotto.domain.LottoWinningNumber;
import lotto.domain.LottoWinningResult;
import lotto.dto.LottoBonusNumberCreateRequest;
import lotto.dto.LottoPurchaseRequest;
import lotto.dto.LottoWinningNumberCreateRequest;
import lotto.generator.LottoGenerator;

public class LottoGameService {

    private final LottoGenerator lottoGenerator;

    private LottoPurchase lottoPurchase;
    private List<Lotto> lottoTickets;
    private LottoWinningNumber lottoWinningNumber;
    private LottoBonusNumber lottoBonusNumber;

    public LottoGameService(LottoGenerator lottoGenerator) {
        this.lottoGenerator = lottoGenerator;
    }

    public LottoWinningResult calculateLottoWinningResult() {
        LottoWinningResult lottoWinningResult = new LottoWinningResult();
        for (Lotto lottoTicket : lottoTickets) {
            LottoPrize lottoPrize = lottoTicket.prize(lottoWinningNumber, lottoBonusNumber);
            lottoWinningResult.addPrize(lottoPrize);
        }
        return lottoWinningResult;
    }

    public LottoPurchase getLottoPurchase() {
        return lottoPurchase;
    }

    public List<Lotto> createLottoPurchase(LottoPurchaseRequest lottoPurchaseRequest) {
        lottoPurchase = new LottoPurchase(lottoPurchaseRequest.getPurchaseAmount());
        return lottoTickets = lottoPurchase.purchase(lottoGenerator);
    }

    public void createLottoWinningNumber(LottoWinningNumberCreateRequest lottoWinningNumberCreateRequest) {
        lottoWinningNumber = new LottoWinningNumber(lottoWinningNumberCreateRequest.getNumbers());
    }

    public void createLottoBonusNumber(LottoBonusNumberCreateRequest lottoBonusNumberCreateRequest) {
        lottoBonusNumber = new LottoBonusNumber(lottoWinningNumber, lottoBonusNumberCreateRequest.getBonusNumber());
    }
}