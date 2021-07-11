

package com.ken.material.common.util;

import org.ini4j.Config;
import org.ini4j.Ini;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-21
 **/
public class IniUtil {
    public static Map<String,String> parseIni(String string) {
        Config config = new Config();
        config.setGlobalSection(true);
        config.setGlobalSectionName("");
        Ini ini = new Ini();
        ini.setConfig(config);
        try {
            ini.load(new StringReader(string));
            return ini.get("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String,String> parseIni(String sectionName,String string) {
        Ini ini = new Ini();
        try {
            ini.load(new StringReader(string));
            return ini.get(sectionName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
