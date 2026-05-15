package com.likerhood.design.service.logic;

import com.likerhood.design.model.vo.TreeNodeLink;

import java.util.List;

public abstract class BaseLogic implements LogicFilter {

    @Override
    public Long filter(String matterValue, List<TreeNodeLink> treeNodeLinkList){
        for (TreeNodeLink nodeLine : treeNodeLinkList) {
            if(decisionLogic(matterValue, nodeLine))    return nodeLine.getNodeIdTo();
        }
        return 0L;
    }


    private boolean decisionLogic(String matterValue, TreeNodeLink nodeLink){
        switch (nodeLink.getRuleLimitType()){
            case 1:
                return matterValue.equals(nodeLink.getRuleLimitValue());
            case 2:
                return Double.parseDouble(matterValue) > Double.parseDouble(nodeLink.getRuleLimitValue());
            case 3:
                return Double.parseDouble(matterValue) < Double.parseDouble(nodeLink.getRuleLimitValue());
            case 4:
                return Double.parseDouble(matterValue) <= Double.parseDouble(nodeLink.getRuleLimitValue());
            case 5:
                return Double.parseDouble(matterValue) >= Double.parseDouble(nodeLink.getRuleLimitValue());
            default:
                return false;

        }
    }


}
