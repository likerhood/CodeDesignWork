package com.likerhood.design.factories;

import com.likerhood.design.buttons.Button;
import com.likerhood.design.buttons.MacOSButton;
import com.likerhood.design.checkboxes.Checkbox;
import com.likerhood.design.checkboxes.MacOSCheckbox;

public class MacOSFactory implements GUIFactory{
    @Override
    public Button createButton() {
        return new MacOSButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacOSCheckbox();
    }
}
