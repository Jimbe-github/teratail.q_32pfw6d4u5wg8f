package com.teratail.q_32pfw6d4u5wg8f;

import android.content.*;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.*;

/**
 * アプリ全体のデータを保持する ViewModel 。
 * データは主に LiveData で保持し、自身のデータの保存・再生機能を持つ。
 */
public class MainViewModel extends ViewModel {
  private static final String LOG_TAG = "MainViewModel";

  private final MutableLiveData<SaveAndEndGame> saveAndEndGameLiveData = new MutableLiveData<>(SaveAndEndGame.NOOP);
  LiveData<SaveAndEndGame> getSaveAndEndGame() { return saveAndEndGameLiveData; }
  void setSaveAndEndGame(SaveAndEndGame b) { saveAndEndGameLiveData.setValue(b); }

  private final MutableLiveData<Stage> stageLiveData = new MutableLiveData<>(Stage.PRE);
  LiveData<Stage> getStage() { return stageLiveData; }
  void setStage(Stage stage) { stageLiveData.setValue(stage); }

  private final MutableLiveData<Integer> intDataLiveData = new MutableLiveData<>(1);
  LiveData<Integer> getIntData() { return intDataLiveData; }
  void setIntData(int data) { intDataLiveData.setValue(data); }

  private final MutableLiveData<Double> doubleDataLiveData = new MutableLiveData<>(12.0);
  LiveData<Double> getDoubleData() { return doubleDataLiveData; }
  void setDoubleData(double data) { doubleDataLiveData.setValue(data); }

  private final MutableLiveData<String> stringDataLiveData = new MutableLiveData<>("ABC");
  LiveData<String> getStringData() { return stringDataLiveData; }
  void setStringData(String data) { stringDataLiveData.setValue(data); }

  void save(Context context) {
    Log.d(LOG_TAG, "save()");

    SharedPreferences sharedPreferences = getDataPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    //データを保存
    editor.putString("STAGE", getStage().getValue().name());
    editor.putInt("INTDATA", getIntData().getValue());
    editor.putLong("DOUBLEDATA", Double.doubleToRawLongBits(getDoubleData().getValue()));
    editor.putString("STRINGDATA", getStringData().getValue());

    editor.apply();

    Toast.makeText(context, "保存しました", Toast.LENGTH_LONG).show();
  }

  void load(Context context) {
    Log.d(LOG_TAG, "load()");

    SharedPreferences pref = getDataPreferences(context);

    //データを再生
    setStage(Stage.valueOf(pref.getString("STAGE", Stage.PRE.name())));
    setIntData(pref.getInt("INTDATA", 0));
    setDoubleData(Double.longBitsToDouble(pref.getLong("DOUBLEDATA", Double.doubleToRawLongBits(0.0))));
    setStringData(pref.getString("STRINGDATA", ""));
  }

  SharedPreferences getDataPreferences(Context context) {
    return context.getSharedPreferences("Data", Context.MODE_PRIVATE);
  }
}