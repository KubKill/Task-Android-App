package pl.edu.pja.mp1

import android.app.Application
import pl.edu.pja.mp1.db.AppDatabase

class App : Application(){
    val database by lazy { AppDatabase.open(this)}

    override fun onCreate() {
        super.onCreate()
        database
    }
}