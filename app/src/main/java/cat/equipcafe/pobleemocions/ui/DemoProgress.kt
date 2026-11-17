package cat.equipcafe.pobleemocions.ui

/**
 * PROVISIONAL (AND-03.1): progreso del mapa solo en memoria, para ver los estados de las farolas
 * (S05-S09) mientras no existe el progreso real (AND-03.2) ni persistencia validada.
 *
 * No guarda nada de ningún menor ni se escribe en disco. Se borra al cerrar la sesión
 * («Descansar» en el mapa vuelve a Perfils). AND-03.2 lo sustituye.
 */
object DemoProgress {

    const val LEVEL_COUNT = 4

    private val completed = mutableSetOf<Int>()

    val completedCount: Int get() = completed.size

    /** Primer nivel sin superar (1..4), o null si ya están los cuatro. */
    val currentLevel: Int? get() = (1..LEVEL_COUNT).firstOrNull { it !in completed }

    fun complete(levelId: Int) {
        completed += levelId
    }

    fun isCompleted(levelId: Int): Boolean = levelId in completed

    fun reset() {
        completed.clear()
    }
}
