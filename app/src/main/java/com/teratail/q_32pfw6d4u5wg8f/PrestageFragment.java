package com.teratail.q_32pfw6d4u5wg8f;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.*;
import androidx.fragment.app.*;
import androidx.lifecycle.*;

/**
 * アプリで最初に表示される画面。
 * ロードボタン、保存・終了ダイアログ表示ボタンを持つ。
 * ステージ1への遷移が可能。
 */
public class PrestageFragment extends Fragment {
  private static final String LOG_TAG = "PrestageFragment";

  public PrestageFragment() {
    super(R.layout.fragment_prestage);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    MainViewModel model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

    Button loadButton = view.findViewById(R.id.load);
    loadButton.setOnClickListener(v -> model.load(getContext()));

    Button startButton = view.findViewById(R.id.start);
    startButton.setOnClickListener(v -> model.setStage(Stage.ONE));

    Button showDialogButton = view.findViewById(R.id.showDialog);
    showDialogButton.setOnClickListener(v -> {
      SaveAndEndGameDialogFragment.newInstance(false).show(getChildFragmentManager(), "SaveAndEndGameDialog");
    });
  }
}