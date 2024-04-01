package com.ccsw.tutorial.loan.model;

import java.time.LocalDate;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author LaClCr
 *
 */

public class LoanDto {

    private Long id;

    private GameDto game;

    private ClientDto client;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate initDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * @return id
     */
    public Long getId() {

        return this.id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(Long id) {

        this.id = id;
    }

    /**
     * @return game
     */
    public GameDto getGame() {

        return this.game;
    }

    /**
     * @param game new value of {@link #getGame}.
     */
    public void setGame(GameDto game) {

        this.game = game;
    }

    /**
     * @return client
     */
    public ClientDto getClient() {

        return this.client;
    }

    /**
     * @param client new value of {@link #getClient}.
     */
    public void setClient(ClientDto client) {

        this.client = client;
    }

    /**
     * @return initDate
     */
    public LocalDate getInitDate() {

        return this.initDate;
    }

    /**
     * @param initDate new value of {@link #getInitDate}.
     */
    public void setInitDate(LocalDate initDate) {

        this.initDate = initDate;
    }

    /**
     * @return endDate
     */
    public LocalDate getEndDate() {

        return this.endDate;
    }

    /**
     * @param endDate new value of {@link #getEndDate}.
     */
    public void setEndDate(LocalDate endDate) {

        this.endDate = endDate;
    }
}