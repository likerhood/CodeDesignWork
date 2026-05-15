package com.likerhood.design.model.vo;


/**
 * 树根信息
 */

public class TreeRoot {

    private Long treeId;        // 规则树id
    private Long treeRootNodeId;    // 规则书根id
    private String treeName;    // 规则书名称

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public Long getTreeRootNodeId() {
        return treeRootNodeId;
    }

    public void setTreeRootNodeId(Long treeRootNodeId) {
        this.treeRootNodeId = treeRootNodeId;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }
}
