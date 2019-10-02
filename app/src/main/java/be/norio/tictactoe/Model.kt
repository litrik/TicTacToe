package be.norio.tictactoe

enum class Player {
    X, O
}

data class Game(val boardSize: Int = 3) {

    var turn = Player.X
    val squares = Array(boardSize) { Array<Player?>(boardSize) { null } }
    var finished = false
    var winner: Player? = null

    fun play(row: Int, col: Int) {
        if (row >= boardSize || col >= boardSize) {
            throw InvalidSquareException("Square ${row}-${col} does not exist")
        }
        if (squares[row][col] != null) {
            throw InvalidSquareException("Square ${row}-${col} already taken by ${squares[row][col]}")
        }
        squares[row][col] = turn
        turn = if (turn == Player.X) Player.O else Player.X
        updateGameState()
    }

    private fun updateGameState() {
        for (index in squares.indices) {
            // Check rows
            if ((0 until boardSize).map { squares[index][it] }.distinct().size == 1) {
                squares[index][0]?.let {
                    winner = it
                }
            }
            // Check cols
            if ((0 until boardSize).map { squares[it][index] }.distinct().size == 1) {
                squares[0][index]?.let {
                    winner = it
                }
            }
            // Check diagonal top-left to bottom-right
            if ((0 until boardSize).map { squares[it][it] }.distinct().size == 1) {
                squares[0][0]?.let {
                    winner = it
                }
            }
            // Check diagonal top-right to bottom-left
            if ((0 until boardSize).map { squares[it][boardSize - 1 - it] }.distinct().size == 1) {
                squares[0][boardSize - 1]?.let {
                    winner = it
                }
            }
        }
        finished = if (winner != null) {
            true
        } else {
            !squares.flatten().contains(null)
        }
    }
}

class InvalidSquareException(message: String) : Exception(message)

