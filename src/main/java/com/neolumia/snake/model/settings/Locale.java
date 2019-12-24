
package com.neolumia.snake.model.settings;

public enum Locale {
  /**
   * The language of the game
   */
  ENGLISH(0, java.util.Locale.ENGLISH), GERMAN(1, java.util.Locale.GERMAN);

  private final int id;
  private final java.util.Locale locale;

  Locale(int id, java.util.Locale locale) {
    this.id = id;
    this.locale = locale;
  }

  public static Locale fromId(int id) {
    for (Locale locale : values()) {
      if (locale.id == id) {
        return locale;
      }
    }
    return null;
  }

  public int getId() {
    return id;
  }

  public java.util.Locale getLocale() {
    return locale;
  }
}
