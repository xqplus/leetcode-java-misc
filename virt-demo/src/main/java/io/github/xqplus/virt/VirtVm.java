package io.github.xqplus.virt;

import lombok.Data;

/**
 * 虚拟机 virt_vm
 *
 * @author chenq
 * @version 1.0.0
 * @create 2023/2/6 10:35:38
 */
@Data
//@TableName("virt_vm")
public class VirtVm {

    /**
     * 虚拟中心ID
     */
    private Integer centerId;

//    /**
//     * 虚拟中心类型
//     */
//    @TableField(exist = false)
//    private Integer centerType;

    /**
     * 虚拟机ID
     */
    private String vmId;

    /**
     * 虚拟机 uuid，由于 vmware 平台虚拟机会偶发性的出现迁移导致 vm-id 变化问题（现场环境，本地未重现），
     * 所以这里添加字段用来辅助查询虚拟机实例
     *
     * @since 2.4.1
     */
    private String uuid;

    /**
     * 虚拟机名称
     */
    private String vmName;

    /**
     * 虚拟机置备大小
     */
    private Long vmSizeTotal;

    /**
     * 虚拟机实际大小
     */
    private Long vmSizeActual;

    /**
     * 操作系统类型（0-windows 1-linux）
     */
    private Integer osType;

    /**
     * 操作系统名称
     */
    private String osName;

    /**
     * 开机状态（0关机 1开机 2挂起）
     */
    private Integer powerState;

    /**
     * 快照存储位置（待定项）
     */
    private String snapshotLocation;

    /**
     * 资源-数据中心id
     */
    private String datacenterId;

    /**
     * 资源-集群id
     */
    private String clusterId;

    /**
     * 资源-主机id
     */
    private String hostId;

    /**
     * 资源-资源池id
     */
    private String resourcePoolId;

    /**
     * 关联的任务ID
     */
    private Integer taskId;
}

