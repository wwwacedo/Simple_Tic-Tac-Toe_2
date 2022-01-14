package tictactoe

import java.lang.NumberFormatException
import kotlin.math.abs

data class Player(val marker: Char, val cells: MutableList<Int>)

class Cell(
    val line: Int,
    val column: Int,
    val index: Int,
    var status: Char = '_'
) {

    companion object {
        fun findStatus(cells: List<Cell>, inputIndex: Int): Char {
            for (cell in cells) {
                if (cell.index == inputIndex)
                    return cell.status
            }
            return ' '
        }

        fun finder(cells: List<Cell>, line: Int, column: Int): Cell {
            for (cell in cells) {
                if (cell.line == line && cell.column == column) return cell
            }
            return Cell(0, 0, 0, ' ')
        }
    }
}

object TicTacToe {
    private val players = listOf(Player('X', mutableListOf()), Player('O', mutableListOf()))

    private val winningConditions = listOf(
        listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // horizontal
        listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // vertical
        listOf(0, 4, 8), listOf(2, 4, 6), // diagonal
    )

    private val cells = listOf(
        Cell(1, 1, 0), Cell(1, 2, 1), Cell(1, 3, 2),
        Cell(2, 1, 3), Cell(2, 2, 4), Cell(2, 3, 5),
        Cell(3, 1, 6), Cell(3, 2, 7), Cell(3, 3, 8),
    )

    private val printer = {
        print(
            "---------\n" +
                    "| ${Cell.findStatus(cells, 0)} ${Cell.findStatus(cells, 1)} ${Cell.findStatus(cells, 2)} |\n" +
                    "| ${Cell.findStatus(cells, 3)} ${Cell.findStatus(cells, 4)} ${Cell.findStatus(cells, 5)} |\n" +
                    "| ${Cell.findStatus(cells, 6)} ${Cell.findStatus(cells, 7)} ${Cell.findStatus(cells, 8)} |\n" +
                    "---------\n"
        )
    }

    private fun count(ch: Char) = cells.count { it.status == ch }

    private fun verifyWinning(player: Player): String {
        for (condition in winningConditions) {
            if (condition[0] in player.cells && condition[1] in player.cells && condition[2] in player.cells) {
                return "${player.marker} wins"
            }
        }
        return "No winner"
    }

    private fun analyzer(player: Player): String {
        return if (abs(count('X') - count('O')) > 1) "Impossible"
        else if (verifyWinning(player) == "No winner" && count('_') == 0) "Draw"
        else verifyWinning(player)
    }

    fun playGame() {
        printer()
        loop@while (true) {
            for (player in players) {
                while (true) {
                    print("Enter the coordinates: ")
                    val coordinates = readLine()!!.split(" ")
                    try {
                        if (coordinates[0].toInt() in 1..3 && coordinates[1].toInt() in 1..3) {
                            val cell = Cell.finder(cells, coordinates[0].toInt(), coordinates[1].toInt())
                            if (cell.status == '_') {
                                cell.status = player.marker
                                player.cells.add(cell.index)
                                printer()
                                if (analyzer(player) != "No winner") {
                                    println(analyzer(player))
                                    break@loop
                                }
                                break
                            } else {
                                println("This cell is occupied! Choose another one!")
                            }
                        } else {
                            println("Coordinates should be from 1 to 3!")
                        }
                    } catch (e: NumberFormatException) {
                        println("You should enter numbers!")
                    }
                }
            }
        }
    }
}

fun main() {
    TicTacToe.playGame()
}
