package tw.tonyyang.roomsqlite;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by tonyyang on 2017/12/29.
 */

@RunWith(AndroidJUnit4.class)
public class UserTest {
    private UserDao userDao;
    private AppDatabase appDatabase;

    @Before
    public void createDB() {
        Context context = InstrumentationRegistry.getTargetContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDao = appDatabase.userDao();
    }

    @After
    public void closeDB() {
        appDatabase.close();
    }

    @Test
    public void writeAndReadInList() throws Exception {
        User user = new User("Tony", "Yang");
        userDao.insertAll(user);
    }

    @Test
    public void queryByPrice() throws Exception {
        User user = userDao.findByName("Tony", "Yang");
        assert user != null;
    }

}
