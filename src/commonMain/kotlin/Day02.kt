val day02 = day(2) {
    part1(expectedExampleOutput = 8, expectedOutput = 2486) {
        input.mapIndexed { i, l ->
            val map = l.substringAfter(':').split(';').all { s ->
                s.split(',').map { c ->
                    c.trim().split(' ').run { Cube.valueOf(last()) to first().toInt() }
                }.all {
                    it.second <= it.first.max
                }
            }
            if (map) i + 1 else 0

        }.sum()
    }

    part2(expectedExampleOutput = 2286, expectedOutput = 87984) {
        input.sumOf { l ->
            l.substringAfter(':').split(';')
                .flatMap { s ->
                    s.split(',').map { c ->
                        c.trim().split(' ').run { Cube.valueOf(last()) to first().toInt() }
                    }
                }
                .groupBy { it.first }
                .mapValues { it.value.maxOf { it.second } }
                .values.reduce { acc, i -> acc * i }
        }
    }
}

enum class Cube(val max: Int) {
    red(12), green(13), blue(14)
}