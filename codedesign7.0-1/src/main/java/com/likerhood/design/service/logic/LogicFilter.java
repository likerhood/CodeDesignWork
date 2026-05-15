package com.likerhood.design.service.logic;

import com.likerhood.design.model.vo.TreeNodeLink;

import java.util.List;
import java.util.Map;

public interface LogicFilter {

    /**
     * 逻辑决策器
     */

    Long filter(String matterValue, List<TreeNodeLink> treeNodeLinkList);


    /**
     * 获取决策值
     */
    String matterValue(Long treeId, String userId, Map<String, String> decisionMatter);

}
