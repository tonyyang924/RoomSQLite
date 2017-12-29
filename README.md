
參考官網說明實作：

https://developer.android.com/training/data-storage/room/index.html

---

User.java

```java
@Entity
public class User {
    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.
}
```

UserDao.java
```java
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
           + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
```

AppDatabase.java
```java
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
```

After creating the files above, you get an instance of the created database using the following code:
```java
AppDatabase db = Room.databaseBuilder(getApplicationContext(),
        AppDatabase.class, "database-name").build();
```

---

# Unit Test

Create a test in androidTest, following above:

```java
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
```

For practice, create a db on memory using this:
```java
appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
```
then define some test in this class if all test passed you can do anything about DB CRUD.