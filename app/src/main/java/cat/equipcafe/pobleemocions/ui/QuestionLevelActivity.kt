package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.R
import cat.equipcafe.pobleemocions.databinding.ActivityQuestionLevelBinding

/**
 * S10-S19 · Niveles 1 y 2 (pregunta y 3 caras). Estados de cada ronda: pregunta, con ayuda,
 * reintento suave y acierto.
 *
 * AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): solo el aspecto del prototipo y un recorrido de demostración.
 * Provisional hasta AND-03.2 (nivel 1) y AND-03.3 (nivel 2), que aportan las rondas reales, el orden al azar,
 * la puntuación y el registro de métricas:
 *  - la pregunta es siempre la misma y las caras están en un orden fijo;
 *  - tocar una cara tras el acierto pasa a la ronda siguiente (la versión real avanzará sola);
 *  - tras 8 rondas el nivel queda superado y se vuelve al mapa.
 */
class QuestionLevelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionLevelBinding
    private lateinit var cards: List<View>

    private var levelId = 1
    private var round = 1
    private var retry = false
    private var helpUsed = false
    private var success = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        levelId = intent.getIntExtra(EXTRA_LEVEL_ID, 1).coerceIn(1, 2)
        binding.level1.root.visibility = if (levelId == 1) View.VISIBLE else View.GONE
        binding.level2.root.visibility = if (levelId == 2) View.VISIBLE else View.GONE

        cards = if (levelId == 1) {
            listOf(binding.level1.btnAnswer1, binding.level1.btnAnswer2, binding.level1.btnAnswer3)
        } else {
            listOf(binding.level2.btnChoice1, binding.level2.btnChoice2, binding.level2.btnChoice3)
        }
        cards.forEachIndexed { index, card -> card.setOnClickListener { onCardTapped(index) } }

        binding.btnHelp.setOnClickListener { onHelp() }
        binding.btnRest.setOnClickListener { goTo<RestActivity>() }
        binding.btnComplete.setOnClickListener { finishLevel() }

        render()
    }

    private fun onCardTapped(index: Int) {
        when {
            success -> nextRound()
            index == CORRECT_INDEX || retry -> { // tras un reintento suave cualquier cara cuenta como acierto
                success = true
                render()
            }
            else -> {
                retry = true
                render()
            }
        }
    }

    private fun onHelp() {
        if (success) return
        helpUsed = true
        render()
    }

    private fun nextRound() {
        round++
        retry = false
        helpUsed = false
        success = false
        if (round > ROUNDS) finishLevel() else render()
    }

    private fun finishLevel() {
        DemoProgress.complete(levelId) // provisional hasta AND-03.2
        finish()
    }

    private fun render() {
        renderRoundDots()
        binding.tvRound.text = getString(R.string.round_format, round, ROUNDS)

        // «Ajuda» atenúa la opción que no es correcta (no se puede tocar).
        val dim = helpUsed && !success
        cards.forEachIndexed { index, card ->
            val dimmed = dim && index == DIMMED_INDEX
            card.alpha = if (dimmed) DIMMED_ALPHA else 1f
            card.isEnabled = !dimmed
        }

        if (levelId == 1) renderLevel1() else renderLevel2()
    }

    private fun renderLevel1() = with(binding.level1) {
        promptCard.visibility = if (success) View.GONE else View.VISIBLE
        praiseRow.visibility = if (success) View.VISIBLE else View.GONE

        val bubbleVisible = !success && (retry || helpUsed)
        brisaRow.visibility = if (success || bubbleVisible) View.VISIBLE else View.GONE
        bubble.visibility = if (bubbleVisible) View.VISIBLE else View.GONE
        tvBubble.setText(if (retry) R.string.l1_retry else R.string.l1_hint)
    }

    private fun renderLevel2() = with(binding.level2) {
        val asking = !success && !retry
        characterFace.setBackgroundResource(
            when {
                success -> R.drawable.face_joy
                retry -> R.drawable.brisa
                else -> R.drawable.face_neutral
            }
        )
        characterSpeaker.visibility = if (asking) View.VISIBLE else View.GONE
        tvCharacterText.setText(
            when {
                success -> R.string.praise
                retry -> R.string.l2_retry
                else -> R.string.l2_question
            }
        )
    }

    /** Puntos de ronda: superadas en verde, la actual más grande y en amarillo, pendientes en gris. */
    private fun renderRoundDots() {
        val small = resources.getDimensionPixelSize(R.dimen.dot_size)
        val big = resources.getDimensionPixelSize(R.dimen.dot_size_current)
        val gap = resources.getDimensionPixelSize(R.dimen.dot_gap)
        binding.roundDots.removeAllViews()
        for (number in 1..ROUNDS) {
            val size = if (number == round) big else small
            val dot = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(size, size).apply {
                    marginStart = gap / 2
                    marginEnd = gap / 2
                }
                setBackgroundResource(
                    when {
                        number < round -> R.drawable.bg_dot_done
                        number == round -> R.drawable.bg_dot_current
                        else -> R.drawable.bg_dot_pending
                    }
                )
            }
            binding.roundDots.addView(dot)
        }
    }

    companion object {
        /** Nivel a jugar: 1 (La Plaça) o 2 (El Parc). */
        const val EXTRA_LEVEL_ID = "level_id"

        private const val ROUNDS = 8
        private const val CORRECT_INDEX = 1 // nivel 1: cara contenta; nivel 2: cara triste (ambas en el centro)
        private const val DIMMED_INDEX = 2  // la cara enfadada
        private const val DIMMED_ALPHA = 0.35f
    }
}
