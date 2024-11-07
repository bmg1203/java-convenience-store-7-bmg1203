package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import store.utils.Parser;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDateTime start_date;
    private final LocalDateTime end_date;

    public Promotion(String name, String buy, String get, String start_date, String end_date) {
        this.name = name;
        this.buy = Parser.parseNumberToInt(buy);
        this.get = Parser.parseNumberToInt(get);
        this.start_date = LocalDateTime.parse(start_date);
        this.end_date = LocalDateTime.parse(end_date);
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public boolean isActive() {
        LocalDateTime today = DateTimes.now();
        return !today.isBefore(start_date) && !today.isAfter(end_date);
    }
}
