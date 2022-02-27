package com.teratail.q_32pfw6d4u5wg8f;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * ステージ1画面。
 * 保存・終了ダイアログ表示ボタンを持つ。
 * 固有データとして int 型の入力がある。
 * ステージ2への遷移が可能。
 */
public class Stage1Fragment extends Fragment {
  private static final String LOG_TAG = "Stage1Fragment";

  public Stage1Fragment() {
    super(R.layout.fragment_stage1);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    MainViewModel model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

    EditText editText = view.findViewById(R.id.editText);
    editText.setOnFocusChangeListener((v, hasFocus) -> {
      if(!hasFocus) {
        model.setIntData(Integer.parseInt(editText.getText().toString()));
      }
    });
    model.getIntData().observe(getViewLifecycleOwner(), value -> {
      editText.setText(""+value);
    });

    Button nextButton = view.findViewById(R.id.nextStage);
    nextButton.setOnClickListener(v -> model.setStage(Stage.TWO));

    Button showDialogButton = view.findViewById(R.id.showDialog);
    showDialogButton.setOnClickListener(v -> {
      SaveAndEndGameDialogFragment.newInstance(true).show(getChildFragmentManager(), "SaveAndEndGameDialog");
    });
  }
}