package cat.equipcafe.pobleemocions.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cat.equipcafe.pobleemocions.R
import cat.equipcafe.pobleemocions.databinding.ActivityPictogramAccessBinding

/**
 * S02-S04 · Acceso por 3 pictogramas.
 *
 * AND-03.1 (Joc Mòbil · Sprint 2 · 0489-RA2): solo el aspecto y el llenado visual de las casillas.
 * Provisional hasta AND-02.3 / SEG-01.3: aún no se compara con la secuencia guardada del perfil,
 * así que cualquier combinación de 3 pictogramas entra al mapa.
 */
class PictogramAccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPictogramAccessBinding

    /** Recursos de los pictogramas elegidos, en orden (máximo [SLOT_COUNT]). */
    private val chosen = mutableListOf<Int>()

    private lateinit var slots: List<Pair<View, View>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPictogramAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        slots = listOf(
            binding.slot1 to binding.slot1Picto,
            binding.slot2 to binding.slot2Picto,
            binding.slot3 to binding.slot3Picto,
        )
        slots.forEachIndexed { index, (slot, _) ->
            slot.contentDescription = getString(R.string.cd_slot_format, index + 1)
        }

        binding.btnBack.setOnClickListener { finish() }

        mapOf(
            binding.btnPictoSun to R.drawable.picto_sun,
            binding.btnPictoBall to R.drawable.picto_ball,
            binding.btnPictoTree to R.drawable.picto_tree,
            binding.btnPictoHouse to R.drawable.picto_house,
            binding.btnPictoFish to R.drawable.picto_fish,
            binding.btnPictoStar to R.drawable.picto_star,
        ).forEach { (card, pictoRes) ->
            card.setOnClickListener { pick(pictoRes) }
        }
    }

    /** Al volver desde el mapa la secuencia empieza de nuevo. */
    override fun onStart() {
        super.onStart()
        chosen.clear()
        showSlots()
    }

    private fun pick(pictoRes: Int) {
        if (chosen.size >= SLOT_COUNT) return
        chosen += pictoRes
        showSlots()
        if (chosen.size == SLOT_COUNT) {
            goTo<MapActivity>()
        }
    }

    private fun showSlots() {
        slots.forEachIndexed { index, (slot, picto) ->
            if (index < chosen.size) {
                slot.setBackgroundResource(R.drawable.bg_slot_filled)
                picto.setBackgroundResource(chosen[index])
            } else {
                slot.setBackgroundResource(R.drawable.bg_slot_empty)
                picto.background = null
            }
        }
    }

    private companion object {
        const val SLOT_COUNT = 3
    }
}
