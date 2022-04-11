package id.hikmah.binar.challenge4crud.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import id.hikmah.binar.challenge4crud.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAllUser(): List<User>

    @Query("SELECT * FROM User WHERE username = :query")
    fun getUser(query: String): User

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    fun checkLogin(username: String, password: String): List<User>

    @Query("SELECT id FROM User WHERE username = :username")
    fun getId(username: String): User

    @Insert(onConflict = REPLACE)
    fun insertUser(user: User): Long

    @Update
    fun updateUser(user: User): Int

    @Delete
    fun deleteUser(user: User): Int
}
