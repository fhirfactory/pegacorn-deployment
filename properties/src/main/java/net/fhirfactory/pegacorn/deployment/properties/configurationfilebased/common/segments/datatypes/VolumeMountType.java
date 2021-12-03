package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.datatypes;

import org.apache.commons.lang3.StringUtils;

public class VolumeMountType {
    private String volumeMountPath;
    private String volumeName;
    private String volumeHostPath;

    public VolumeMountType(){
        setVolumeHostPath(null);
        setVolumeName(null);
        setVolumeMountPath(null);
    }

    public void mergeOverrides(VolumeMountType overrides){
        if (overrides.hasVolumeName()) {
            setVolumeName(overrides.getVolumeName());
        }
        if (overrides.hasVolumeMountPath()) {
            setVolumeMountPath(overrides.getVolumeMountPath());
        }
        if(overrides.hasVolumeHostPath()){
            setVolumeHostPath(overrides.getVolumeHostPath());
        }
    }

    public boolean hasVolumeMountPath(){
        boolean has = !(StringUtils.isEmpty(volumeMountPath));
        return(has);
    }

    public boolean hasVolumeName(){
        boolean has = !(StringUtils.isEmpty(volumeName));
        return(has);
    }

    public boolean hasVolumeHostPath(){
        boolean has = !(StringUtils.isEmpty(volumeHostPath));
        return(has);
    }

    public String getVolumeMountPath() {
        return volumeMountPath;
    }

    public void setVolumeMountPath(String volumeMountPath) {
        this.volumeMountPath = volumeMountPath;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public String getVolumeHostPath() {
        return volumeHostPath;
    }

    public void setVolumeHostPath(String volumeHostPath) {
        this.volumeHostPath = volumeHostPath;
    }

    @Override
    public String toString() {
        return "VolumeMountType{" +
                ", volumeMountPath=" + volumeMountPath +
                ", volumeName=" + volumeName +
                ", volumeHostPath=" + volumeHostPath +
                '}';
    }
}
