package com.example.ecommerce_app.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File





@Database(entities = [User::class, Users::class], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Returns the DAO for this application.
     * @return The DAO for this application.
     */
    abstract fun getUserDao(): UserDao

    abstract fun getUsersDao(): UsersDao

    companion object {

        private const val TAG = "AppDatabase"

        const val VERSION = 2
        private const val DATABASE_NAME = "inventory_database.db"

        @Volatile
        private var instance: AppDatabase? = null

        /**
         * Gets and returns the database instance if exists; otherwise, builds a new database.
         * @param context The context to access the application context.
         * @return The database instance.
         */
        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        /**
         * Creates and returns the callback object to execute when the database is first created.
         * @return The callback object to execute when the database is first created.
         */
        private fun appDatabaseCallback(): Callback = object : Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d(TAG, "Database has been created.")

                // Throws exception
                CoroutineScope(Dispatchers.IO).launch {
                    instance?.getUserDao()?.let{

                    }
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Log.d(TAG, "Database has been opened.")
            }
        }

        /**
         * Builds and returns the database.
         * @param appContext The application context to reference.
         * @return The built database.
         */
        private fun buildDatabase(appContext: Context): AppDatabase {
            val filesDir = appContext.getExternalFilesDir(null)
            val dataDir = File(filesDir, "data")
            if (!dataDir.exists())
                dataDir.mkdir()

            val builder =
                Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    File(dataDir, DATABASE_NAME).toString()
                ).fallbackToDestructiveMigration()

            return builder.build()
        }

    }
}