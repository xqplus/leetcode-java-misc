package io.github.xqplus.virt.sangfor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SangforDemo {

    public static void main(String[] args) {
        String s = "{\"hugepage\":0,\"net0\":\"e1000=00:50:56:A8:BA:8D,peer_device_type=evs,peer_device_id=4e645c7e-6a09-4d86-94e8-f296a65b5a91,peer_vlan_group_id=ca577ee1-f01c-4b83-91d5-cb1be6411779,iface_id=265cc687-5089-481f-9d4a-f5b44308f75c,bridgename=物理出口1,dhcp_reply_flag=0,dhcp6_reply_flag=0,connect=on,qos_enable=0,ingress_kbps=0,egress_kbps=0,host_tso=on\",\"mouse_type\":\"usb\",\"lifespan\":\"-1\",\"cfgstoragename\":\"192.168.8.38/本地存储\",\"uuid\":\"ad45f970-67dd-49e9-b201-49aa474bfeed\",\"bootdisk\":\"ide0\",\"host_gpu\":0,\"timing_sync_vm_clock_enable\":0,\"l3_cache_enable\":\"0\",\"cores\":2,\"host\":\"host-005056a8b309\",\"logo\":0,\"sockets\":1,\"no_kvm_clock\":0,\"location_config\":{\"ip\":\"192.168.8.38\",\"name\":\"192.168.8.38\",\"id\":\"host-005056a8b309\"},\"create_time\":1765263277,\"numa\":1,\"cfgstorageats\":-1,\"cpu\":\"core2duo\",\"use_vhost_user_block\":0,\"high_performance_vm\":0,\"ha_enable\":0,\"name\":\"win2016\",\"real_use_vnet\":\"no\",\"uefi_bios\":0,\"cfgstoragelvtype\":\"local\",\"disable_ple\":0,\"use_vnet\":\"no\",\"invtsc\":0,\"splash_time\":0,\"use_vblk\":\"no\",\"memory\":2048,\"ctrl\":{\"auto_hotplug_memory\":0,\"backup\":0,\"max_memory_limit\":30720,\"vmtype\":\"vm\",\"auto_hotplug_cores\":0,\"auto_hotplug_sockets\":0,\"max_cpu_limit\":30,\"ha\":0,\"derived\":0,\"status\":\"running\",\"snap\":0},\"enable_network_affinity\":0,\"shutdown_time\":\"0\",\"use_uuid\":1,\"hotplug\":0,\"digest\":\"028d7e819cea43e2283ff3fb8cd7c0ba8b763cc5\",\"is_poly\":1,\"os_installed\":1,\"balloon_memory\":1,\"cfgstorage\":\"local\",\"sandbox\":0,\"ostype\":\"ws1664\",\"graphic_type\":\"qxl\",\"ip_unequal\":[],\"high_performance_disks\":\"ide0,ide1\",\"encrypted\":0,\"cpu_exclusive\":0,\"ide1\":\"local:vm-disk-2.qcow2,cache=real_none,preallocate=metadata,forecast=enable,cache_size=256,volume_type=qcow2,unmap=off,size=10737418240,storagename=本地存储\",\"ide0\":\"local:vm-disk-1.qcow2,cache=real_none,preallocate=metadata,forecast=enable,cache_size=256,volume_type=qcow2,unmap=off,size=21474836480,storagename=本地存储\",\"onboot\":0,\"file_filter\":0,\"cfgstorageshared\":0,\"tpm_enabled\":0,\"biosversion\":\"/sf/share/kvm/bios.bin\",\"location\":{\"ip\":\"192.168.8.38\",\"name\":\"192.168.8.38\",\"id\":\"host-005056a8b309\"},\"real_use_vblk\":\"no\"}";
        JSONObject vm = JSON.parseObject(s);
        List<JSONObject> sangforHCIDiskList = getSangforHCIDiskList(vm);
        for (JSONObject jsonObject : sangforHCIDiskList) {
            System.out.println(jsonObject);
        }
    }

    private static List<JSONObject> getSangforHCIDiskList(JSONObject vmDetail) {
        List<JSONObject> disks = new ArrayList<>();
        for (Map.Entry<String, Object> entry : vmDetail.entrySet()) {
            if (entry.getKey().startsWith("ide") && entry.getValue().toString().contains("volume_type=qcow2")) {
                JSONObject disk = new JSONObject();
                disk.put("id", Integer.parseInt(entry.getKey().substring(3)));
                String preallocate = null;
                Long size = null;
                for (String kv : entry.getValue().toString().split(",")) {
                    if (kv.startsWith("preallocate=")) {
                        preallocate = kv.substring("preallocate=".length());
                    } else if (kv.startsWith("size=")) {
                        size = Long.parseLong(kv.substring("size=".length()));
                    }
                }

                disk.put("size", size);
                disk.put("preallocate", preallocate);
                disks.add(disk);
            }
        }
        disks.sort(Comparator.comparingInt(d -> d.getIntValue("id")));
        return disks;
    }
}
