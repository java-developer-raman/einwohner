package com.sharma.web.controller;

import com.sharma.data.resource.AnwendungsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

@RestController
public class AnwendungsInformationenController {
    private static final Logger logger = LoggerFactory.getLogger(AnwendungsInformationenController.class);

    @GetMapping(path = "/anwendungs-info")
    @ResponseBody
    public AnwendungsInfo getAnwendungsInformationen(){
        AnwendungsInfo anwendungsInfo = new AnwendungsInfo();
        Manifest manifest = getManifest(AnwendungsInformationenController.class);
        Attributes attr = manifest.getMainAttributes();
        anwendungsInfo.setProjectName(attr.getValue("Implementation-Title"));
        anwendungsInfo.setProjectVersion(attr.getValue("Implementation-Version"));
        anwendungsInfo.setAutor(attr.getValue("Author"));
        return anwendungsInfo;
    }

    private Manifest getManifest(Class<?> clz) {
        String resource = "/" + clz.getName().replace(".", "/") + ".class";
        String fullPath = clz.getResource(resource).toString();
        String archivePath = fullPath.substring(0, fullPath.length() - resource.length());
        if (archivePath.endsWith("\\WEB-INF\\classes") || archivePath.endsWith("/WEB-INF/classes")) {
            archivePath = archivePath.substring(0, archivePath.length() - "/WEB-INF/classes".length()); // Required for wars
        }
        try (InputStream input = new URL(archivePath + "/META-INF/MANIFEST.MF").openStream()) {
            return new Manifest(input);
        } catch (Exception e) {
            throw new RuntimeException("Loading MANIFEST for class " + clz + " failed!", e);
        }
    }
}
