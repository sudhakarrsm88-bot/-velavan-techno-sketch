package com.relevanttechnosketch.app

object EngineeringCalculator {

    enum class CADSoftware(val baseRate: Double) {
        SOLIDWORKS(600.0),
        CREO(750.0),
        AUTOCAD(400.0),
        ANSYS_FEA(1200.0)
    }

    enum class Complexity(val multiplier: Double) {
        SIMPLE(1.0),
        MEDIUM(1.8),
        ADVANCED(3.2)
    }

    fun calculateEstimate(
        software: CADSoftware,
        complexity: Complexity,
        componentCount: Int,
        isUrgent: Boolean
    ): Pair<Int, Int> {
        val count = componentCount.coerceAtLeast(1)
        val urgencyFactor = if (isUrgent) 1.5 else 1.0
        val base = software.baseRate * complexity.multiplier * count * urgencyFactor

        val minPrice = (base * 0.9).toInt()
        val maxPrice = (base * 1.25).toInt()
        return Pair(minPrice, maxPrice)
    }
}
