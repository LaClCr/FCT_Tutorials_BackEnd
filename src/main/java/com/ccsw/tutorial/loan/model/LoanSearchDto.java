package com.ccsw.tutorial.loan.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.game.model.GameDto;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author LaClCr
 *
 */
public class LoanSearchDto {

    private PageableRequest pageable;

    private GameDto game;

    private ClientDto client;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Formats the date using the pattern "yyyy-MM-dd".
     * 
     * If the date is not null, it will be formatted accordingly.
     */
    public void formatDate() {
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = date.format(formatter);
            this.date = LocalDate.parse(formattedDate, formatter);
        }
    }

}
