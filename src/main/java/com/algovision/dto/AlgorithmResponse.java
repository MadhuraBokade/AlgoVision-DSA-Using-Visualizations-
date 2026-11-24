package com.algovision.dto;
import com.algovision.model.VisualizationStep;
import java.util.List;
public class AlgorithmResponse {
    private String algorithm;
    private int totalSteps;
    private List<VisualizationStep> steps;
    private String error;
    private boolean success;
    public AlgorithmResponse() {}
    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String v) { this.algorithm=v; }
    public int getTotalSteps() { return totalSteps; }
    public void setTotalSteps(int v) { this.totalSteps=v; }
    public List<VisualizationStep> getSteps() { return steps; }
    public void setSteps(List<VisualizationStep> v) { this.steps=v; }
    public String getError() { return error; }
    public void setError(String v) { this.error=v; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean v) { this.success=v; }
    public static AlgorithmResponse ok(String algorithm, List<VisualizationStep> steps) {
        AlgorithmResponse r = new AlgorithmResponse();
        r.algorithm=algorithm; r.steps=steps; r.totalSteps=steps.size(); r.success=true;
        return r;
    }
    public static AlgorithmResponse error(String msg) {
        AlgorithmResponse r = new AlgorithmResponse();
        r.error=msg; r.success=false;
        return r;
    }
}
