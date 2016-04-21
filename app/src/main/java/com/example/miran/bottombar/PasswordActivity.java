package com.example.miran.bottombar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class PasswordActivity extends Activity {

    int[] realPassword = {0,0,0,0};
    int[] input;
    int[] first_input;
    boolean check_again; //false면 두번째 입력 안받은거.
    int count;
    TextView star1,star2, star3,star4,pwd_notify;
    int mode;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_layout);
        Intent intent = getIntent();
        mode = intent.getIntExtra("mode", -1);
        init();
    }
    void init(){
        /*추가 : 저장된 비밀번호 불러오기*/
        input = new int[4];
        if(mode == 1){
            first_input = new int[4];
            check_again = false;
        }
        int count = 0;
        star1 = (TextView)findViewById(R.id.star1);
        star2 = (TextView)findViewById(R.id.star2);
        star3 = (TextView)findViewById(R.id.star3);
        star4 = (TextView)findViewById(R.id.star4);
        pwd_notify = (TextView)findViewById(R.id.pwd_notify);
        context = this;
        //비밀번호 확인
        if(mode==0){
            pwd_notify.setText("비밀번호를 입력하세요.");
        }
        //비밀번호 변경
        else if(mode==1){
            pwd_notify.setText("변경할 비밀번호를 입력하세요.");
        }
    }

    public void btnClick(View v)
    {
        switch (v.getId())
        {
            case R.id.one:
                input[count++] = 1;
                break;
            case R.id.two:
                input[count++] = 2;
                break;
            case R.id.three:
                input[count++] = 3;
                break;
            case R.id.four:
                input[count++] = 4;
                break;
            case R.id.five:
                input[count++] = 5;
                break;
            case R.id.six:
                input[count++] = 6;
                break;
            case R.id.seven:
                input[count++] = 7;
                break;
            case R.id.eight:
                input[count++] = 8;
                break;
            case R.id.nine:
                input[count++] = 9;
                break;
            case R.id.zero:
                input[count++] = 0;
                break;
            case R.id.backspace:
                break;
        }
        if(count==1) {
            if (v.getId() != R.id.backspace)
                star1.setTextColor(ContextCompat.getColor(this, R.color.brown));
            else {
                star1.setTextColor(ContextCompat.getColor(this, R.color.white));
                count--;
            }
        }
        else if(count == 2) {
            if (v.getId() != R.id.backspace)
                star2.setTextColor(ContextCompat.getColor(this, R.color.brown));
            else {
                star2.setTextColor(ContextCompat.getColor(this, R.color.white));
                count--;
            }
        }
        else if(count == 3) {
            if (v.getId() != R.id.backspace)
                star3.setTextColor(ContextCompat.getColor(this, R.color.brown));
            else {
                star3.setTextColor(ContextCompat.getColor(this, R.color.white));
                count--;
            }
        }
        else if(count == 4) {
            if (v.getId() != R.id.backspace) {
                star4.setTextColor(ContextCompat.getColor(this, R.color.brown));
                checkPassword();
            }else {
                star4.setTextColor(ContextCompat.getColor(this, R.color.white));
                count--;
            }
        }
    }
    void checkPassword(){
        if(mode==1&& check_again) {
            if(first_input[0]== input[0]&& first_input[1] == input[1]&& first_input[2] == input[2]&& first_input[3] == input[3]) {
                /* 추가 : 바뀐 비밀번호 디비에 저장.*/
                for(int i = 0 ; i<4 ; i++)
                    realPassword[i] = input[i];
                pwd_notify.setText("변경되었습니다!");
                TimerTask timertask = new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                    }};
                Timer timer = new Timer();
                timer.schedule(timertask, 500);
            }
            else{
                pwd_notify.setText("두 번호가 같지 않습니다.");
                Vibrator vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(500);
                final android.os.Handler mHandler = new android.os.Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        pwd_notify.setText("변경할 비밀번호를 입력하세요.");
                        ChangeStarToWhite();
                    }
                };
                TimerTask timertask = new TimerTask() {
                    @Override
                    public void run() {
                        Message msg = mHandler.obtainMessage();
                        mHandler.sendMessage(msg);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(timertask, 500);
                mode = 1;
                check_again = false;
            }
        }
        else if(mode == 1 && !check_again){
            for(int i = 0 ; i<4 ; i++)
                first_input[i] = input[i];
            pwd_notify.setText("한번 더 입력 하세요.");
            final android.os.Handler mHandler = new android.os.Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    ChangeStarToWhite();
                }
            };
            TimerTask timertask = new TimerTask() {
                @Override
                public void run() {
                    Message msg = mHandler.obtainMessage();
                    mHandler.sendMessage(msg);
                    check_again = true;
                }
            };
            Timer timer = new Timer();
            timer.schedule(timertask, 500);

        }
        //그냥 비밀번호 검사일때
        else if(mode == 0){
            if(realPassword[0]== input[0]&& realPassword[1] == input[1]&& realPassword[2] == input[2]&& realPassword[3] == input[3]) {
                /*달력화면으로 변경 또는 그냥 피니쉬*/
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }
            else {
                pwd_notify.setText("다시 입력 하세요.");
                IfFailPassword();
            }
        }
    }

    //비밀번호 확인 실패시에.
    void IfFailPassword(){
        Vibrator vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(500);
        final android.os.Handler mHandler = new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ChangeStarToWhite();
            }
        };
        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                Message msg = mHandler.obtainMessage();
                mHandler.sendMessage(msg);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timertask, 500);
    }

    //별을 흰색으로 바꾸기.
    void ChangeStarToWhite(){
        star1.setTextColor(ContextCompat.getColor(context,R.color.white));
        star2.setTextColor(ContextCompat.getColor(context, R.color.white));
        star3.setTextColor(ContextCompat.getColor(context, R.color.white));
        star4.setTextColor(ContextCompat.getColor(context, R.color.white));
        count=0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
