package com.ccsw.tutorial.loan;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.loan.exceptions.ClientWithActiveLoanException;
import com.ccsw.tutorial.loan.exceptions.GameAlreadyOnLoanException;
import com.ccsw.tutorial.loan.exceptions.LoanDurationExceededException;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La duración no puede exceder los 14 días.");

        } catch (GameAlreadyOnLoanException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este juego ya está prestado.");

        } catch (ClientWithActiveLoanException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este cliente ya tiene un préstamo en curso.");

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
     * Método para recuperar una lista de {@link Loan}
     *
     * @param clientId   ID del cliente
     * @param idCategory ID de la categoría del juego
     * @param date       Fecha del préstamo
     * @return {@link List} de {@link LoanDto}
     */
//    @Operation(summary = "Find", description = "Method that return a filtered list of Loans")
//    @RequestMapping(path = "", method = RequestMethod.GET)
//    public List<LoanDto> find(@RequestParam(value = "title", required = false) String title,
//            @RequestParam(value = "idClient", required = false) Long idClient,
//            @RequestParam(value = "start_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
//            @RequestParam(value = "end_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date) {
//
//        System.out.println("CONTROLLER");
//        System.out.println(start_date);
//        System.out.println(title);
//        List<Loan> loans = loanService.find(title, idClient, start_date, end_date);
//        System.out.println("CONTROLLER END");
//
//        return loans.stream().map(e -> mapper.map(e, LoanDto.class)).collect(Collectors.toList());
//    }

    @Operation(summary = "Find", description = "Method that return a filtered list of Loans")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public List<LoanDto> find(@RequestBody LoanSearchDto dto) {

        System.out.println(dto);
        System.out.println(dto.getTitle());
        System.out.println(dto.getIdClient());
        List<Loan> loans = loanService.find(dto);

        return loans.stream().map(e -> mapper.map(e, LoanDto.class)).collect(Collectors.toList());
    }

}
