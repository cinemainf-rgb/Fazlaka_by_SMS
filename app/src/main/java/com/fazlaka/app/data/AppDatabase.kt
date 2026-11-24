package com.fazlaka.app.data
import androidx.room.*
@Entity(tableName = "wallets") data class Wallet(@PrimaryKey val id: String, val name: String, val balance: Long, val limit: Long, val type: String)
@Entity(tableName = "products") data class Product(@PrimaryKey(autoGenerate = true) val id: Int = 0, val name: String, val buy: Long, val sell: Long, val qty: Int, val threshold: Int, val cat: String)
@Entity(tableName = "moves") data class Move(@PrimaryKey(autoGenerate = true) val id: Int = 0, val date: String, val time: String, val title: String, val detail: String, val amount: Long)
@Dao interface WalletDao { @Query("SELECT * FROM wallets") suspend fun all(): List<Wallet>; @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(w: Wallet); @Update suspend fun update(w: Wallet) }
@Dao interface ProductDao { @Query("SELECT * FROM products") suspend fun all(): List<Product>; @Insert suspend fun insert(p: Product); @Update suspend fun update(p: Product); @Delete suspend fun delete(p: Product) }
@Dao interface MoveDao { @Query("SELECT * FROM moves ORDER BY id DESC LIMIT 100") suspend fun all(): List<Move>; @Insert suspend fun insert(m: Move) }
@Database(entities = [Wallet::class, Product::class, Move::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() { abstract fun walletDao(): WalletDao; abstract fun productDao(): ProductDao; abstract fun moveDao(): MoveDao }
