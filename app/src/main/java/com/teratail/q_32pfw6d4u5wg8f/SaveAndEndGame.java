package com.teratail.q_32pfw6d4u5wg8f;

/**
 * ゲーム保存・終了識別
 */
enum SaveAndEndGame {
  NOOP(false, false), SAVE(true, false), ENDGAME(false, true), SAVE_AND_ENDGAME(true, true);

  final boolean needSave, needEndGame;

  /**
   * コンストラクタ
   * @param needSave true=保存する
   * @param needEndGame true=終了する
   */
  SaveAndEndGame(boolean needSave, boolean needEndGame) {
    this.needSave = needSave;
    this.needEndGame = needEndGame;
  }
}
