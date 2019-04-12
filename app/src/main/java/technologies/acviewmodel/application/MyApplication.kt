package technologies.acviewmodel.application

import android.app.Application
import android.content.Context
import technologies.acviewmodel.networking.NetworkModule

class MyApplication : Application() {

    val component: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {

        fun getApplicationComponent(context: Context): ApplicationComponent? {
            return (context.applicationContext as MyApplication).component
        }
    }
}
