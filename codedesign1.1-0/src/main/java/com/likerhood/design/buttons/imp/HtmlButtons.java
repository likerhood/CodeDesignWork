package com.likerhood.design.buttons.imp;

import com.likerhood.design.buttons.IButton;

public class HtmlButtons implements IButton {
    public void render() {
        System.out.println("<button>Test Button</button>");
        onClick();
    }

    public void onClick() {
        System.out.println("html Click! Button says - 'Hello World!'");
    }
}
