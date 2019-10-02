package be.norio.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val game = MutableLiveData<Game>()

    init {
        startNewGame()
    }

    fun getGame() = game as LiveData<Game>

    fun startNewGame() {
        game.value = Game()
    }

    fun play(row: Int, col: Int) {
        game.value?.play(row, col)
        game.value = game.value
    }
}