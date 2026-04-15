package com.likerhood.design.factory;

import com.likerhood.design.factory.imp.HtmlDialogFactory;
import com.likerhood.design.factory.imp.WindowsDialogFactory;

import static org.junit.Assert.*;

public class DialogFactoryTest {

    public static DialogFactory dialogFactory;

    public static void main(String[] args) {

        config();
        runBusinessLogic();

    }



    // 配置函数，选择工厂
    public static void config() {
        if (System.getProperty("os.name").equals("Windows 11")){
            dialogFactory = new WindowsDialogFactory();
        } else {
            dialogFactory = new HtmlDialogFactory();
        }
    }



    // 生成产品
    public static void runBusinessLogic() {
        dialogFactory.renderWindow();

    }


}