package io.github.xqplus.virt;

import lombok.Data;

/**
 * 虚拟资源 virt_resource
 *
 * @author chenq
 * @version 1.2.1
 * @create 2023/2/6 10:15:15
 * @update 2023/2/22 16:48:21
 */
@Data
//@TableName("virt_resource")
public class VirtResource {

//    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 虚拟化中心ID
     */
    private Integer centerId;

    /**
     * 上级资源 ID
     */
    private String parentId;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源类型（1Datacenter 2Cluster 3Host 4ResourcePool）
     * update for openstack   5 实例类型  6 网络
     */
    private Integer type;

    /**
     * 图标名称
     */
    private String icon;
}
