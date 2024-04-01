package com.ccsw.tutorial.loan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.tutorial.loan.model.Loan;

/**
 * @author LaClCr
 *
 */
@Repository
public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    /**
     * Método para encontrar todos los préstamos teniendo en cuenta la paginación
     * 
     * @param spec     Especificaciones para el filtrado
     * @param pageable Información de la paginación
     * @return página de préstamos que coinciden con el filtrado
     */

    @EntityGraph(attributePaths = { "client", "game" })
    Page<Loan> findAll(Specification<Loan> spec, Pageable pageable);
}
