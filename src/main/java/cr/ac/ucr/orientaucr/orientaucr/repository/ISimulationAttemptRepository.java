/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.SimulationAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author luisr
 */

public interface ISimulationAttemptRepository extends JpaRepository<SimulationAttempt, String> {

}
