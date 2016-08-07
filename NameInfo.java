package edu.byu.cs.familymap.personActivity;

/**
 * Used for getting name information into List View.
 * Created by chris on 8/6/2016.
 */
public class NameInfo {
    private String value;
    private String label;

    public NameInfo(String value, String label)
    {
        setValue(value);
        setLabel(label);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
