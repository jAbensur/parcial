package com.example.parcial;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.GridLayout;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

    private boolean playerXTurn = true;
    private final int[] gameState = new int[9];
    private final int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };
    private boolean gameActive = true;
    private final List<Integer> xPositions = new ArrayList<>();
    private final List<Integer> oPositions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        resetGame();
    }

    @SuppressLint("SetTextI18n")
    public void resetGame() {
        playerXTurn = true;
        gameActive = true;

        xPositions.clear();
        oPositions.clear();

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((Button) gridLayout.getChildAt(i)).setText("");
            gridLayout.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        }

        TextView textViewStatus = findViewById(R.id.textViewStatus);
        textViewStatus.setText("Jugador 1 (X) empieza");
    }

    @SuppressLint("SetTextI18n")
    public void onGridButtonClick(View view) {
        Button button = (Button) view;
        int buttonIndex = Integer.parseInt(button.getResources().getResourceEntryName(button.getId()).substring(6));

        if (gameState[buttonIndex] == 2 && gameActive) {
            if (playerXTurn) {
                if (xPositions.size() == 3) {
                    int firstPos = xPositions.remove(0);
                    gameState[firstPos] = 2;
                    Button firstButton = (Button) ((GridLayout) findViewById(R.id.gridLayout)).getChildAt(firstPos);
                    firstButton.setText("");
                    firstButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                }
                gameState[buttonIndex] = 0;
                xPositions.add(buttonIndex);
                button.setText("X");
                button.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            } else {
                if (oPositions.size() == 3) {
                    int firstPos = oPositions.remove(0);
                    gameState[firstPos] = 2;
                    Button firstButton = (Button) ((GridLayout) findViewById(R.id.gridLayout)).getChildAt(firstPos);
                    firstButton.setText("");
                    firstButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                }
                gameState[buttonIndex] = 1;
                oPositions.add(buttonIndex);
                button.setText("O");
                button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            }

            if (checkWinner()) {
                gameActive = false;
                TextView textViewStatus = findViewById(R.id.textViewStatus);
                textViewStatus.setText("Ganaron las " + (playerXTurn ? "X" : "O"));
            } else if (isBoardFull()) {
                TextView textViewStatus = findViewById(R.id.textViewStatus);
                textViewStatus.setText("Empate!");
            } else {
                playerXTurn = !playerXTurn;
                TextView textViewStatus = findViewById(R.id.textViewStatus);
                textViewStatus.setText("Turno de " + (playerXTurn ? "Jugador 1 (X)" : "Jugador 2 (O)"));
            }
        }
    }

    private boolean checkWinner() {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int state : gameState) {
            if (state == 2) {
                return false;
            }
        }
        return true;
    }

    public void onResetButtonClick(View view) {
        resetGame();
    }
}