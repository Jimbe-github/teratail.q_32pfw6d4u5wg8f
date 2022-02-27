package com.teratail.q_32pfw6d4u5wg8f;

import android.app.*;
import android.os.Bundle;

import androidx.annotation.*;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.*;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

/**
 * データ保存・終了ダイアログ。
 * 各画面から任意に使われる。
 * 最終的には単にダイアログを閉じるか、 ViewModel の SaveAndEndGame を設定する。
 * 従って本ダイアログの結果が必要な場合は SaveAndEndGame に Observer を登録すること。
 */
public class SaveAndEndGameDialogFragment extends DialogFragment {
  private static final String ARG_CAN_SAVED = "canSaved";

  /**
   * インスタンス生成
   * @param camSaved true=保存可能
   * @return 生成したインスタンス
   */
  @NonNull
  public static SaveAndEndGameDialogFragment newInstance(boolean camSaved) {
    SaveAndEndGameDialogFragment fragment = new SaveAndEndGameDialogFragment();
    Bundle args = new Bundle();
    args.putBoolean(ARG_CAN_SAVED, camSaved);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState){
    LayoutInflater inflater = LayoutInflater.from(getContext());
    View view = inflater.inflate(R.layout.dialog_save_and_endgame,null);

    MainViewModel model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

    TextView hozon_tv = view.findViewById(R.id.hozon_tv);
    hozon_tv.setText("hozon_messege");

    Button returnButton = view.findViewById(R.id.returnButton);
    Button finishButton = view.findViewById(R.id.finishButton);

    CheckBox doSaveCheckBox = view.findViewById(R.id.doSaveCheckBox);
    doSaveCheckBox.setOnCheckedChangeListener((buttonView,isChecked) -> {
      if(isChecked) {
        setTextAndListenerTo(returnButton, "保存して戻る", v -> {
          model.setSaveAndEndGame(SaveAndEndGame.SAVE);
          dismiss();
        });
        setTextAndListenerTo(finishButton, "保存して終了", v -> {
          ConfirmDialogFragment.newInstance("保存して終了", SaveAndEndGame.SAVE_AND_ENDGAME)
                  .show(getChildFragmentManager(), "confirm");
        });
      } else {
        setTextAndListenerTo(returnButton, "戻る", v -> {
          dismiss();
        });
        setTextAndListenerTo(finishButton, "終了", v -> {
          ConfirmDialogFragment.newInstance("終了", SaveAndEndGame.ENDGAME)
                  .show(getChildFragmentManager(), "confirm");
        });
      }
    });

    boolean canSaved = getArguments().getBoolean(ARG_CAN_SAVED);
    doSaveCheckBox.setChecked(canSaved);
    doSaveCheckBox.setEnabled(canSaved);

    AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(view).create();
    dialog.setCanceledOnTouchOutside(false);//ダイアログ以外の画面をタップしてもダイアログは消えない
    return dialog;
  }

  private void setTextAndListenerTo(Button button, String text, View.OnClickListener listener) {
    button.setText(text);
    button.setOnClickListener(listener);
  }

  /**
   * 終了確認ダイアログ。
   */
  public static class ConfirmDialogFragment extends DialogFragment {
    private static final String ARG_POSITIVE_TEXT = "PositiveText";
    private static final String ARG_POSITIVE_VALUE = "PositiveValue";

    /**
     * インスタンス生成
     * @param positiveText PositiveButton用テキスト
     * @param positiveValue PositiveButton押下時に ViewModel の SaveAndEndGame に設定する値
     * @return 生成したインスタンス
     */
    @NonNull
    public static ConfirmDialogFragment newInstance(@NonNull String positiveText, @NonNull SaveAndEndGame positiveValue) {
      ConfirmDialogFragment fragment = new ConfirmDialogFragment();
      Bundle args = new Bundle();
      args.putString(ARG_POSITIVE_TEXT, positiveText);
      args.putSerializable(ARG_POSITIVE_VALUE, positiveValue);
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      MainViewModel model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

      String positiveText = getArguments().getString(ARG_POSITIVE_TEXT);
      SaveAndEndGame positiveValue = (SaveAndEndGame)getArguments().getSerializable(ARG_POSITIVE_VALUE);
      AlertDialog dialog = new AlertDialog.Builder(getContext())
              .setTitle("終了確認")
              .setMessage("ゲームを終了しますか？")
              .setNegativeButton("戻る", (d, which) -> dismiss())
              .setPositiveButton(positiveText, (d, which) -> {
                model.setSaveAndEndGame(positiveValue);
                dismiss();
              })
              .create();
      dialog.setCanceledOnTouchOutside(false);
      return dialog;
    }
  }
}