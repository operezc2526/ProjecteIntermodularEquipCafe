package cat.equipcafe.pobleemocions.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Ayudas de navegación y de ventana compartidas por todas las pantallas.
 *
 * Solo usan Intents explícitos entre Activities (tecnología permitida en el alcance):
 * sin Navigation Component ni ViewModel.
 */

/** Abre la pantalla [T]; [configure] permite añadir extras al Intent. */
inline fun <reified T : Activity> Activity.goTo(configure: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(configure))
}

/**
 * Vuelve a la pantalla [T] que ya está en la pila (cerrando las que hay encima).
 * Si no estuviera, la crea. Se usa para «Descansar», «Anar al mapa» y «Tornar al mapa».
 */
inline fun <reified T : Activity> Activity.goBackTo() {
    val intent = Intent(this, T::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    startActivity(intent)
}

/**
 * Desde Android 15 (y con targetSdk 35 o superior) las ventanas se dibujan de borde a borde.
 * Este relleno evita que botones y textos queden bajo las barras del sistema y respeta
 * el margen mínimo de 24 dp de la guía de estilo AND-01.2.
 */
fun View.applySystemBarsPadding() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets ->
        val bars = windowInsets.getInsets(
            WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
        )
        view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
        WindowInsetsCompat.CONSUMED
    }
}
