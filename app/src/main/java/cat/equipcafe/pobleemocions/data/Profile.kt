package cat.equipcafe.pobleemocions.data

/**
 * Perfil de un niño o niña («tabla» de perfiles).
 *
 * AND-02.1 (Joc Mòbil · Sprint 2 · 0489-RA1). Modelo alineado con el esquema JSON v1.1.
 *
 * Privacidad (Protecció dels Usuaris · 0490-RA5): aquí NUNCA va el nombre real del menor.
 * [username] es un seudónimo que genera la app (formato ANIMAL-NN, p. ej. GUINEU-07) y es
 * exactamente la misma cadena que se exporta en el campo `username` de cada sesión.
 *
 * @property username seudónimo único del perfil. Debe cumplir [USERNAME_REGEX].
 * @property passwordHash hash bcrypt de la «contraseña» (secuencia de pictogramas). Nunca texto
 *   plano. Lo calcula AND-02.3 / SEG-01.3; este modelo solo lo guarda.
 * @property createdAtMillis momento del alta, en milisegundos desde 1970 (`System.currentTimeMillis()`).
 */
data class Profile(
    val username: String,
    val passwordHash: String,
    val createdAtMillis: Long,
) {
    init {
        // Un perfil con un username fuera de formato produciría JSON inválido en todas sus sesiones.
        require(USERNAME_REGEX.matches(username)) {
            "username fuera de formato (mayúsculas, dígitos y guiones, 4–16 caracteres): $username"
        }
    }

    companion object {
        /** Mismo patrón que `username` en el esquema JSON v1.1. */
        val USERNAME_REGEX = Regex("^[A-Z0-9-]{4,16}$")
    }
}
