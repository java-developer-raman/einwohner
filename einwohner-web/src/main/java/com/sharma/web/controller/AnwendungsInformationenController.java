package com.sharma.web.controller;

import com.sharma.core.collaborator.ApplicationManifestReader;
import com.sharma.data.resource.AnwendungsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

@RestController
public class AnwendungsInformationenController {
    private static final Logger logger = LoggerFactory.getLogger(AnwendungsInformationenController.class);

    @GetMapping(path = "/anwendungs-info")
    @ResponseBody
    public AnwendungsInfo getAnwendungsInformationen(){
        AnwendungsInfo anwendungsInfo = new AnwendungsInfo();
        Manifest manifest = ApplicationManifestReader.getManifest();
        Attributes attr = manifest.getMainAttributes();
        anwendungsInfo.setProjectName(attr.getValue("Implementation-Title"));
        anwendungsInfo.setProjectVersion(attr.getValue("Implementation-Version"));
        anwendungsInfo.setAutor(attr.getValue("Author"));
        return anwendungsInfo;
    }

}
