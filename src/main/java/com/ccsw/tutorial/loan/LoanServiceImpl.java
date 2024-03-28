package com.ccsw.tutorial.loan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.exceptions.ClientWithActiveLoanException;
import com.ccsw.tutorial.loan.exceptions.GameAlreadyOnLoanException;
import com.ccsw.tutorial.loan.exceptions.LoanDurationExceededException;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    /**
     * Obtiene un préstamo por su ID.
     *
     * @param id El ID del préstamo.
     * @return El préstamo correspondiente al ID, o null si no se encuentra.
     */
    @Override
    public Loan get(Long id) {
        return this.loanRepository.findById(id).orElse(null);
    }

    /**
     * Encuentra préstamos según los criterios especificados en el DTO de búsqueda.
     *
     * @param dto El DTO de búsqueda que contiene los criterios de búsqueda.
     * @return Una página de préstamos que coinciden con los criterios de búsqueda.
     */
    @Override
//    public List<Loan> find(String title, Long idClient, LocalDate start_date, LocalDate end_date) {
//
//        Specification<Loan> spec = Specification.where(null);
//
//        if (title != null) {
//            spec = spec.and(new LoanSpecification(new SearchCriteria("game.title", ":", title)));
//        }
//        if (idClient != null) {
//            spec = spec.and(new LoanSpecification(new SearchCriteria("client.id", ":", idClient)));
//        }
//        if (start_date != null) {
//            spec = spec.and(new LoanSpecification(new SearchCriteria("start_date", ">=", start_date)));
//        }
//        if (end_date != null) {
//            spec = spec.and(new LoanSpecification(new SearchCriteria("end_date", "<=", end_date)));
//        }
//
//        return this.loanRepository.findAll(spec);
//    }

    public List<Loan> find(LoanSearchDto dto) {

        Specification<Loan> spec = Specification.where(null);

        if (dto.getTitle() != null) {
            spec = spec.and(new LoanSpecification(new SearchCriteria("game.title", ":", dto.getTitle())));
        }
        if (dto.getIdClient() != null) {
            spec = spec.and(new LoanSpecification(new SearchCriteria("client.id", ":", dto.getIdClient())));
        }
        if (dto.getStart_date() != null) {
            spec = spec.and(new LoanSpecification(new SearchCriteria("start_date", ">=", dto.getStart_date())));
        }
        if (dto.getEnd_date() != null) {
            spec = spec.and(new LoanSpecification(new SearchCriteria("end_date", "<=", dto.getEnd_date())));
        }

        return this.loanRepository.findAll(spec);
    }

    /**
     * Guarda un nuevo préstamo basado en el DTO de préstamo proporcionado.
     *
     * @param dto El DTO de préstamo que contiene la información del préstamo a
     *            guardar.
     * @throws Exception Si ocurre un error durante el proceso de guardado, como la
     *                   duración del préstamo excede los 14 días, el juego ya está
     *                   siendo prestado o el cliente tiene un préstamo activo.
     */
    public void save(LoanDto dto) throws Exception {

        Loan loan = new Loan();
        List<Loan> loans = (List<Loan>) this.loanRepository.findAll();

        if (!isLoanDurationValid(dto)) {
            throw new LoanDurationExceededException("Loan must be 14 days max");
        }

        for (int i = 0; i < loans.size(); i++) {

            if (dto.getGame().getId() == loans.get(i).getGame().getId()) {

                if (isGameOnLoan(loans, dto, i)) {
                    throw new GameAlreadyOnLoanException("Game already on loan");
                }
            }

            if (dto.getClient().getId() == loans.get(i).getClient().getId()) {
                if (isClientWithActiveLoan(loans, dto, i)) {
                    throw new ClientWithActiveLoanException("Client has already an active loan");
                }
            }
        }

        BeanUtils.copyProperties(dto, loan, "id", "game", "client");

        loan.setGame(gameService.get(dto.getGame().getId()));
        loan.setClient(clientService.get(dto.getClient().getId()));

        this.loanRepository.save(loan);
    }

    /**
     * Elimina un préstamo por su ID.
     *
     * @param id El ID del préstamo a eliminar.
     * @throws Exception Si el préstamo no existe.
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Loan does not exist");
        }

        this.loanRepository.deleteById(id);
    }

    // ---------------MÉTODOS DE VALIDACIÓN--------------------

    /**
     * Verifica si la duración del préstamo especificado en el DTO de préstamo es
     * válida.
     *
     * @param dto El DTO de préstamo que contiene la información de las fechas de
     *            inicio y fin del préstamo.
     * @return true si la duración del préstamo es válida y no excede los 14 días,
     *         false de lo contrario.
     */
    public boolean isLoanDurationValid(LoanDto dto) {

        boolean ok = true;
        if (ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) > 14) {
            ok = false;
        }
        return ok;
    }

    /**
     * Verifica si el juego especificado en el DTO de préstamo está siendo prestado
     * en el rango de fechas especificado.
     *
     * @param loans La lista de préstamos existentes.
     * @param dto   El DTO de préstamo que contiene la información del juego.
     * @param index El índice del préstamo en la lista.
     * @return true si el juego está siendo prestado en el rango de fechas
     *         especificado, false de lo contrario.
     */
    public boolean isGameOnLoan(List<Loan> loans, LoanDto dto, int index) {
        LocalDate loanStartDate = loans.get(index).getStartDate();
        LocalDate loanEndDate = loans.get(index).getEndDate();

        return dto.getStartDate().isEqual(loanStartDate) || dto.getEndDate().isEqual(loanEndDate)
                || (dto.getStartDate().isBefore(loanEndDate) && dto.getEndDate().isAfter(loanStartDate));
    }

    /**
     * Verifica si el cliente tiene un préstamo activo en la lista de préstamos
     * proporcionada.
     *
     * @param loans La lista de todos los préstamos.
     * @param dto   El DTO de préstamo que contiene la información del cliente y las
     *              fechas de inicio y fin del préstamo.
     * @param index El índice del préstamo actual en la lista de préstamos.
     * @return true si el cliente tiene un préstamo activo en la lista de préstamos,
     *         false de lo contrario.
     */
    public boolean isClientWithActiveLoan(List<Loan> loans, LoanDto dto, int index) {
        LocalDate loanStartDate = loans.get(index).getStartDate();
        LocalDate loanEndDate = loans.get(index).getEndDate();

        return dto.getStartDate().isEqual(loanStartDate) || dto.getEndDate().isEqual(loanEndDate)
                || (dto.getStartDate().isBefore(loanEndDate) && dto.getEndDate().isAfter(loanStartDate));
    }

}
