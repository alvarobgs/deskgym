package br.com.abg.deskgym.dao.impl;

import br.com.abg.deskgym.dao.ExperimentalClassScheduleDAO;
import br.com.abg.deskgym.entity.ExperimentalClassSchedule;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Implementação para o DAO de ExperimentalClassSchedule.
 * 
 * @author alvaro
 */
public class ExperimentalClassScheduleDAOImpl extends AbstractDAOImpl<ExperimentalClassSchedule> implements ExperimentalClassScheduleDAO {

    @Override
    public List<ExperimentalClassSchedule> getScheduleClassesByDate(Date startDate, Date endDate) {
        final String strQuery = "SELECT ecs FROM ExperimentalClassSchedule ecs " +
                                "WHERE ecs.date >= :sd AND ecs.date <= :ed";

        final Query query = this.getEm().createQuery(strQuery);
        query.setParameter("sd", startDate);
        query.setParameter("ed", endDate);

        return query.getResultList();
    }
}
