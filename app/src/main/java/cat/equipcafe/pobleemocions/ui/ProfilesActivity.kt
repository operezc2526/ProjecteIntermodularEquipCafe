package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.databinding.ActivityProfilesBinding

/**
 * S01 · Perfils. Provisional: el RecyclerView de perfiles llega con AND-02.2.
 *
 * Esqueleto de navegación de AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): sin lógica de juego todavía.
 */
class ProfilesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        binding.btnProfile.setOnClickListener {
            goTo<PictogramAccessActivity>()
        }
        binding.btnNewProfile.setOnClickListener {
            goTo<AdultPinActivity>()
        }
        binding.btnAdultZone.setOnClickListener {
            goTo<AdultPinActivity>()
        }
    }
}
