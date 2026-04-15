package com.likerhood.design.factories;

import com.likerhood.design.buttons.Button;
import com.likerhood.design.checkboxes.Checkbox;

public interface GUIFactory {

    Button createButton();
    Checkbox createCheckbox();

}
