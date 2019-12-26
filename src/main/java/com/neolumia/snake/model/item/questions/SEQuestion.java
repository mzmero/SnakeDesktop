package com.neolumia.snake.model.item.questions;

import com.neolumia.snake.model.item.Item;
import com.neolumia.snake.model.item.ItemType;
import com.neolumia.snake.model.questions.Question;

public abstract class SEQuestion extends Item {

  public SEQuestion(String name) {
    super(ItemType.QUESTION, name);
  }

}
