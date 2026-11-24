package com.algovision.dto;
import java.util.List;
public class AlgorithmRequest {
    private String algorithm;
    private List<Integer> array;
    private Integer target;
    private String operation;
    private String type;
    private Integer value;
    private Integer position;
    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String v) { this.algorithm=v; }
    public List<Integer> getArray() { return array; }
    public void setArray(List<Integer> v) { this.array=v; }
    public Integer getTarget() { return target; }
    public void setTarget(Integer v) { this.target=v; }
    public String getOperation() { return operation; }
    public void setOperation(String v) { this.operation=v; }
    public String getType() { return type; }
    public void setType(String v) { this.type=v; }
    public Integer getValue() { return value; }
    public void setValue(Integer v) { this.value=v; }
    public Integer getPosition() { return position; }
    public void setPosition(Integer v) { this.position=v; }
}
