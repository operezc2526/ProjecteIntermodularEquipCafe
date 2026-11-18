package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.databinding.ActivityAdultPanelBinding

/**
 * S30 · Zona adults · Panell. Provisional: la lista de perfiles llega con AND-02.2.
 *
 * Esqueleto de navegación de AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): sin lógica de juego todavía.
 */
class AdultPanelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdultPanelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdultPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        binding.btnNewProfile.setOnClickListener {
            goTo<NewProfileActivity>()
        }
        binding.btnExport.setOnClickListener {
            goTo<ExportActivity>()
        }
        binding.btnExit.setOnClickListener {
            finish()
        }
    }
}
