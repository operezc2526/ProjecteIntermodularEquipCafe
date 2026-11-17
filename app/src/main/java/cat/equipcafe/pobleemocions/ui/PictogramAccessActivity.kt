package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.databinding.ActivityPictogramAccessBinding

/**
 * S02-S04 · Acceso por 3 pictogramas. Provisional hasta AND-02.3 / SEG-01.3.
 *
 * Esqueleto de navegación de AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): sin lógica de juego todavía.
 */
class PictogramAccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPictogramAccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPictogramAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnEnter.setOnClickListener {
            goTo<MapActivity>()
        }
    }
}
