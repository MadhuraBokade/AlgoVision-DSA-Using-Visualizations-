package com.algovision.model;
import java.util.List;
import java.util.Map;
public class VisualizationStep {
    private int stepNumber;
    private String operation;
    private int[] array;
    private List<Integer> activeIndices;
    private List<Integer> sortedIndices;
    private List<Integer> swappedIndices;
    private Map<String,Integer> pointers;
    private String explanation;
    private boolean completed;
    private String phase;
    private String extra;
    public VisualizationStep() {}
    public int getStepNumber() { return stepNumber; }
    public void setStepNumber(int v) { this.stepNumber=v; }
    public String getOperation() { return operation; }
    public void setOperation(String v) { this.operation=v; }
    public int[] getArray() { return array; }
    public void setArray(int[] v) { this.array=v; }
    public List<Integer> getActiveIndices() { return activeIndices; }
    public void setActiveIndices(List<Integer> v) { this.activeIndices=v; }
    public List<Integer> getSortedIndices() { return sortedIndices; }
    public void setSortedIndices(List<Integer> v) { this.sortedIndices=v; }
    public List<Integer> getSwappedIndices() { return swappedIndices; }
    public void setSwappedIndices(List<Integer> v) { this.swappedIndices=v; }
    public Map<String,Integer> getPointers() { return pointers; }
    public void setPointers(Map<String,Integer> v) { this.pointers=v; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String v) { this.explanation=v; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean v) { this.completed=v; }
    public String getPhase() { return phase; }
    public void setPhase(String v) { this.phase=v; }
    public String getExtra() { return extra; }
    public void setExtra(String v) { this.extra=v; }
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final VisualizationStep s = new VisualizationStep();
        public Builder stepNumber(int v) { s.stepNumber=v; return this; }
        public Builder operation(String v) { s.operation=v; return this; }
        public Builder array(int[] v) { s.array=v==null?null:v.clone(); return this; }
        public Builder activeIndices(List<Integer> v) { s.activeIndices=v; return this; }
        public Builder sortedIndices(List<Integer> v) { s.sortedIndices=v; return this; }
        public Builder swappedIndices(List<Integer> v) { s.swappedIndices=v; return this; }
        public Builder pointers(Map<String,Integer> v) { s.pointers=v; return this; }
        public Builder explanation(String v) { s.explanation=v; return this; }
        public Builder completed(boolean v) { s.completed=v; return this; }
        public Builder phase(String v) { s.phase=v; return this; }
        public Builder extra(String v) { s.extra=v; return this; }
        public VisualizationStep build() { return s; }
    }
}
