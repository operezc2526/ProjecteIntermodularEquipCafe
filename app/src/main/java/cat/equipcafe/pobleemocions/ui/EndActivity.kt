package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.databinding.ActivityEndBinding

/**
 * S28 · Final de sessió (Àlbum del poble). Provisional hasta AND-03.6.
 *
 * Esqueleto de navegación de AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): sin lógica de juego todavía.
 */
class EndActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEndBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        binding.btnBackMap.setOnClickListener {
            goBackTo<MapActivity>()
        }
    }
}
