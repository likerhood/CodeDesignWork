package com.likerhood.design.factory.imp;

import com.likerhood.design.buttons.IButton;
import com.likerhood.design.buttons.imp.WindowsButtons;
import com.likerhood.design.factory.DialogFactory;

public class WindowsDialogFactory extends DialogFactory {
    public IButton createButton() {
        return new WindowsButtons();
    }
}
