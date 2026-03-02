package io.github.xqplus.virt;

/**
 * Virt resource type.
 *
 * @author chenq
 */
public enum ResourceType {

    DATACENTER(1, "Datacenter", "数据中心"), // 不同平台不同解释
    CLUSTER(2, "Cluster", "集群"),
    HOST(3, "Host", "主机"),
    RESOURCE_POOL(4, "Resource Pool", "资源池"),
    FOLDER(5, "Folder", "文件夹");

    private final int code;

    private final String name;

    private final String desc;

    ResourceType(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
