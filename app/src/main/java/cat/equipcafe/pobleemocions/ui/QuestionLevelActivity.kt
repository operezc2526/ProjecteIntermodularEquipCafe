package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.R
import cat.equipcafe.pobleemocions.databinding.ActivityQuestionLevelBinding

/**
 * S10-S19 · Niveles 1 y 2 (pregunta y opciones). Provisional hasta AND-03.2 / AND-03.3.
 *
 * Esqueleto de navegación de AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): sin lógica de juego todavía.
 */
class QuestionLevelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionLevelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        val levelId = intent.getIntExtra(EXTRA_LEVEL_ID, 1)
        val levelName = if (levelId == 2) R.string.level_2_name else R.string.level_1_name
        binding.tvTitle.text = getString(R.string.level_title_format, levelId, getString(levelName))

        binding.btnRest.setOnClickListener {
            goTo<RestActivity>()
        }
        binding.btnComplete.setOnClickListener {
            DemoProgress.complete(levelId) // provisional hasta AND-03.2
            finish()
        }
    }

    companion object {
        /** Nivel a jugar: 1 (La Plaça) o 2 (El Parc). */
        const val EXTRA_LEVEL_ID = "level_id"
    }
}
