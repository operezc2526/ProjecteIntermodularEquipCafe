package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.databinding.ActivityAdultPinBinding

/**
 * S29 · Zona adults · PIN. Provisional: sin PIN hasta SEG-02.2.
 *
 * Esqueleto de navegación de AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): sin lógica de juego todavía.
 */
class AdultPinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdultPinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdultPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        binding.btnCancel.setOnClickListener {
            finish()
        }
        binding.btnEnter.setOnClickListener {
            goTo<AdultPanelActivity>()
            finish()
        }
    }
}
