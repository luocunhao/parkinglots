package cn.xlink.parkinglots.client.permission;

import com.alibaba.fastjson.JSONObject;

public class AccessToken {
    private final int permission;

    private final int role;

    private final String     accessToken;
    /**
     * <pre>
     * {
     * "corpId":"",
     * "userId":"",
     * "memberId":"",
     * "appId":""
     * }
     * </pre>
     */
    private final JSONObject data;

    public AccessToken(int permission, int role, String accessToken, JSONObject data) {
        super();
        this.permission = permission;
        this.role = role;
        this.accessToken = accessToken;
        this.data = data;
    }

    public static final AccessToken from(String accessToken, String dataString) {
        JSONObject data       = JSONObject.parseObject(dataString);
        int        permission = RestPermissionType.NONE;

        if (!data.containsKey("role")) {
            return new AccessToken(permission, -1, accessToken, data);
        }

        Integer role = data.getInteger("role");
        switch (role) {
            case 0:
            case 1:
                permission = RestPermissionType.CORP;
                break;
            case 6:
                permission = RestPermissionType.EMPOWER;
                break;
            default:
                permission = RestPermissionType.ANYBODY;
                break;
        }
        return new AccessToken(permission, role, accessToken, data);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getId() {
        return data.getString("id");
    }

    public String getCorpId() {
        return data.getString("corp_id");
    }

    public int getDeviceId() {
        return data.getInteger("device_id");
    }

    public int getUserId() {
        return data.getInteger("user_id");
    }

    public String memberId() {
        return data.getString("member_id");
    }

    public int getPermission() {
        return permission;
    }

    public JSONObject getData() {
        return data;
    }

    public int getRole() {
        return role;
    }


}
