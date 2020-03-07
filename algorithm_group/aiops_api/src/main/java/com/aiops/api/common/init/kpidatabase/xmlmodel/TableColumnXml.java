package com.aiops.api.common.init.kpidatabase.xmlmodel;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-07 16:00
 */
@XmlRootElement(name = "column")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"name", "type", "primary"})
@Data
public class TableColumnXml implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String type;

    private boolean primary;
}
