package com.ccsw.tutorial.loan;

import java.util.List;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

public interface LoanService {

    /**
     * Recupera un {@link Loan} a través de su ID
     *
     * @param id PK de la entidad
     * @return {@link Loan}
     */
    Loan get(Long id);

    /**
     * Recupera los préstamos filtrando opcionalmente por título, cliente y/o fecha
     *
     * @param clientId   título del juego
     * @param idCategory cliente
     * @param date       fecha
     * @return {@link List} de {@link Loan}
     */
//    List<Loan> find(String title, Long idClient, LocalDate start_date, LocalDate end_date);
    List<Loan> find(LoanSearchDto dto);

    /**
     * Guarda un préstamo
     * 
     * @param dto datos de la entidad
     */
    void save(LoanDto dto) throws Exception;

    /**
     * Método para eliminar un {@link Loan}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

}
