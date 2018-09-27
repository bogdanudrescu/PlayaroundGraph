/*
 * Created on May 10, 2005
 */
package test;

/**
 *
 */
public class NodeWraper {

    public Node node;

    public Boolean sens;

    public Integer curentCost;

    public Integer maxCost;

    public NodeWraper(Node node, Boolean sens, Integer curentCost, Integer maxCost) {
        this.node = node;
        this.sens = sens;
        this.curentCost = curentCost;
        this.maxCost = maxCost;
    }

}