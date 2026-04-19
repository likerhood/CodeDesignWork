package com.likerhood.design;

import com.likerhood.design.ceil.LevelOneCeiling;
import com.likerhood.design.ceil.LevelTwoCeiling;
import com.likerhood.design.coat.DuluxCoat;
import com.likerhood.design.coat.LiBangCoat;
import com.likerhood.design.floor.ShengXiangFloor;
import com.likerhood.design.tile.DongPengTile;
import com.likerhood.design.tile.MarcoPoloTile;

/**
 * 构建具体产品
 */
public class Builder {

    public IMenu levelOne(Double area){
        return new DecorationPackageMenu(area, "豪华欧式")
                .appendCeiling(new LevelTwoCeiling())
                .appendCoat(new DuluxCoat())
                .appendFloor(new ShengXiangFloor());
    }


    public IMenu levelTwo(Double area){
        return new DecorationPackageMenu(area, "轻奢田园")
                .appendCeiling(new LevelTwoCeiling())
                .appendCoat(new LiBangCoat())
                .appendTile(new MarcoPoloTile());
    }

    public IMenu levelThree(Double area){
        return new DecorationPackageMenu(area, "现代简约")
                .appendCeiling(new LevelOneCeiling())   // 吊顶，一级顶
                .appendCoat(new LiBangCoat())           // 涂料，立邦
                .appendTile(new DongPengTile());        // 地砖，东鹏
    }


}
