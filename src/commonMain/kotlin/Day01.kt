val day01 = day(1) {
    part1(expectedExampleOutput = 142, expectedOutput = 54159) {
        input.sumOf { l ->
            (l.first { it.isDigit() }.toString() + l.last { it.isDigit() }).toInt()
        }
    }

    part2(expectedExampleOutput = 142, expectedOutput = 53866) {
        val digitsAndLetters = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
            .mapIndexed { i, s -> s to (i+1).toString()  }.toMap()

        input.sumOf {l ->
            val replacementStart= digitsAndLetters.filter { l.contains(it.key) }.minByOrNull { l.indexOf(it.key) }?.toPair()
            val replacementEnd= digitsAndLetters.filter { l.contains(it.key) }.maxByOrNull { l.lastIndexOf(it.key) }?.toPair()

            val newLineStart = replacementStart?.run { l.replaceFirst(first,second) } ?: l
            val newLineEnd = replacementEnd?.run { l.replaceLast(first,second) } ?: l

            val result = (newLineStart.first { it.isDigit() }.toString()+ newLineEnd.last { it.isDigit() }).toInt()
            result
        }
    }
}
