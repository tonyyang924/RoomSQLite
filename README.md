
## Android Room Persistence Library

---

User.java

```java
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
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
    public static final String DATABASE_NAME = "SimpleRoomDatabase";
    private static AppDatabase INSTANCE;
    public abstract UserDao userDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
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

---

### Reference:
https://medium.com/@ajaysaini.official/building-database-with-room-persistence-library-ecf7d0b8f3e9
https://medium.com/@magdamiu/android-room-persistence-library-97ad0d25668e
https://developer.android.com/training/data-storage/room/index.html