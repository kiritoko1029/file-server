package com.cxc.fileservice.domain;

import lombok.Data;

/**
 * @author chenxiangcai
 */
@Data
public class FSFile {
    private String Name;
    private String Size;
    private String relatePath;
    private String modifyTime;
}
