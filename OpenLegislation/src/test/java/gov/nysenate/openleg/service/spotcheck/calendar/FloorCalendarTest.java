package gov.nysenate.openleg.service.spotcheck.calendar;

import gov.nysenate.openleg.BaseTests;
import gov.nysenate.openleg.model.base.Version;
import gov.nysenate.openleg.model.calendar.Calendar;
import gov.nysenate.openleg.model.calendar.CalendarId;
import gov.nysenate.openleg.model.calendar.CalendarType;
import gov.nysenate.openleg.model.calendar.alert.CalendarAlertFile;
import gov.nysenate.openleg.model.calendar.spotcheck.CalendarEntryListId;
import gov.nysenate.openleg.model.spotcheck.SpotCheckObservation;
import gov.nysenate.openleg.processor.spotcheck.calendar.CalendarAlertProcessor;
import gov.nysenate.openleg.util.FileIOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Chenguang He on 5/24/2017.
 */
public class FloorCalendarTest extends BaseTests{

    private final File alertFile = FileIOUtils.getResourceFile("calendarAlerts/floor_cal_alert-2015-10-20150219T143033.html");

    @Autowired
    private CalendarAlertProcessor process;
    @Autowired
    private CalendarCheckService calendarCheckService;

    @Test
    public void floorCalendarTest() throws FileNotFoundException {
        Calendar dummyCalendar = new Calendar(new CalendarEntryListId(new CalendarId(10, 2015), CalendarType.FLOOR_CALENDAR, Version.ORIGINAL, 0));
        Calendar expected = process.process(new CalendarAlertFile(alertFile));
        List<SpotCheckObservation<CalendarEntryListId>> spotCheckObservation = calendarCheckService.checkAll(dummyCalendar,expected);
        CalendarEntryListId actual = spotCheckObservation.get(0).getKey();
        assertTrue(actual.getType().equals(CalendarType.FLOOR_CALENDAR));
        assertEquals(expected.getId().getCalNo(),actual.getCalendarId().getCalNo());
        assertEquals(expected.getId().getYear(),actual.getCalendarId().getYear());

        System.out.println(spotCheckObservation);
    }

}
