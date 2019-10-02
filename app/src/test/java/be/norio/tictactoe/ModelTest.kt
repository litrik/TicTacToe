package be.norio.tictactoe

import org.junit.Assert
import org.junit.Test

class ModelTest {

    @Test
    fun initialGameState() {
        val game = Game()
        Assert.assertEquals(false, game.finished)
        Assert.assertEquals(null, game.winner)
        Assert.assertEquals(Player.X, game.turn)
    }

    @Test(expected = InvalidSquareException::class)
    fun squareOutsideBoard() {
        val game = Game()
        game.play(5, 6)
    }

    @Test(expected = InvalidSquareException::class)
    fun squareAlreadyOccupied() {
        val game = Game()
        game.play(0, 0)
        game.play(0, 0)
    }

    @Test
    fun switchTurnsAfterPlaying() {
        val game = Game()
        Assert.assertEquals(Player.X, game.turn)
        game.play(0, 0)
        Assert.assertEquals(Player.O, game.turn)
    }

    @Test
    fun winWithRow() {
        // XXX
        // ...
        // OO.
        val game = Game()
        for (col in 0 until game.boardSize) {
            // Player.X plays in row 0
            game.play(0, col)
            if (col < game.boardSize - 1) {
                Assert.assertEquals(null, game.winner)
                Assert.assertEquals(false, game.finished)
                // Player.O plays in row 2
                game.play(2, col)
                Assert.assertEquals(null, game.winner)
            } else {
                Assert.assertEquals(Player.X, game.winner)
                Assert.assertEquals(true, game.finished)
            }
        }
    }

    @Test
    fun winWithCol() {
        // X.O
        // X.O
        // X..
        val game = Game()
        for (row in 0 until game.boardSize) {
            // Player.X plays in col 0
            game.play(row, 0)
            if (row < game.boardSize - 1) {
                Assert.assertEquals(null, game.winner)
                Assert.assertEquals(false, game.finished)
                // Player.O plays in col 2
                game.play(row, 2)
                Assert.assertEquals(null, game.winner)
            } else {
                Assert.assertEquals(Player.X, game.winner)
                Assert.assertEquals(true, game.finished)
            }
        }
    }

    @Test
    fun winWithBackSlash() {
        // XO.
        // .XO
        // ..X
        val game = Game()
        for (row in 0 until game.boardSize) {
            // Player.X plays in slash diagonal
            game.play(row, row)
            if (row < game.boardSize - 1) {
                Assert.assertEquals(null, game.winner)
                Assert.assertEquals(false, game.finished)
                // Player.O plays same row, next column
                game.play(row, (row + 1) % game.boardSize)
                Assert.assertEquals(null, game.winner)
            } else {
                Assert.assertEquals(Player.X, game.winner)
                Assert.assertEquals(true, game.finished)
            }
        }
    }

    @Test
    fun winWithSlash() {
        // O.X
        // .XO
        // X..
        val game = Game()
        for (row in 0 until game.boardSize) {
            // Player.X plays in backslash diagonal
            game.play(row, game.boardSize - 1 - row)
            if (row < game.boardSize - 1) {
                Assert.assertEquals(null, game.winner)
                Assert.assertEquals(false, game.finished)
                // Player.O plays same row, next column
                game.play(row, (game.boardSize - 1 - row + 1) % game.boardSize)
                Assert.assertEquals(null, game.winner)
            } else {
                Assert.assertEquals(Player.X, game.winner)
                Assert.assertEquals(true, game.finished)
            }
        }
    }

    @Test
    fun draw() {
        // XOX
        // XOO
        // OXX
        val game = Game()
        game.play(0, 0) // Player.X
        game.play(0, 1) // Player.O
        game.play(0, 2) // Player.X
        game.play(1, 2) // Player.O
        game.play(1, 0) // Player.X
        game.play(1, 1) // Player.O
        game.play(2, 2) // Player.X
        game.play(2, 0) // Player.O
        Assert.assertEquals(null, game.winner)
        Assert.assertEquals(false, game.finished)
        game.play(2, 1) // Player.X
        Assert.assertEquals(null, game.winner)
        Assert.assertEquals(true, game.finished)
    }


}
