package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.databinding.ActivityMapBinding

/**
 * S05-S09 · Mapa del poble. Provisional: farolas y progreso llegan con AND-03.2.
 *
 * Esqueleto de navegación de AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): sin lógica de juego todavía.
 */
class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        binding.btnLevel1.setOnClickListener {
            goTo<QuestionLevelActivity> { putExtra(QuestionLevelActivity.EXTRA_LEVEL_ID, 1) }
        }
        binding.btnLevel2.setOnClickListener {
            goTo<QuestionLevelActivity> { putExtra(QuestionLevelActivity.EXTRA_LEVEL_ID, 2) }
        }
        binding.btnLevel3.setOnClickListener {
            goTo<Level3Activity>()
        }
        binding.btnLevel4.setOnClickListener {
            goTo<Level4Activity>()
        }
        binding.btnRest.setOnClickListener {
            goBackTo<ProfilesActivity>()
        }
        binding.btnEnd.setOnClickListener {
            goTo<EndActivity>()
        }
    }
}
