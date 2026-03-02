package io.github.xqplus.virt;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Virtual platform center type enum.
 *
 * @author chenq
 */
public enum CenterType {

    VMWARE_VSPHERE(1, "VMware vSphere"),
    HUAWEI_FUSION_COMPUTE(2, "Huawei FusionCompute"),
    H3C_CAS(3, "H3C CAS/UIS"),
    ZSTACK_CLOUD(4, "ZStack Cloud"),
    HYPER_V(5, "Microsoft Hyper-V"),
    //    SANGFOR(6, "Sangfor HCI/SCP"),   // 现在先拆分成两类
    SANGFOR_SCP(6, "Sangfor SCP"),
    SANGFOR_HCI(7, "Sangfor HCI"),
    OPENSTACK(8, "OpenStack"),
    SMARTX(9, "SmartX"),
    XEN_SERVER(10, "Citrix XenServer"),
    SUPER_CLOUD(11, "SuperCloud"),
    INSPUR_INCLOUD_SPHERE(12, "Inspur InCloud Sphere"),
    OVIRT(13, "Red Hat oVirt");


    private final int code;
    private final String name;

    private static JSONArray typeArr;
    private static Map<Integer, CenterType> mappings;

    CenterType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CenterType resolve(Integer code) {
        if (mappings == null) {
            mappings = new HashMap<>(values().length);
            for (CenterType centerType : values()) {
                mappings.put(centerType.code, centerType);
            }
        }
        return mappings.get(code);
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static String name(int code) {
        for (CenterType value : CenterType.values()) {
            if (value.getCode() == code) {
                return value.getName();
            }
        }
        return null;
    }

    public static int code(String name) {
        for (CenterType value : CenterType.values()) {
            if (value.getName().equals(name)) {
                return value.getCode();
            }
        }
        return -1;
    }

    public static JSONArray getTypeArr() {
        ensureTypeArr();
        return typeArr;
    }

    public boolean codeEquals(Integer code) {
        return code != null && code == this.code;
    }

    private static void ensureTypeArr() {
        if (typeArr == null || typeArr.isEmpty()) {
            typeArr = new JSONArray();
            for (CenterType type : CenterType.values()) {
                JSONObject jo = new JSONObject();
                jo.put("code", type.getCode());
                jo.put("name", type.getName());
                typeArr.add(jo);
            }
        }
    }
}
