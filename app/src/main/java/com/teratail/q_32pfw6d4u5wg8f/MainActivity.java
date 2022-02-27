package com.teratail.q_32pfw6d4u5wg8f;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.*;

import android.os.*;
import android.util.Log;

/**
 * アプリを構成する画面(フラグメント)の土台となる、アプリ唯一のアクティビティ。
 * このアクティビティが終了すればアプリの終了となる。
 * 各画面やゲーム自体の内容には関わらず、データを保持する ViewModel の保持と、
 * ( その ViewModel を通じての ) 直轄のステージフラグメントの遷移操作および
 * データの保存(のトリガ)・ゲームの終了を行う。
 */
public class MainActivity extends AppCompatActivity {
  private static final String LOG_TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    MainViewModel model = new ViewModelProvider(this).get(MainViewModel.class);

    model.getSaveAndEndGame().observe(this, value -> {
      Log.d(LOG_TAG, "observe value="+value);

      if(value.needSave) model.save(this);
      if(value.needEndGame) new Handler(getMainLooper()).post(() -> finish()); //一応全て終わってからの感じ
    });

    //ステージ遷移
    model.getStage().observe(this, stage -> {
      Log.d(LOG_TAG, "observe stage="+stage);

      //この辺は enum Stage に移しても良いかも?
      Fragment nextFragment;
      switch(stage) {
        case PRE: nextFragment = new PrestageFragment(); break;
        case ONE: nextFragment = new Stage1Fragment(); break;
        case TWO: nextFragment = new Stage2Fragment(); break;
        case THREE: nextFragment = new Stage3Fragment(); break;
        default: throw new IllegalArgumentException("stage="+stage);
      }
      getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.fragmentBase, nextFragment)
              .commit();
    });
  }
}