package com.likerhood.design.factory.imp;

import com.likerhood.design.buttons.IButton;
import com.likerhood.design.buttons.imp.HtmlButtons;
import com.likerhood.design.factory.DialogFactory;

public class HtmlDialogFactory extends DialogFactory {
    public IButton createButton() {
        return new HtmlButtons();
    }
}
