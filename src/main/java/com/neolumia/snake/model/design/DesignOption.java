
package com.neolumia.snake.model.design;

import java.util.Optional;

public interface DesignOption<T> {

  String getName();

  String getFile();

  Optional<T> next();

  Optional<T> before();
}
