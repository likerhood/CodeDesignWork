package com.likerhood.design.service.engine.impl;

import com.likerhood.design.model.aggregates.TreeRich;
import com.likerhood.design.model.vo.EngineResult;
import com.likerhood.design.model.vo.TreeNode;
import com.likerhood.design.service.engine.EngineBase;

import java.util.Map;

public class TreeEngineHandler extends EngineBase {

    @Override
    public EngineResult process(Long treeId, String userId, TreeRich treeRich, Map<String, String> decisionMatter) {
        // 决策流程
        TreeNode treeNode = engineDecisionMaker(treeRich, treeId, userId, decisionMatter);
        // 决策结果
        return new EngineResult(userId, treeId, treeNode.getTreeNodeId(), treeNode.getNodeValue());
    }

}
