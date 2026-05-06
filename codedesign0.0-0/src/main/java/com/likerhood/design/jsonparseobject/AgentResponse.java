package com.likerhood.design.jsonparseobject;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class AgentResponse {
    private String intent;

    // 重点关注：这是一个包含泛型的集合属性
    private List<Action> actions;
    private Map<String, Double> confidenceScores;

    @Data
    public static class Action {
        private String actionName;
        private String target;
    }
}