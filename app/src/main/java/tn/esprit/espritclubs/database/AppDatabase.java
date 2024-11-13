package tn.esprit.espritclubs.database;

import android.content.Context;

import androidx.room.*;

import java.util.concurrent.*;

import tn.esprit.espritclubs.dao.UserDao;
import tn.esprit.espritclubs.entities.Cart;
import tn.esprit.espritclubs.entities.Medicament;
import tn.esprit.espritclubs.entities.OrderPlace;
import tn.esprit.espritclubs.entities.User;
import tn.esprit.espritclubs.utils.Converters;

@Database(entities = { User.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "clubs_database")
                            .allowMainThreadQueries() // Not recommended for production, should be avoided
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
