package edu.duke.ece651.grp9.risk.shared;

import java.io.Serializable;

public interface Resource extends Serializable {

    public int getQuantity();

    public void setResource(int resourceQuantity);

    public void addResource(int addResourceQuantity);

}
