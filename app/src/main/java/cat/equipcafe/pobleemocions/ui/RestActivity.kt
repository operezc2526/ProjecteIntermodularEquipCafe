package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.databinding.ActivityRestBinding

/**
 * S14/S19/S22/S27 · Descansar. Provisional: el registro del abandono llega con AND-04.2.
 *
 * Esqueleto de navegación de AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): sin lógica de juego todavía.
 */
class RestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        binding.btnContinue.setOnClickListener {
            finish()
        }
        binding.btnMap.setOnClickListener {
            goBackTo<MapActivity>()
        }
    }
}
