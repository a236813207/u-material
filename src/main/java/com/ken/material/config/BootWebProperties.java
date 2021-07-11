package com.ken.material.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@ConfigurationProperties(prefix = BootWebProperties.PAGEHELPER_PREFIX)
public class BootWebProperties {
    public static final String PAGEHELPER_PREFIX = "com.ken.mvc";

    private boolean openLog = true;
    private boolean writeEnumAsObject = true;
    private boolean writeNullListAsEmpty = true;
    private boolean writeNullNumberAsZero = true;
    private boolean writeNullStringAsEmpty = true;

    private String timeZone = "GMT+08";
    private JsonInclude.Include jsonInclude = JsonInclude.Include.ALWAYS;
    private boolean jsonPrettyPrint = true;

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public JsonInclude.Include getJsonInclude() {
        return jsonInclude;
    }

    public void setJsonInclude(JsonInclude.Include jsonInclude) {
        this.jsonInclude = jsonInclude;
    }

    public boolean isJsonPrettyPrint() {
        return jsonPrettyPrint;
    }

    public void setJsonPrettyPrint(boolean jsonPrettyPrint) {
        this.jsonPrettyPrint = jsonPrettyPrint;
    }

    public boolean isWriteNullListAsEmpty() {
        return writeNullListAsEmpty;
    }

    public void setWriteNullListAsEmpty(boolean writeNullListAsEmpty) {
        this.writeNullListAsEmpty = writeNullListAsEmpty;
    }

    public boolean isWriteNullNumberAsZero() {
        return writeNullNumberAsZero;
    }

    public void setWriteNullNumberAsZero(boolean writeNullNumberAsZero) {
        this.writeNullNumberAsZero = writeNullNumberAsZero;
    }

    public boolean isWriteNullStringAsEmpty() {
        return writeNullStringAsEmpty;
    }

    public void setWriteNullStringAsEmpty(boolean writeNullStringAsEmpty) {
        this.writeNullStringAsEmpty = writeNullStringAsEmpty;
    }

    public boolean isWriteEnumAsObject() {
        return writeEnumAsObject;
    }

    public void setWriteEnumAsObject(boolean writeEnumAsObject) {
        this.writeEnumAsObject = writeEnumAsObject;
    }

    public boolean isOpenLog() {
        return openLog;
    }

    public void setOpenLog(boolean openLog) {
        this.openLog = openLog;
    }
}
