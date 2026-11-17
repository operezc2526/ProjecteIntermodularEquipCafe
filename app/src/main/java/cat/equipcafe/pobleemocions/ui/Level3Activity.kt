package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.databinding.ActivityLevel3Binding

/**
 * S20-S22 · Nivel 3 (La Plaça Gran). Provisional hasta AND-03.4.
 *
 * Esqueleto de navegación de AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): sin lógica de juego todavía.
 */
class Level3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLevel3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevel3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        binding.btnRest.setOnClickListener {
            goTo<RestActivity>()
        }
        binding.btnComplete.setOnClickListener {
            finish()
        }
    }
}
