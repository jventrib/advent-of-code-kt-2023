import kotlin.math.pow

val day04 = day(4) {
    val cards = input
        .mapIndexed { i, l ->
            l.substringAfter(':')
                .split('|')
                .map { it.split("\\D+".toRegex()) }
                .map { it.filter(String::isNotBlank) }
                .map { it.map(String::toInt) }
                .run { Card(i + 1, first(), last()) }
        }

    part1(expectedExampleOutput = 13, expectedOutput = 23235) {
        cards
            .map { c -> c.winning.intersect(c.player.toSet()).size }
            .filter { it > 0 }
            .sumOf { 2.0.pow(it - 1).toInt() }
    }

    part2(expectedExampleOutput = 30, expectedOutput = 5920640) {
        val queue = ArrayDeque<Card>()
        queue.addAll(cards)
        var total = cards.size
        while (queue.isNotEmpty()) {
            val c = queue.removeFirst()
            val matching = c.winning.intersect(c.player.toSet()).size
            val cardList = (c.number until c.number + matching).map { cards[it] }
            queue.addAll(cardList)
            total += cardList.size
        }
        total
    }
}

private data class Card(val number: Int, val winning: List<Int>, val player: List<Int>)