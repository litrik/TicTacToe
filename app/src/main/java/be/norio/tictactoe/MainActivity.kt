package be.norio.tictactoe

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var model: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this).get(GameViewModel::class.java)
        model.getGame().observe(this, Observer {
            updateUi(it)
        })
        attachListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_new -> {
                startNewGame()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startNewGame() {
        model.startNewGame()
    }

    private fun attachListeners() {
        squaresView.children.forEachIndexed { row, tableRow ->
            (tableRow as TableRow).children.forEachIndexed { col, button ->
                button.setOnClickListener {
                    play(row, col)
                }
            }
        }
    }

    private fun play(row: Int, col: Int) {
        try {
            // Toast.makeText(this, "You played ${row},${col}", Toast.LENGTH_SHORT).show()
            model.play(row, col)
        } catch (exception: Exception) {
            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUi(game: Game) {
        squaresView.children.forEachIndexed { row, tableRow ->
            (tableRow as TableRow).children.forEachIndexed { col, button ->
                button as TextView
                if (game.squares[row][col] == null) {
                    button.text = ""
                    button.isEnabled = !game.finished
                } else {
                    button.text = game.squares[row][col].toString()
                    button.isEnabled = false
                }
            }
        }
        if (game.finished) {
            if (game.winner == null) {
                messageView.text = getString(R.string.message_draw)
            } else {
                messageView.text = getString(R.string.message_winner, game.winner)
            }
        } else {
            messageView.text = getString(R.string.message_turn, game.turn)
        }
    }
}
