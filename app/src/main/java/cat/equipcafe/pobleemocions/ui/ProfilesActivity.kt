package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.databinding.ActivityProfilesBinding

/**
 * S01 · Perfils. Provisional: las tres cartas son de ejemplo; el RecyclerView de perfiles llega con AND-02.2.
 *
 * AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): navegación y aspecto del prototipo, sin lógica de juego todavía.
 */
class ProfilesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        listOf(binding.btnProfile, binding.btnProfile2, binding.btnProfile3).forEach { card ->
            card.setOnClickListener { goTo<PictogramAccessActivity>() }
        }
        // Crear perfiles es una acción de adultos: pasa por el PIN (0490-RA5).
        binding.btnNewProfile.setOnClickListener { goTo<AdultPinActivity>() }
        binding.btnAdultZone.setOnClickListener { goTo<AdultPinActivity>() }
    }
}
