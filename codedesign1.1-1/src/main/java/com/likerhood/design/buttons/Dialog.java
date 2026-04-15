package com.likerhood.design.buttons;

import com.likerhood.design.buttons.imp.HtmlButtons;
import com.likerhood.design.buttons.imp.WindowsButtons;

public class Dialog {

    public void renderWindow() {
        String os = System.getProperty("os.name");

        if (os.equals("Windows 11")) {
            // 直接 new 具体产品类
            WindowsButtons button = new WindowsButtons();
            button.render();
        } else {
            // 直接 new 另一个具体产品类
            HtmlButtons button = new HtmlButtons();
            button.render();
        }
    }


}
