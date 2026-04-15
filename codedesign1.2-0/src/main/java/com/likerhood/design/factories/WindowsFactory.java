package com.likerhood.design.factories;

import com.likerhood.design.buttons.Button;
import com.likerhood.design.buttons.Windowsbutton;
import com.likerhood.design.checkboxes.Checkbox;
import com.likerhood.design.checkboxes.WindowsCheckbox;

public class WindowsFactory implements GUIFactory{
    @Override
    public Button createButton() {
        return new Windowsbutton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}
