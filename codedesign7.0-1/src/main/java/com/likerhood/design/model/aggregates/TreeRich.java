package com.likerhood.design.model.aggregates;


import com.likerhood.design.model.vo.TreeNode;
import com.likerhood.design.model.vo.TreeNodeLink;
import com.likerhood.design.model.vo.TreeRoot;

import java.util.Map;

/**
 * 规则树聚合
 */
public class TreeRich {

    private TreeRoot treeRoot;              // 数根节点
    private Map<Long, TreeNode> treeNodeMap;    // 树节点 -> 子节点

    public TreeRich(TreeRoot treeRoot, Map<Long, TreeNode> treeNodeMap) {
        this.treeRoot = treeRoot;
        this.treeNodeMap = treeNodeMap;
    }

    public TreeRoot getTreeRoot() {
        return treeRoot;
    }

    public void setTreeRoot(TreeRoot treeRoot) {
        this.treeRoot = treeRoot;
    }

    public Map<Long, TreeNode> getTreeNodeMap() {
        return treeNodeMap;
    }

    public void setTreeNodeMap(Map<Long, TreeNode> treeNodeMap) {
        this.treeNodeMap = treeNodeMap;
    }
}
