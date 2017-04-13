package dev.popupwindow.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.EasyEditSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import dev.joemi.easypopupwindow.EasyPopupWindow;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn1,btn2,btn3,btn4;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1= (Button) findViewById(R.id.btn1);
        btn2= (Button) findViewById(R.id.btn2);
        btn3= (Button) findViewById(R.id.btn3);
        btn4= (Button) findViewById(R.id.btn4);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        mContext=this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                showPopupBottom();
                break;
            case R.id.btn2:
                showPopupTop();
                break;
            case R.id.btn3:
                showEnterAndExistAnim();
                break;
            case R.id.btn4:
                showEditText();
                break;
        }
    }

    private void showPopupBottom(){
        View contentView=LayoutInflater.from(this).inflate(R.layout.layout_popup,null);
        EasyPopupWindow easyPopupWindow=new EasyPopupWindow.Builder(this)
                .setView(contentView)
                .setOutsideTouchable(true)
                .withBackgroundAlpha(EasyPopupWindow.DEFAULTBACKGROUNDALPHA)
                .build()
                .showAsDropDown(btn1);
        TextView tv1= (TextView) contentView.findViewById(R.id.tv1);
        TextView tv2= (TextView) contentView.findViewById(R.id.tv2);
        TextView tv3= (TextView) contentView.findViewById(R.id.tv3);
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message="";
                switch (v.getId()){
                    case R.id.tv1:
                        message="item1 click";
                        break;
                    case R.id.tv2:
                        message="item2 click";
                        break;
                    case R.id.tv3:
                        message="item3 click";
                        break;
                }
                Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
            }
        };
        tv1.setOnClickListener(onClickListener);
        tv2.setOnClickListener(onClickListener);
        tv3.setOnClickListener(onClickListener);


    }

    private void showPopupTop(){
        EasyPopupWindow easyPopupWindow=new EasyPopupWindow.Builder(this)
                .setView(R.layout.layout_popup)
                .setOutsideTouchable(true)
                .withBackgroundAlpha(EasyPopupWindow.DEFAULTBACKGROUNDALPHA)
                .setOnDimissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Toast.makeText(mContext,"popupwindow dismiss",Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        easyPopupWindow.showAsDropDown(btn2,0,-(easyPopupWindow.getWindowHeight()+btn2.getHeight()));
    }

    private void showEnterAndExistAnim(){
        EasyPopupWindow easyPopupWindow=new EasyPopupWindow.Builder(this)
                .setView(R.layout.layout_popup)
                .setOutsideTouchable(true)
                .withBackgroundAlpha(EasyPopupWindow.DEFAULTBACKGROUNDALPHA)
                .setAnimationStyle(R.style.PopWindowAnimationStyle)
                .build();
        easyPopupWindow.showAsDropDown(btn3,(btn3.getWidth()-easyPopupWindow.getWindowWidth())/2,0);
    }

    private void showEditText(){
        EasyPopupWindow easyPopupWindow=new EasyPopupWindow.Builder(this)
                .setView(R.layout.layout_popup_edittext)
                .size(btn4.getWidth(),btn4.getHeight())
                .setOutsideTouchable(true)
                .withBackgroundAlpha(EasyPopupWindow.DEFAULTBACKGROUNDALPHA)
                .setFocusable(true)
                .build()
                .showAsDropDown(btn4);
    }

}
