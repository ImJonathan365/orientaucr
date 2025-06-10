package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.SimulationAttempt;
import cr.ac.ucr.orientaucr.orientaucr.repository.ISimulationAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimulationAttemptJPA {

    @Autowired
    private ISimulationAttemptRepository attemptRepo;

    @Transactional
    public void saveAttempt(SimulationAttempt attempt) {
        attemptRepo.save(attempt);
    }
}
