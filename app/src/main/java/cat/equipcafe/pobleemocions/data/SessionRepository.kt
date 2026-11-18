package cat.equipcafe.pobleemocions.data

/**
 * Acceso a las partidas guardadas.
 *
 * AND-02.1 (Joc Mòbil · Sprint 2 · 0489-RA1). Igual que [ProfileRepository]: las pantallas usan la
 * interfaz y hoy se guarda en memoria. Quién llama a [add] al terminar una partida y cómo se
 * enlaza con el perfil activo es AND-04.3; la exportación a JSON es AND-05.1.
 */
interface SessionRepository {
    /**
     * Guarda [session]. Devuelve false, sin cambiar nada, si ya hay una partida con ese
     * `sessionId` (el UUID es la clave de idempotencia del esquema JSON).
     */
    fun add(session: GameSession): Boolean

    /** Todas las partidas, en el orden en que se guardaron. */
    fun getAll(): List<GameSession>

    /** Las partidas del perfil [username], en el orden en que se guardaron. */
    fun getByUsername(username: String): List<GameSession>
}

/**
 * Implementación provisional: las partidas viven solo mientras la app esté abierta.
 */
class InMemorySessionRepository : SessionRepository {

    private val sessions = LinkedHashMap<String, GameSession>()

    override fun add(session: GameSession): Boolean {
        if (sessions.containsKey(session.sessionId)) return false
        sessions[session.sessionId] = session
        return true
    }

    override fun getAll(): List<GameSession> = sessions.values.toList()

    override fun getByUsername(username: String): List<GameSession> =
        sessions.values.filter { it.username == username }
}
