package io.github.austindewitt13.rot.dao;

import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;
import io.github.austindewitt13.rot.AlarmEventDatabase;
import io.github.austindewitt13.rot.model.Alarm;
import io.github.austindewitt13.rot.util.LiveDataTestUtil;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

/**
 * Tests the AlarmDao input to the AlarmEventDatabase.
 */
@SmallTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlarmDaoTest {

    private static AlarmEventDatabase db;
    private static AlarmDao dao;
    private static long alarmId;

    /**
     * Creates the rule for task.
     */
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    /**
     * @throws Exception
     * Tests and throws exception in AlarmDao.
     */
    @BeforeClass
    public static void setup() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AlarmEventDatabase.class).build();
        dao = db.alarmDao();
    }

    /**
     * Inserts a new Alarm into the AlarEventDatabase.
     */
    @Test
    public void insert() {
        Alarm alarm = new Alarm();
        alarm.setHour(12);
        alarm.setMinute(30);
        alarmId = dao.insert(alarm);
        assertTrue(alarmId > 0);
    }

    /**
     * Inserts a new Alarm into the AlarmEventDatabase and is expected to throw an Exception.
     */
    @Test(expected = Exception.class)
    public void insertNullAlarm() {
        Alarm alarm = new Alarm();
        dao.insert(alarm);
        fail("This shouldn't get here");
    }

    /**
     * @throws InterruptedException
     * Finds the id of an Alarm in the AlarmEventDatabase and asserts that it equals a certain value.
     */
    @Test
    public void postInsertFindById() throws InterruptedException {
        Alarm alarm = LiveDataTestUtil.getValue(dao.findById(alarmId));
        assertNotNull(alarm);
        assertEquals(12,alarm.getHour());
    }

    /**
     * @throws Exception
     * Closes the Database.
     */
    @AfterClass
    public static void tearDown() throws Exception {
        db.close();
    }
}