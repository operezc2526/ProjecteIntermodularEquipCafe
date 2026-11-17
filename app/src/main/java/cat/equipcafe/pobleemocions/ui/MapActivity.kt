package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.R
import cat.equipcafe.pobleemocions.databinding.ActivityMapBinding

/**
 * S05-S09 · Mapa de faroles: 4 niveles en fila, cada uno con su farola, y barra de progreso.
 *
 * AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): aspecto del prototipo y estados de los niveles.
 * El progreso viene de [DemoProgress] (en memoria, provisional) hasta AND-03.2.
 */
class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding

    /** Vistas de cada nivel, en orden (nivel 1 = índice 0). */
    private class LevelViews(val circle: TextView, val halo: View, val lamp: View, val nameRes: Int)

    private lateinit var levels: List<LevelViews>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        levels = listOf(
            LevelViews(binding.btnLevel1, binding.halo1, binding.lamp1, R.string.level_1_name),
            LevelViews(binding.btnLevel2, binding.halo2, binding.lamp2, R.string.level_2_name),
            LevelViews(binding.btnLevel3, binding.halo3, binding.lamp3, R.string.level_3_name),
            LevelViews(binding.btnLevel4, binding.halo4, binding.lamp4, R.string.level_4_name),
        )

        binding.btnLevel1.setOnClickListener {
            goTo<QuestionLevelActivity> { putExtra(QuestionLevelActivity.EXTRA_LEVEL_ID, 1) }
        }
        binding.btnLevel2.setOnClickListener {
            goTo<QuestionLevelActivity> { putExtra(QuestionLevelActivity.EXTRA_LEVEL_ID, 2) }
        }
        binding.btnLevel3.setOnClickListener { goTo<Level3Activity>() }
        binding.btnLevel4.setOnClickListener { goTo<Level4Activity>() }
        binding.btnRest.setOnClickListener { goBackTo<ProfilesActivity>() }
        binding.btnEnd.setOnClickListener { goTo<EndActivity>() }
    }

    /** Al volver de un nivel el mapa se repinta con el progreso actualizado. */
    override fun onResume() {
        super.onResume()
        render()
    }

    private fun render() {
        val lit = DemoProgress.completedCount
        val current = DemoProgress.currentLevel

        levels.forEachIndexed { index, level ->
            val levelId = index + 1
            val name = getString(level.nameRes)
            when {
                DemoProgress.isCompleted(levelId) -> {
                    level.circle.setBackgroundResource(R.drawable.bg_level_done)
                    level.circle.text = ""
                    level.circle.contentDescription =
                        getString(R.string.cd_level_done_format, levelId, name)
                    level.halo.background = null
                    level.lamp.setBackgroundResource(R.drawable.lamp_on)
                }
                levelId == current -> {
                    level.circle.setBackgroundResource(R.drawable.bg_level_current)
                    level.circle.text = levelId.toString()
                    level.circle.contentDescription =
                        getString(R.string.cd_level_current_format, levelId, name)
                    level.halo.setBackgroundResource(R.drawable.bg_level_halo)
                    level.lamp.setBackgroundResource(R.drawable.lamp_off)
                }
                else -> {
                    level.circle.setBackgroundResource(R.drawable.bg_level_locked)
                    level.circle.text = ""
                    level.circle.contentDescription =
                        getString(R.string.cd_level_locked_format, levelId, name)
                    level.halo.background = null
                    level.lamp.setBackgroundResource(R.drawable.lamp_off)
                }
            }
            // Solo el nivel actual se puede abrir: los superados y los bloqueados no responden.
            level.circle.isEnabled = levelId == current
        }

        binding.tvStars.text = lit.toString()
        binding.tvProgress.text = if (lit == DemoProgress.LEVEL_COUNT) {
            getString(R.string.map_progress_done)
        } else {
            getString(R.string.map_progress_format, lit)
        }
        setWeight(binding.progressFill, lit.toFloat())
        setWeight(binding.progressRest, (DemoProgress.LEVEL_COUNT - lit).toFloat())
        binding.btnEnd.visibility = if (lit == DemoProgress.LEVEL_COUNT) View.VISIBLE else View.GONE
    }

    private fun setWeight(view: View, weight: Float) {
        val params = view.layoutParams as LinearLayout.LayoutParams
        params.weight = weight
        view.layoutParams = params
    }
}
