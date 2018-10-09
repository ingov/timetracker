package de.spiegel.timetracker.business.timerecords.entity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author veithi
 */
public class TimeRecordIT {

    @Test
    public void timeRecordValidationTest() {

        Calendar calendar = new GregorianCalendar();

        // no UserId/date
        TimeRecord tr = new TimeRecord(0, null, false, 0, null, null, true, null);
        assertFalse(tr.isValide());

        // date not same day as startdate-day
        Calendar start = (Calendar) calendar.clone();
        start.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        tr = new TimeRecord(1, calendar, false, 0, start, calendar, true, null);
        assertFalse(tr.isValide());

        // enddate not past startdate
        tr = new TimeRecord(1, calendar, false, 0, calendar, calendar, true, null);
        assertFalse(tr.isValide());

        // fza and leisure day
        tr = new TimeRecord(1, calendar, false, 60, calendar, calendar, true, null);
        assertFalse(tr.isValide());

        // dispatcher and other entries
        tr = new TimeRecord(1, calendar, true, 0, calendar, calendar, true, null);
        assertFalse(tr.isValide());

        //valide dispatcher
        tr = new TimeRecord(1, calendar, true, 0, null, null, true, null);
        assertTrue(tr.isValide());

        //valide start-end
        Calendar end = (Calendar) calendar.clone();
        end.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);
        tr = new TimeRecord(1, calendar, false, 0, calendar, end, true, null);
        assertTrue(tr.isValide());

        //valide valide fza
        tr = new TimeRecord(1, calendar, false, 420, null, null, false, null);
        assertTrue(tr.isValide());

    }

}
