package com.ccsw.tutorial.loan;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.loan.exceptions.ClientWithActiveLoanException;
import com.ccsw.tutorial.loan.exceptions.GameAlreadyOnLoanException;
import com.ccsw.tutorial.loan.exceptions.InitDatePosteriorToEndDate;
import com.ccsw.tutorial.loan.exceptions.LoanDurationExceededException;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author LaClCr
 *
 */
@Tag(name = "Loan", description = "API of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para crear un nuevo préstamo.
     *
     * @param dto El DTO que contiene la información del préstamo a guardar.
     * @return ResponseEntity con el resultado de la operación.
     * @throws Exception
     */
    @Operation(summary = "Save", description = "Method that saves a Loan")
    @RequestMapping(path = { "" }, method = RequestMethod.PUT)

    public ResponseEntity<?> save(@RequestBody LoanDto dto) throws Exception {

        try {
            this.loanService.save(dto);
            return ResponseEntity.ok().build();

        } catch (LoanDurationExceededException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La duración no puede exceder los 14 días.");

        } catch (GameAlreadyOnLoanException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este juego ya está prestado.");

        } catch (ClientWithActiveLoanException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este cliente ya tiene un préstamo en curso.");

        } catch (InitDatePosteriorToEndDate e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("La fecha de inicio no puede ser posterior a la fecha de fin.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo guardar el préstamo.");
        }
    }

    /**
     * Método para eliminar un {@link Loan}
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Loan")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {

        this.loanService.delete(id);
    }

    /**
     * Método que devuelve una lista paginada de préstamos filtrada por criterios.
     *
     * @param dto El DTO que contiene los criterios de búsqueda.
     * @return Una página de resultados de préstamos.
     */
    @Operation(summary = "Find", description = "Method that return a filtered list of Loans")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<LoanDto> find(@RequestBody LoanSearchDto dto) {

        dto.formatDate();
        Page<Loan> loans = loanService.find(dto);

        return new PageImpl<>(
                loans.getContent().stream().map(e -> mapper.map(e, LoanDto.class)).collect(Collectors.toList()),
                loans.getPageable(), loans.getTotalElements());
    }

}
