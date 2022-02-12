/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */

package com.flowcentraltech.flowcentral.interconnect.configuration.xml.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.flowcentraltech.flowcentral.interconnect.configuration.xml.ApplicationConfig;
import com.flowcentraltech.flowcentral.interconnect.configuration.xml.InterconnectAppConfig;
import com.flowcentraltech.flowcentral.interconnect.configuration.xml.InterconnectConfig;

/**
 * XML utilities.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public final class XmlUtils {

    private static final Logger LOGGER = Logger.getLogger(XmlUtils.class.getName());

    private XmlUtils() {
        
    }
    
    public static List<ApplicationConfig> readInterconnectConfig(String resourceName) throws Exception {
        LOGGER.log(Level.INFO, "Reading interconnect configuration...");
        InterconnectConfig interconnectConfig = XmlUtils.readConfig(InterconnectConfig.class, resourceName);
        if (interconnectConfig.getInterconnectAppConfigs() != null) {
            List<InterconnectAppConfig> appConfigList = interconnectConfig.getInterconnectAppConfigs()
                    .getAppConfigList();
            if (appConfigList != null && !appConfigList.isEmpty()) {
                List<ApplicationConfig> resultList = new ArrayList<ApplicationConfig>();
                for (InterconnectAppConfig interconnectAppConfig : appConfigList) {
                    ApplicationConfig applicationConfig = XmlUtils.readConfig(ApplicationConfig.class,
                            interconnectAppConfig.getConfigFile());
                    resultList.add(applicationConfig);
                }

                LOGGER.log(Level.INFO, "[{0}] application interconnect configuration read.", resultList.size());
                return resultList;
            }
        }

        return Collections.emptyList();
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T readConfig(Class<T> clazz, String resourceName) throws Exception {
        T config = null;
        InputStream in = null;
        try {
            LOGGER.log(Level.INFO, "Reading interconnect resource : [{0}]", resourceName);
            in = XmlUtils.openFileResourceInputStream(resourceName);

            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbUnmarshaller.setEventHandler(new ValidationEventHandler()
                {
                    @Override
                    public boolean handleEvent(ValidationEvent event) {
                        return false;
                    }
                });

            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            // Disable JAXB DTD validation
            xmlReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            xmlReader.setFeature("http://xml.org/sax/features/validation", false);
            config = (T) jaxbUnmarshaller.unmarshal(new SAXSource(xmlReader, new InputSource(in)));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading interconnect configuration", e);
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        return config;
    }
    
    private static InputStream openFileResourceInputStream(String resourceName) throws Exception {
        File file = new File(resourceName);
        if (file.exists()) {
            return new FileInputStream(file);
        }

        return XmlUtils.class.getClassLoader()
                .getResourceAsStream(XmlUtils.conformJarSeparator(resourceName));
    }

    private static String conformJarSeparator(String filename) {
        filename = XmlUtils.conform("/", filename);
        if (filename.startsWith("/")) {
            return filename.substring("/".length());
        }
        return filename;
    }

    private static String conform(String separator, String name) {
        if (name == null) {
            return "";
        }

        if (separator.equals("\\")) {
            return name.replace('/', '\\');
        }
        return name.replace('\\', '/');
    }
    
}
