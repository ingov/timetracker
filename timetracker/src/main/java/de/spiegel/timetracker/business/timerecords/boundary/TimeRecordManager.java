package de.spiegel.timetracker.business.timerecords.boundary;

import de.spiegel.timetracker.business.timerecords.entity.TimeRecord;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author veithi
 */
@Stateless
public class TimeRecordManager {

    @PersistenceContext
    EntityManager em;


    public List<TimeRecord> all() {
        return this.em.createNamedQuery(TimeRecord.findAll, TimeRecord.class)
                .getResultList();
    }

    public TimeRecord save(TimeRecord timeRecord) {
        return this.em.merge(timeRecord);
    }
    public TimeRecord findById(long id) {
        return this.em.find(TimeRecord.class, id);
    }

    public void delete(long id) {
        try {
            TimeRecord reference = this.em.getReference(TimeRecord.class, id);
            this.em.remove(reference);
        } catch (EntityNotFoundException e) {
            // we want to remove it...
        }
    }

    public TimeRecord updateStatus(long id, boolean done) {
        TimeRecord timeRecord = this.findById(id);
        if (timeRecord == null) {
            return null;
        }
        timeRecord.setDone(done);
        return timeRecord;
    }

}
