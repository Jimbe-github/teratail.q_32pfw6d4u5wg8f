package com.teratail.q_32pfw6d4u5wg8f;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * ステージ2画面。
 * 保存・終了ダイアログ表示ボタンを持つ。
 * 固有データとして Double 型の入力がある。
 * ステージ1,3への遷移が可能。
 */
public class Stage2Fragment extends Fragment {
  private static final String LOG_TAG = "Stage2Fragment";

  public Stage2Fragment() {
    super(R.layout.fragment_stage2);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    MainViewModel model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

    EditText editText = view.findViewById(R.id.editText);
    editText.setOnFocusChangeListener((v, hasFocus) -> {
      if(!hasFocus) {
        model.setDoubleData(Double.parseDouble(editText.getText().toString()));
      }
    });
    model.getDoubleData().observe(getViewLifecycleOwner(), value -> {
      editText.setText(""+value);
    });

    Button prevButton = view.findViewById(R.id.prevStage);
    prevButton.setOnClickListener(v -> model.setStage(Stage.ONE));

    Button nextButton = view.findViewById(R.id.nextStage);
    nextButton.setOnClickListener(v -> model.setStage(Stage.THREE));

    Button showDialogButton = view.findViewById(R.id.showDialog);
    showDialogButton.setOnClickListener(v -> {
      SaveAndEndGameDialogFragment.newInstance(true).show(getChildFragmentManager(), "SaveAndEndGameDialog");
    });
  }
}