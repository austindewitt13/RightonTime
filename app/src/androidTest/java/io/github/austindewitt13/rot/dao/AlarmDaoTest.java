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

@SmallTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlarmDaoTest {

    private static AlarmEventDatabase db;
    private static AlarmDao dao;
    private static long alarmId;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @BeforeClass
    public static void setup() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AlarmEventDatabase.class).build();
        dao = db.alarmDao();
    }

    @Test
    public void insert() {
        Alarm alarm = new Alarm();
        alarm.setHour(12);
        alarm.setMinute(30);
        alarmId = dao.insert(alarm);
        assertTrue(alarmId > 0);
    }

    @Test(expected = Exception.class)
    public void insertNullAlarm() {
        Alarm alarm = new Alarm();
        dao.insert(alarm);
        fail("This shouldn't get here");
    }

    @Test
    public void postInsertFindById() throws InterruptedException {
        Alarm alarm = LiveDataTestUtil.getValue(dao.findById(alarmId));
        assertNotNull(alarm);
        assertEquals(12,alarm.getHour());
    }


    @AfterClass
    public static void tearDown() throws Exception {
        db.close();
    }
}