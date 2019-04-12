package technologies.acviewmodel.application

import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    internal fun providesContext(): Context {
        return context
    }
}
