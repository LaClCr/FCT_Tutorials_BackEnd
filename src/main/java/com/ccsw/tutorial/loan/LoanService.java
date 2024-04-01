package com.ccsw.tutorial.loan;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

/**
 * @author LaClCr
 *
 */
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
     * @param dto El DTO de búsqueda que contiene los criterios de búsqueda.
     * 
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> find(LoanSearchDto dto);

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
