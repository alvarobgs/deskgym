package br.com.abg.deskgym.dao;

import br.com.abg.deskgym.entity.ExperimentalClassSchedule;

import java.util.Date;
import java.util.List;

/**
 * Interface para manipulação de dados de ExperimentalClassSchedule.
 * 
 * @author alvaro
 */
public interface ExperimentalClassScheduleDAO extends AbstractDAO<ExperimentalClassSchedule> {

    /**
     * Carrega as aulas experimentais para determinado dia.
     *
     * @param startDate
     * @param endDate
     *
     * @return
     */
    List<ExperimentalClassSchedule> getScheduleClassesByDate(Date startDate, Date endDate);
    
}
