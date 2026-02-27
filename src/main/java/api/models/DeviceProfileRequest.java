package api.models;

import java.util.HashMap;
import java.util.Map;

public class DeviceProfileRequest {
    private String name;
    // ThingsBoard requires these default values
    private String type = "DEFAULT";
    private String transportType = "DEFAULT";
    private String provisionType = "DISABLED";
    private Map<String, Object> profileData;

    public DeviceProfileRequest(String name) {
        this.name = name;

        this.profileData = new HashMap<>();

        Map<String, String> config = new HashMap<>();
        config.put("type", "DEFAULT");

        Map<String, String> transportConfig = new HashMap<>();
        transportConfig.put("type", "DEFAULT");

        Map<String, String> provisionConfig = new HashMap<>();
        provisionConfig.put("type", "DISABLED");

        this.profileData.put("configuration", config);
        this.profileData.put("transportConfiguration", transportConfig);
        this.profileData.put("provisionConfiguration", provisionConfig);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getProvisionType() {
        return provisionType;
    }

    public void setProvisionType(String provisionType) {
        this.provisionType = provisionType;
    }

    public Map<String, Object> getProfileData() {
        return profileData;
    }

    public void setProfileData(Map<String, Object> profileData) {
        this.profileData = profileData;
    }
}