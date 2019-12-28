package com.neolumia.snake.model.settings;

/** This class is for defining the settings like difficulty, size, player name and more */
public final class Settings {
  public static Locale DEFAULT_LOCALE = Locale.ENGLISH;

  public static Difficulty DEFAULT_DIFFICULTY = Difficulty.MEDIUM;
  public static Size DEFAULT_SIZE = Size.MEDIUM;
  public static String DEFAULT_NAME = "Fadi";
  public static boolean DEFAULT_LEADERBOARD = false;

  public Locale locale;
  public Difficulty difficulty;
  public Size size;
  public String playerName = "Fadi";
  public boolean leaderboard;

  public Settings(
      Locale locale, Difficulty difficulty, Size size, String playerName, boolean leaderboard) {
    this.locale = locale;
    this.difficulty = difficulty;
    this.size = size;
    this.playerName = "Fadi";
    this.leaderboard = leaderboard;
  }
}
