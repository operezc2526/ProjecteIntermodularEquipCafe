package cat.equipcafe.pobleemocions.data

import java.util.UUID

/**
 * Partida (sesión) y resultado por nivel («tabla» de partidas).
 *
 * AND-02.1 (Joc Mòbil · Sprint 2 · 0489-RA1). Cada clase refleja un objeto del esquema JSON v1.1
 * (`AND-04_esquema_json_v1.1.json`): `session`, `level` y `extra`. Los nombres de Kotlin son la
 * versión camelCase de los del JSON (`level_time_ms` → `levelTimeMs`).
 *
 * Este archivo solo define los datos. Rellenarlos es AND-04.2 (grabador), guardarlos asociados al
 * perfil es AND-04.3 y convertirlos a JSON, con su validación contra el esquema, es AND-05.1.
 */

/** Identificador del juego en el catálogo de la base de datos (valor fijo del JSON v1.1). */
const val GAME_ID = "POBLE_EMOCIONS"

/**
 * Motivo por el que un nivel no se completó (`motiu_abandonament` en el JSON).
 * Los nombres coinciden con los valores del JSON, así que `name` se puede exportar tal cual.
 */
enum class AbandonReason {
    /** El niño pulsó el botón «Descansar». */
    DESCANSAR,

    /** Salió del nivel. */
    SORTIDA,

    /** La app pasó a segundo plano. */
    SEGON_PLA,
}

/** Errores por emoción confundida. Solo niveles 1 y 2. */
data class ErrorsByEmotion(
    val joy: Int = 0,
    val sadness: Int = 0,
    val anger: Int = 0,
    val fear: Int = 0,
)

/** Acciones de ayuda elegidas. Solo nivel 3 (no puntúa). */
data class HelpChoices(
    val hug: Int = 0,
    val share: Int = 0,
    val tellAdult: Int = 0,
)

/**
 * Métricas extra de un nivel (`extra` en el JSON). Cada nivel usa solo una parte:
 * niveles 1 y 2 → [errorsByEmotion]; nivel 3 → [choices]; nivel 4 → [cyclesCompleted] (0 a 3).
 * Que cada nivel respete esto lo comprueba AND-05.1 al validar contra el esquema.
 */
data class LevelExtra(
    val errorsByEmotion: ErrorsByEmotion? = null,
    val choices: HelpChoices? = null,
    val cyclesCompleted: Int? = null,
)

/**
 * Resultado de un nivel jugado (`level` en el JSON).
 *
 * @property levelId 1 La Plaça, 2 El Parc, 3 La Plaça Gran, 4 El Jardí de la Calma.
 * @property abandonReason solo si [completed] es false.
 * @property levelTimeMs tiempo en el nivel, en milisegundos.
 * @property errorsMade siempre 0 en los niveles 3 y 4 (no puntúan).
 * @property avgResponseMs tiempo medio de respuesta; opcional.
 */
data class LevelResult(
    val levelId: Int,
    val completed: Boolean,
    val abandonReason: AbandonReason? = null,
    val levelTimeMs: Int,
    val nAttempts: Int,
    val errorsMade: Int,
    val helpClicks: Int,
    val avgResponseMs: Int? = null,
    val extra: LevelExtra? = null,
)

/**
 * Una partida completa de un perfil (`session` en el JSON).
 *
 * Los totales que el JSON exige (`level_reached`, `n_attempts`, `errors_made`, `help_clicks`,
 * `abandoned`) NO se guardan: se calculan a partir de [levels] y [completed]. Así nunca pueden
 * contradecirse con el detalle por nivel, que es la regla del esquema.
 *
 * @property sessionId UUID v4. Clave de idempotencia: una misma partida no se importa dos veces
 *   (usar [newId]).
 * @property username seudónimo del perfil que jugó ([Profile.username]). Nunca un nombre real.
 * @property dateTimeMillis inicio de la partida en milisegundos desde 1970; AND-05.1 lo exporta
 *   como ISO 8601 con zona horaria.
 * @property sessionLengthSeconds duración total en segundos.
 * @property score solo suman los niveles 1 y 2 (10 puntos al primer intento, 5 al segundo).
 * @property completed true solo si se completan los cuatro niveles.
 * @property isSimulated true solo en las sesiones generadas por el simulador de Python.
 * @property levels un resultado por nivel jugado, sin repetir `levelId`. Una partida guardada
 *   debe tener al menos uno.
 */
data class GameSession(
    val sessionId: String,
    val username: String,
    val dateTimeMillis: Long,
    val sessionLengthSeconds: Int,
    val score: Int,
    val completed: Boolean,
    val isSimulated: Boolean = false,
    val levels: List<LevelResult>,
) {
    val gameId: String get() = GAME_ID

    /** Invariante del esquema: `abandoned = NOT completed`. */
    val abandoned: Boolean get() = !completed

    /** Mayor `levelId` jugado (0 solo si [levels] está vacío, algo que no se debe guardar). */
    val levelReached: Int get() = levels.maxOfOrNull { it.levelId } ?: 0

    val nAttempts: Int get() = levels.sumOf { it.nAttempts }
    val errorsMade: Int get() = levels.sumOf { it.errorsMade }
    val helpClicks: Int get() = levels.sumOf { it.helpClicks }

    companion object {
        /** Genera un `session_id` nuevo (UUID v4). */
        fun newId(): String = UUID.randomUUID().toString()
    }
}
