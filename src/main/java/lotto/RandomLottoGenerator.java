package lotto;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.Collections;
import java.util.List;

public class RandomLottoGenerator implements LottoGenerator {
    @Override
    public Lotto generate() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(
                LottoConstant.LOTTO_START_NUMBER,
                LottoConstant.LOTTO_END_NUMBER,
                LottoConstant.LOTTO_NUMBER_COUNT);

        Collections.sort(numbers);

        return new Lotto(numbers);
    }
}