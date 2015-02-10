package com.example.homework4;

import com.parse.Parse;
import com.parse.ParseUser;

import android.R.integer;
import android.R.string;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class MainActivity extends Activity //ActionBarActivity 
{

    private EditText EditInput;

    private Button ButtonNewGame;
    private Button ButtonOK;
    private Button butRanking;

    private int Number;
    private int Counter;  //static !?

    private TextView textTile;
    private TextView textUser;

    private Dialog dialogName;
    private Dialog ranking; //一個名為排行榜的新的彈跳視窗

    private ListView listRanking;
    String[] BallArray=new String[] {"1","2","3","4","5"};

    private ParseQuery<ParseObject> rankingData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // parse 初始化
        //Parse.initialize(this, "PC17TlNAa5805dHMEbHs1xUSoBtW6V0cn1rdMKQk", "GNuUoUJTZiBed6OpuxzLe3F0nv24t4ekguRtVNZx");
        Parse.initialize(this, "PC17TlNAa5805dHMEbHs1xUSoBtW6V0cn1rdMKQk", "GNuUoUJTZiBed6OpuxzLe3F0nv24t4ekguRtVNZx");
        // parse 登入
        //submitScore();
        //ParseUser.enableAutomaticUser();


        // TextView
        textTile=(TextView)findViewById(R.id.textView4);
        textUser=(TextView)findViewById(R.id.textView6);


        // EditText  //發現錯字
        EditInput=(EditText)findViewById(R.id.editText1);

        // Button
        ButtonNewGame=(Button)findViewById(R.id.button1);
        ButtonNewGame.setOnClickListener(newbutton);

        ButtonOK=(Button)findViewById(R.id.button2);
        ButtonOK.setOnClickListener(OK);

        butRanking=(Button)findViewById(R.id.button3);
        butRanking.setOnClickListener(Ranking);

        textTile.setText("("+Counter+")");

//        listRanking=(ListView)findViewById(R.id.listrank);
//
//         ArrayAdapter<String> adapterBalls=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,BallArray);
//
//          listRanking.setAdapter(adapterBalls);

        initGame();

    }

    private void initGame() {   //亂數產生
        Number=(int)(Math.random()*99)+1;
        Counter=1;
        textTile.setText("("+Counter+")"+"("+Number+")");
    }

    private void submitScore() {
        // 建立parse物件  - 表單名稱score board
        ParseObject pushObject = new ParseObject("scoreboard");
        // 放入使用者帳號
        pushObject.put("User_name", textUser.getText().toString());

        // 放入次數
        pushObject.put("score", Counter);
        pushObject.put("Answer", Number );

        pushObject.saveInBackground();
        //打入OAO


        textTile.setText(pushObject.getObjectId());

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private Button.OnClickListener OK =new Button.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {


            if (!EditInput.getText().toString().equals(""))
            {

                //Integer(他是一個工具箱)裡有一個parseInt可以將字串轉成數字
                int Input=Integer.parseInt(EditInput.getText().toString());


                if(Input==Number)
                {



                    showAlertDialog("恭喜你答對囉",
                            "答案為"+Integer.toString(Number)+"你總共猜了:"+ Integer.toString (Counter)+"次");
                    EditInput.setText("");

                    //輸入玩家姓名
                    dialogName=new Dialog(MainActivity.this);
                    dialogName.setTitle("猜數字");
                    dialogName.setCancelable(false);
                    dialogName.setContentView(R.layout.mydlg);

                    Button loginBtnOk=(Button)dialogName.findViewById(R.id.butOk);
                    Button loginBtnCancel=(Button)dialogName.findViewById(R.id.butCancel);

                    loginBtnOk.setOnClickListener(loginDlgBtnOKOnClkLis);
                    loginBtnCancel.setOnClickListener(loginDlgBtnCancelOnClkLis);
                    dialogName.show();

                }
                else if (Input>Number)
                {
                    Toast.makeText(getApplicationContext(), "數字太大", Toast.LENGTH_SHORT).show();
                    //showAlertDialog("錯誤!","猜錯囉!，數字太大");
                    Counter++;
                    EditInput.setText("");
                    textTile.setText("("+Counter+")");


                    //EditText edtUserName=(EditText)


                }
                else if (Input<Number)
                {

                    Toast.makeText(getApplicationContext(), "數字太小", Toast.LENGTH_SHORT).show();
                    //showAlertDialog("錯誤!","猜錯囉!，數字太小");
                    Counter++;
                    EditInput.setText("");
                    textTile.setText("("+Counter+")");
                }


            }

            else
                Toast.makeText(getApplicationContext(), "錯誤!請輸入字串", Toast.LENGTH_SHORT).show();
                //showAlertDialog("錯誤!","請輸入字串");


        }

    };

    private Button.OnClickListener Ranking=new Button.OnClickListener()
    {

        @Override
        public void onClick(View v) {
            // TODO 自動產生的方法 Stub
            ranking=new Dialog(MainActivity.this);
            ranking.setTitle("排行榜");
            ranking.setCancelable(false);
            ranking.setContentView(R.layout.ranking2);

            listRanking=(ListView)ranking.findViewById(R.id.listrank);
            //listRanking = new ListView(getParent());

//            ArrayAdapter<String> adapterBalls=new ArrayAdapter<String>
//                    (getApplicationContext(),android.R.layout.simple_list_item_1,BallArray);

            ArrayAdapter<String> adapterBalls=new ArrayAdapter<String>
                    (MainActivity.this,android.R.layout.simple_list_item_1,BallArray);


            listRanking.setAdapter(adapterBalls);



            //  TextView tv10 =(TextView) ranking.findViewById(R.id.textView10);
            //tv10.setText("hahahahahahahahahahahahhahahahhahahahahhahahahahahahaahhahahahahahhahaha");


            //rankingData = ParseQuery.getQuery("scoreboard");//建立一張新的表單


            //rankingData.findInBackground(new FindCallback<ParseObject>()  //與線上同步資料
            //{
              //  @Override
                //public void done(List<ParseObject> sList, ParseException e)   //資料會存於list中
                //{
                  //  if (e == null)
                    //    DisplayList(sList);
                //}

            //});

            Button rankingOk=(Button)ranking.findViewById(R.id.button);
            rankingOk.setOnClickListener(rankingOkOnClick);

            ranking.show();

        }

    };




    private void showAlertDialog(final String title, String msg) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton("確定", null);
        dialog.show();

    }

    //按下確認後跳出輸入玩家明子的介面
    private Button.OnClickListener loginDlgBtnOKOnClkLis = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO 自動產生的方法 Stub
            EditText edtUserName =(EditText)dialogName.findViewById(R.id.editText1);
            //
            textUser.setText(edtUserName.getText().toString());
            //
            submitScore();
            dialogName.cancel();
        }
    };
    //按下確認後跳出輸入玩家明子的介面


    private Button.OnClickListener loginDlgBtnCancelOnClkLis = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO 自動產生的方法 Stub
            dialogName.cancel();
        }


    };

    private Button.OnClickListener newbutton =new Button.OnClickListener () {

        @Override
        public void onClick(View v) {
            // TODO 自動產生的方法 Stub
            initGame();
        }
    };

    private Button.OnClickListener rankingOkOnClick = new Button.OnClickListener() {

        public void onClick(View v) {
            // TODO 自動產生的方法 Stub
            ranking.cancel();
        }
    };
 }