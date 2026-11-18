package cat.equipcafe.pobleemocions.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas locales (se ejecutan en el ordenador, sin emulador) del modelo de AND-02.1.
 */
class DataModelTest {

    private fun profile(username: String) =
        Profile(username = username, passwordHash = "hash-de-prueba", createdAtMillis = 1_000L)

    private fun level(
        levelId: Int,
        completed: Boolean = true,
        attempts: Int = 8,
        errors: Int = 0,
        help: Int = 0,
    ) = LevelResult(
        levelId = levelId,
        completed = completed,
        levelTimeMs = 60_000,
        nAttempts = attempts,
        errorsMade = errors,
        helpClicks = help,
    )

    private fun session(id: String, username: String = "GUINEU-07", levels: List<LevelResult>, completed: Boolean = false) =
        GameSession(
            sessionId = id,
            username = username,
            dateTimeMillis = 1_000L,
            sessionLengthSeconds = 300,
            score = 0,
            completed = completed,
            levels = levels,
        )

    // --- Profile: el seudónimo -----------------------------------------------------------

    @Test
    fun profile_acceptsPseudonymsInAnimalNnFormat() {
        listOf("GUINEU-07", "OS-12", "LLUNA-04").forEach {
            assertEquals(it, profile(it).username)
        }
    }

    @Test
    fun profile_rejectsAnythingThatCouldBeARealName() {
        listOf(
            "Marta García", // nombre real, con minúsculas, espacio y tilde
            "marta-07", // minúsculas
            "infant-07", // minúsculas (caso abierto de la guía de estilo)
            "AB", // demasiado corto
            "ABCDEFGHIJKLMNOPQ", // 17 caracteres
            "GUINEU 07", // espacio
            "GUINEU-07\n", // salto de línea final
            "",
        ).forEach { bad ->
            assertThrows(IllegalArgumentException::class.java) { profile(bad) }
        }
    }

    // --- GameSession: totales calculados ---------------------------------------------------

    @Test
    fun session_totalsAreComputedFromLevels() {
        val s = session(
            id = "id-1",
            levels = listOf(
                level(1, attempts = 8, errors = 2, help = 1),
                level(2, attempts = 9, errors = 1, help = 0),
                level(3, attempts = 4, errors = 0, help = 2),
            ),
        )
        assertEquals(21, s.nAttempts)
        assertEquals(3, s.errorsMade)
        assertEquals(3, s.helpClicks)
        assertEquals(3, s.levelReached)
        assertEquals(GAME_ID, s.gameId)
    }

    @Test
    fun session_abandonedIsTheOppositeOfCompleted() {
        val all = (1..4).map { level(it) }
        assertTrue(session("id-2", levels = all, completed = false).abandoned)
        assertFalse(session("id-3", levels = all, completed = true).abandoned)
    }

    @Test
    fun session_levelReachedIsTheHighestLevelPlayedNotTheCount() {
        val s = session("id-4", levels = listOf(level(1), level(2, completed = false)))
        assertEquals(2, s.levelReached)
    }

    @Test
    fun session_newIdLooksLikeAUuidV4AndIsUnique() {
        val uuid = Regex("^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")
        val a = GameSession.newId()
        val b = GameSession.newId()
        assertTrue(uuid.matches(a))
        assertTrue(a != b)
    }

    // --- Repositorios en memoria -----------------------------------------------------------

    @Test
    fun profileRepository_keepsInsertionOrderAndRejectsDuplicates() {
        val repo: ProfileRepository = InMemoryProfileRepository()
        assertTrue(repo.add(profile("GUINEU-07")))
        assertTrue(repo.add(profile("OS-12")))
        assertTrue(repo.add(profile("LLUNA-04")))
        assertFalse(repo.add(profile("OS-12")))

        assertEquals(listOf("GUINEU-07", "OS-12", "LLUNA-04"), repo.getAll().map { it.username })
        assertEquals("OS-12", repo.findByUsername("OS-12")?.username)
        assertNull(repo.findByUsername("NO-EXISTE-01"))
    }

    @Test
    fun sessionRepository_rejectsRepeatedSessionIdAndFiltersByProfile() {
        val repo: SessionRepository = InMemorySessionRepository()
        val levels = listOf(level(1))
        assertTrue(repo.add(session("a", "GUINEU-07", levels)))
        assertTrue(repo.add(session("b", "OS-12", levels)))
        assertTrue(repo.add(session("c", "GUINEU-07", levels)))
        assertFalse(repo.add(session("a", "OS-12", levels)))

        assertEquals(listOf("a", "b", "c"), repo.getAll().map { it.sessionId })
        assertEquals(listOf("a", "c"), repo.getByUsername("GUINEU-07").map { it.sessionId })
        assertEquals(emptyList<GameSession>(), repo.getByUsername("LLUNA-04"))
    }
}
