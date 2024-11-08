package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import store.utils.Parser;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDateTime start_date;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final LocalDateTime end_date;

    public Promotion(String name, String buy, String get, String start_date, String end_date) {
        this.name = name;
        this.buy = Parser.parseNumberToInt(buy);
        this.get = Parser.parseNumberToInt(get);
        this.start_date = LocalDate.parse(start_date, DATE_FORMATTER).atStartOfDay();
        this.end_date = LocalDate.parse(end_date, DATE_FORMATTER).atStartOfDay();
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

    public boolean correctCount(int quantity) {
        return quantity % (buy + get) == 0;
    }

    public int addCount(int quantity) {
        quantity += quantity % (buy + get);
        return quantity;
    }

    public int extraCount(int quantity) {
        return quantity % (buy + get);
    }

    public int freeCount(int quantity) {
        return quantity / (buy + get);
    }
}
