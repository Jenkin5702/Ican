package com.kisetsu.ican;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView ivUpload;
    private float[] corrdinateImage;
    private Button btnCompute;
    private float[] corrdinateCompute;
    private Button btnShare;
    private Button btnSave;
    private Button btnBack;
    private RadioGroup rgStandard;
    private float[] corrdinateRadioGroup;
    private RadioButton[] rbSamples;
    private TextView tvResult;
    private TextView tvAttrs;
    private LinearLayout result;
//    private android.support.v7.widget.Toolbar toolbar;

    public float deviceWidth;
    public float deviceHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setWighit();

        deviceWidth=getResources().getDisplayMetrics().widthPixels;
        deviceHeight=getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(MainActivity.this,ActivityData.class));
        return true;
    }

    private void initView(){
        result=findViewById(R.id.result);
        ivUpload=findViewById(R.id.iv_upload);
        corrdinateImage=new float[]{ivUpload.getX(),ivUpload.getY()};
        btnCompute=findViewById(R.id.btn_compute);
        corrdinateCompute=new float[]{btnCompute.getX(),btnCompute.getY()};
        btnShare=findViewById(R.id.btn_share);
        btnSave=findViewById(R.id.btn_save);
        btnBack=findViewById(R.id.btn_back);
        rgStandard=findViewById(R.id.rg_standard);
        corrdinateRadioGroup=new float[]{rgStandard.getX(),rgStandard.getY()};
        tvResult=findViewById(R.id.tv_result);
        tvAttrs=findViewById(R.id.tv_attrs);
    }

    private void setWighit(){
        btnCompute.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        ivUpload.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        result.setAlpha(0);
        btnShare.setY(3000);
        btnSave.setY(3000);
        btnBack.setY(3000);
        tvResult.setY(-3000);
        tvAttrs.setY(-3000);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_compute:
                animateForResult();
                break;
            case R.id.btn_back:
                animateBack();
                break;
            case R.id.iv_upload:
                buildDialog();
                break;
            case R.id.btn_save:
                startActivity(new Intent(MainActivity.this,ActivityData.class));
        }
    }

    private void animateForResult(){
//        toolbar.setBackgroundColor(getResources().getColor(R.color.purple));
//        Toast.makeText(MainActivity.this,""+deviceWidth,Toast.LENGTH_SHORT).show();
        btnCompute.animate().x(2500).y(btnCompute.getY()).setDuration(800).setInterpolator(new AnticipateInterpolator()).start();
        ivUpload.animate().x(2500).y(ivUpload.getY()).setDuration(800).setInterpolator(new AnticipateInterpolator()).start();
        rgStandard.animate().x(-2500).y(rgStandard.getY()).setDuration(800).setInterpolator(new AnticipateInterpolator()).start();
        result.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(result,"alpha",0f,40f).setDuration(3300).start();
        btnShare.animate().x(btnShare.getX()).y(800).setDuration(1000).setInterpolator(new DecelerateInterpolator()).start();
        btnSave.animate().x(btnShare.getX()).y(1000).setDuration(1300).setInterpolator(new DecelerateInterpolator()).start();
        btnBack.animate().x(btnShare.getX()).y(1200).setDuration(1600).setInterpolator(new DecelerateInterpolator()).start();
        tvResult.animate().x(tvResult.getX()).y(200).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
        tvAttrs.animate().x(tvAttrs.getX()).y(300).setDuration(700).setInterpolator(new DecelerateInterpolator()).start();

    }
    private void animateBack(){
//        Toast.makeText(MainActivity.this,""+deviceHeight,Toast.LENGTH_SHORT).show();
//        toolbar.setBackgroundColor(getResources().getColor(R.color.green));
        result.setVisibility(View.INVISIBLE);
        btnCompute.animate().x(10).y(deviceHeight-360).setDuration(1200).setInterpolator(new DecelerateInterpolator()).start();
        ivUpload.animate().x(10).y(10).setDuration(1200).setInterpolator(new DecelerateInterpolator()).start();
        rgStandard.animate().x(10).y(deviceHeight-620).setDuration(1200).setInterpolator(new DecelerateInterpolator()).start();
        tvResult.setVisibility(View.VISIBLE);
        btnShare.setVisibility(View.VISIBLE);
        result.animate().alpha(0).setDuration(1300).setInterpolator(new LinearInterpolator()).start();
        btnShare.animate().x(btnShare.getX()).y(3600).setDuration(1000).setInterpolator(new AnticipateInterpolator()).start();
        btnSave.animate().x(btnShare.getX()).y(3800).setDuration(1300).setInterpolator(new AnticipateInterpolator()).start();
        btnBack.animate().x(btnShare.getX()).y(3000).setDuration(1600).setInterpolator(new AnticipateInterpolator()).start();
        tvResult.animate().x(tvResult.getX()).y(3300).setDuration(500).setInterpolator(new AnticipateInterpolator()).start();
        tvAttrs.animate().x(tvAttrs.getX()).y(3300).setDuration(700).setInterpolator(new AnticipateInterpolator()).start();

    }
    private void buildDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.drawable.ic_dashboard_black_24dp);
        builder.setTitle("选择上传方式");
        //    指定下拉列表的显示数据
        final String[] cities = {"拍照", "相册"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ivUpload.setImageResource(R.drawable.img);
            }
        });
        builder.show();
    }
}
