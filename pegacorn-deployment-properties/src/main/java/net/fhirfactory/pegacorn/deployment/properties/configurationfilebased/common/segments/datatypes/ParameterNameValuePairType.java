package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.datatypes;

public class ParameterNameValuePairType {
    private String parameterName;
    private String parameterValue;

    public ParameterNameValuePairType(){
        this.parameterName = null;
        this.parameterValue = null;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    @Override
    public String toString() {
        return "ConfigAttributePairType{" +
                "name='" + parameterName + '\'' +
                ", value='" + parameterValue + '\'' +
                '}';
    }
}
