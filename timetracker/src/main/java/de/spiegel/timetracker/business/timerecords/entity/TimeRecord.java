package de.spiegel.timetracker.business.timerecords.entity;

import de.spiegel.timetracker.business.validation.CrossCheck;
import de.spiegel.timetracker.business.validation.ValidEntity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author veithi
 */
@Entity
@NamedQuery(name = TimeRecord.FIND_ALL, query = "SELECT t from TimeRecord t")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@CrossCheck
public class TimeRecord implements ValidEntity, Serializable {

    static final String PREFIX = "timetracker.entity.TimeRecord.";
    public static final String FIND_ALL = PREFIX + "findAll";

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private long userId;

    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar recordDate;
    private boolean dispatcher;
    private Integer fza;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar startdate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar enddate;
    private boolean leisure;
    private Long ticketId;

    public TimeRecord(long userId, Calendar date, boolean dispatcher, Integer fza, Calendar startdate, Calendar enddate, boolean leisure, Long ticketId) {
        this.userId = userId;
        this.recordDate = date;
        this.dispatcher = dispatcher;
        this.fza = fza;
        this.startdate = startdate;
        this.enddate = enddate;
        this.leisure = leisure;
        this.ticketId = ticketId;
    }

    public TimeRecord() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Calendar getDate() {
        return recordDate;
    }

    public void setDate(Calendar date) {
        this.recordDate = date;
    }

    public boolean isDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(boolean dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Integer getFza() {
        return fza;
    }

    public void setFza(Integer fza) {
        this.fza = fza;
    }

    public Calendar getStartdate() {
        return startdate;
    }

    public void setStartdate(Calendar startdate) {
        this.startdate = startdate;
    }

    public Calendar getEnddate() {
        return enddate;
    }

    public void setEnddate(Calendar enddate) {
        this.enddate = enddate;
    }

    public boolean isLeisure() {
        return leisure;
    }

    public void setLeisure(boolean leisure) {
        this.leisure = leisure;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public void setDone(boolean done) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isValide() {
        boolean isOk = recordDate != null && userId != 0;

        // recordDate not same day as startdate-day
        if (isOk && startdate != null) {
            isOk = recordDate.equals(startdate) || recordDate.after(startdate);
            if (isOk) {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
                isOk = fmt.format(recordDate.getTime()).equals(fmt.format(startdate.getTime()));
            }
        }
        //either start or enddate is null
        if (isOk) {
            isOk = (startdate == null && enddate == null) || (startdate != null && enddate != null);
        }
        // enddate not past startdate
        if (isOk && enddate != null && startdate != null) {
            isOk = enddate.after(startdate);
        }
        // fza and leisure day
        if (isOk) {
            isOk = !(leisure && fza != null && fza != 0);
        }
        return isOk;
    }

}
