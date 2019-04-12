package technologies.acviewmodel.viewmodel

import android.arch.lifecycle.ViewModel
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)
