package com.example.xox_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView sc_player1,sc_player2,status;
    Button[] buttons = new Button[9];
    Button reset,playagain;
    boolean isit_player1turn;
    int[] gamestate = {5,5,5,5,5,5,5,5,5};
    int[][] winPosition = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int pl1_scorecount,pl2_scorecount,round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sc_player1 = findViewById(R.id.scoreplayer1);
        sc_player2 = findViewById(R.id.scoreplayer2);
        status = findViewById(R.id.status);
        reset = findViewById(R.id.reset_btn);
        playagain = findViewById(R.id.play_againbtn);

        buttons[0] = findViewById(R.id.btn0);
        buttons[1] = findViewById(R.id.btn1);
        buttons[2] = findViewById(R.id.btn2);
        buttons[3] = findViewById(R.id.btn3);
        buttons[4] = findViewById(R.id.btn4);
        buttons[5] = findViewById(R.id.btn5);
        buttons[6] = findViewById(R.id.btn6);
        buttons[7] = findViewById(R.id.btn7);
        buttons[8] = findViewById(R.id.btn8);

        //buttons[0].setOnClickListener();

        for (int i=0; i< buttons.length; i++)
        {
            buttons[i].setOnClickListener(this);
        }

        isit_player1turn = true;
        pl1_scorecount = 0;
        pl2_scorecount =0;
        round =0;

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkWinner() || round==9) {
                    PlayAgain();
                    pl1_scorecount = 0;
                    pl2_scorecount = 0;
                    updateScore();
                }
            }
        });

        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkWinner() || round==9) {
                    PlayAgain();
                    updateScore();
                }
            }
        });
    }

    private void PlayAgain() {
        round=0;
        isit_player1turn=true;
        for (int j=0; j< buttons.length;j++){
            gamestate[j] =5;
            buttons[j].setText("");
        }
        status.setText("Status");
    }

    private void updateScore() {
        sc_player1.setText(Integer.toString(pl1_scorecount));
        sc_player2.setText(Integer.toString(pl2_scorecount));
    }

    private boolean checkWinner(){      //might take int[state] as param,then gamestates should be changed to state
        boolean winresult = false;
        for (int[] winPosition : winPosition){
            if (gamestate[winPosition[0]] == gamestate[winPosition[1]] && gamestate[winPosition[1]]==gamestate[winPosition[2]] && gamestate[winPosition[0]] !=5){
                winresult = true;
            }
        }
        return winresult;
    }

    @Override
    public void onClick(View view) {
        if (!((Button)view).getText().toString().equals(""))
        {
            return;
        } else if (checkWinner())
        {
            return;
        }

        String buttonId = view.getResources().getResourceEntryName(view.getId());
        int gamestateint = Integer.parseInt(buttonId.substring(buttonId.length()-1,buttonId.length()));

        if (isit_player1turn)
        {
            ((Button)view).setText("X");
            gamestate[gamestateint] = 0;
        }
        else
        {

            ((Button)view).setText("O");                //int bestMove = minimax(gamestate);
            gamestate[gamestateint] = 1;                //buttons[bestMove].setText("O");
            //gamestate[bestMove] = 1;
        }
        round++;

        if (checkWinner())
        {
            if (isit_player1turn)
            {
                pl1_scorecount++;
                updateScore();
                status.setText("Player 1 has won.");
            }
            else{
                pl2_scorecount++;
                updateScore();
                status.setText("Player 2 has won.");
            }
        }
        else if (round ==9)
        {
            status.setText("No Winner");
        }
        else
        {
            isit_player1turn = !isit_player1turn;
        }

    }
}