package com.likerhood.design.factory;

import com.likerhood.design.buttons.IButton;


/**
 * 抽象工厂，作为父类，继承的子代工厂用于创建产品
 */
public abstract class DialogFactory {

    // 对话弹窗
    public void renderWindow() {

        // 创建按钮
        IButton okButton = createButton();
        okButton.render();
    }

    /**
     * Subclasses will override this method in order to create specific button
     * objects.
     */
    public abstract IButton createButton();
}
