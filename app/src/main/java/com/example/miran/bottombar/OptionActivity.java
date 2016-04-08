package com.example.miran.bottombar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class OptionActivity extends Activity {

    ImageButton btn_home, btn_status, btn_cosme, btn_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_layout);
        init();
    }

    void init() {
        btn_home=(ImageButton)findViewById(R.id.btn_home);
        btn_status=(ImageButton)findViewById(R.id.btn_status);
        btn_cosme=(ImageButton)findViewById(R.id.btn_cosme);
        btn_option=(ImageButton)findViewById(R.id.btn_option);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, StatusActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        btn_cosme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, CosmeticActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        btn_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, OptionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        // super.onBackPressed(); //지워야 실행됨

        AlertDialog.Builder d = new AlertDialog.Builder(this);
        d.setMessage("정말 종료하시겠습니까?");
        d.setPositiveButton("예", new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int which) {
                // process전체 종료
                finish();
            }
        });
        d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        d.show();
    }
}
