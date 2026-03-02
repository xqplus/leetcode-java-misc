package io.github.xqplus.virt.rcp;

import io.github.xqplus.virt.CenterType;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class VmConverter {

    public static void main(String[] args) throws IOException {
        Path libvirtXmlFile = createLibvirtXmlFile(CenterType.VMWARE_VSPHERE, Collections.singletonList(Paths.get("/home/sda")),
                Paths.get("E:\\v2v"));
    }

    private static Path createLibvirtXmlFile(CenterType centerType, List<Path> sourceDisks, Path v2vDir) throws IOException {
        Document document = DocumentHelper.createDocument();
        Element domainElement = document.addElement("domain");
        if (centerType == CenterType.VMWARE_VSPHERE) {
            domainElement.addAttribute("xmlns:vmware", "http://libvirt.org/schemas/domain/vmware/1.0");
            domainElement.addAttribute("type", "vmware");
        } else {
            domainElement.addAttribute("xmlns:qemu", "http://libvirt.org/schemas/domain/qemu/1.0");
            domainElement.addAttribute("type", "qemu");
        }
        Element nameElement = domainElement.addElement("name");
        nameElement.addText("disk");
        Element devicesElement = domainElement.addElement("devices");
        for (Path sourceDisk : sourceDisks) {
            Element diskElement = devicesElement.addElement("disk");
            diskElement.addAttribute("type", "file");
            diskElement.addAttribute("device", "disk");
            Element sourceElement = diskElement.addElement("source");
            sourceElement.addAttribute("file", sourceDisk.toString());
        }
        Path xmlFile = v2vDir.resolve("xml");
        OutputFormat outputFormat = OutputFormat.createPrettyPrint();
        outputFormat.setNewLineAfterDeclaration(false);
        XMLWriter xmlWriter = new XMLWriter(Files.newOutputStream(xmlFile), outputFormat);
        xmlWriter.write(document);
        xmlWriter.close();
        return xmlFile;
    }
}
