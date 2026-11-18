package cat.equipcafe.pobleemocions.data

/**
 * Acceso a los perfiles guardados.
 *
 * AND-02.1 (Joc Mòbil · Sprint 2 · 0489-RA1). Las pantallas hablan con esta interfaz y no saben
 * dónde se guardan los datos. Hoy se guardan en memoria; cuando la profesora valide la tecnología
 * de almacenamiento (Room u otra) se escribe otra clase que implemente esta misma interfaz y las
 * pantallas no cambian.
 */
interface ProfileRepository {
    /** Todos los perfiles, en el orden en que se dieron de alta. */
    fun getAll(): List<Profile>

    /** El perfil con ese [username], o null si no existe. */
    fun findByUsername(username: String): Profile?

    /** Da de alta [profile]. Devuelve false, sin cambiar nada, si ya hay un perfil con ese username. */
    fun add(profile: Profile): Boolean
}

/**
 * Implementación provisional: los perfiles viven solo mientras la app esté abierta
 * (se pierden al cerrarla del todo).
 */
class InMemoryProfileRepository : ProfileRepository {

    // LinkedHashMap conserva el orden de inserción: las tarjetas de Perfils no cambian de sitio.
    private val profiles = LinkedHashMap<String, Profile>()

    override fun getAll(): List<Profile> = profiles.values.toList()

    override fun findByUsername(username: String): Profile? = profiles[username]

    override fun add(profile: Profile): Boolean {
        if (profiles.containsKey(profile.username)) return false
        profiles[profile.username] = profile
        return true
    }
}
