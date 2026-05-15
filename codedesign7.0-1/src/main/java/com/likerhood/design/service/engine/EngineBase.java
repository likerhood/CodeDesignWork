package com.likerhood.design.service.engine;


import com.likerhood.design.model.aggregates.TreeRich;
import com.likerhood.design.model.vo.EngineResult;
import com.likerhood.design.model.vo.TreeNode;
import com.likerhood.design.model.vo.TreeRoot;
import com.likerhood.design.service.logic.LogicFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class EngineBase extends EngineConfig implements IEngine {

    private Logger logger = LoggerFactory.getLogger(EngineBase.class);

    @Override
    public  abstract EngineResult process(Long treeId, String userId, TreeRich treeRich, Map<String, String> decisionMatter);


    protected TreeNode engineDecisionMaker(TreeRich treeRich, Long treeId, String userId, Map<String, String> decisionMatter){
        TreeRoot treeRoot = treeRich.getTreeRoot();

        Map<Long, TreeNode> treeNodeMap = treeRich.getTreeNodeMap();

        // 规则树根ID
        Long rootNodeId = treeRoot.getTreeRootNodeId();
        TreeNode treeNodeInfo = treeNodeMap.get(rootNodeId);
        // 节点类型[NodeType]: 1.子叶，2.果实
        while(treeNodeInfo.getNodeType().equals(1)){
            String ruleKey = treeNodeInfo.getRuleKey();     // 1. 取出判断节点的规则，比如userAge和userGender
            LogicFilter logicFilter = logicFilterMap.get(ruleKey);  //2. 根据规则获取对应的逻辑过滤器
            String matterValue = logicFilter.matterValue(treeId, userId, decisionMatter);   //3. 根据过滤器获取对应的决策值，比如用户的userAge=18，userGender
            Long nextNode = logicFilter.filter(matterValue, treeNodeInfo.getTreeNodeLinkList());    //4. 根据决策值过滤出对应的下一个树节点ID，比如userAge=18，userGender=男，对应的树节点ID=100002
            treeNodeInfo = treeNodeMap.get(nextNode);       // 5. 获取下一个树节点的信息，继续循环判断，直到找到果实节点
            logger.info("决策树引擎=>{} userId：{} treeId：{} treeNode：{} ruleKey：{} matterValue：{}", treeRoot.getTreeName(), userId, treeId, treeNodeInfo.getTreeNodeId(), ruleKey, matterValue);
        }
        return treeNodeInfo;
    }

}
