package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.databinding.ActivityLevel4Binding

/**
 * S23-S27 · Nivel 4 (El Jardí de la Calma). Provisional: opcional, sin tarea en el plan.
 *
 * Esqueleto de navegación de AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): sin lógica de juego todavía.
 */
class Level4Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLevel4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevel4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        binding.btnRest.setOnClickListener {
            goTo<RestActivity>()
        }
        binding.btnComplete.setOnClickListener {
            DemoProgress.complete(4) // provisional hasta AND-03.2
            finish()
        }
    }
}
