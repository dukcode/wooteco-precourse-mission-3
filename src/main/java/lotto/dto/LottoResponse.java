package lotto.dto;

import java.util.List;
import lotto.domain.Lotto;

public class LottoResponse {

    private final List<Integer> numbers;

    public LottoResponse(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public static LottoResponse from(Lotto lotto) {
        return new LottoResponse(lotto.getNumbers());
    }
}
