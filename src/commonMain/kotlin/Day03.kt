val day03 = day(3) {

    val segments = input.map { it.replace('.', ' ') }
        .map { l ->
            "\\D|\\w*".toRegex().findAll(l)
                .filter { it.value.isNotBlank() }
                .toList()
        }

    part1(expectedExampleOutput = 4361, expectedOutput = 527144) {
        segments.flatMapIndexed { y, l ->
            l.filter { s -> s.value.isNumeric() }
                .filter { s ->
                    val prev = segments.getOrNull(y - 1) ?: listOf()
                    val next = segments.getOrNull(y + 1) ?: listOf()
                    (l + prev + next)
                        .filterNot { it.value.isNumeric() }
                        .any { ol ->
                            val containedIn = s.range.containedIn(ol.range)
                            containedIn
                        }
                }
        }.sumOf { it.value.toInt() }
    }

    part2(expectedExampleOutput = 467835, expectedOutput = 81463996) {
        val gears = segments.mapIndexed { y, l ->
            l.filter { s -> s.value.isNumeric() }
            val prev = segments.getOrNull(y - 1) ?: listOf()
            val next = segments.getOrNull(y + 1) ?: listOf()
            val around = (l + prev + next)
            val flatMap = l.asSequence().filter { it.value == "*" }
                .map { c ->
                    around
                        .filter { it.value.isNumeric() }
                        .filter { a -> a.range.containedIn(c.range) }

                }
                .filter { it.size == 2 }
                .map { g -> g.map { it.value.toInt() } }
                .map { it.reduce { acc, i -> acc * i } }.toList()

            flatMap
        }.flatten()
        gears.sum()
    }
}

fun IntRange.containedIn(other: IntRange) =
    endInclusive + 1 >= other.first && start - 1 <= other.last