package com.aiops.api.common.init.kpidatabase.xmlmodel;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-07 15:56
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "class")
@Data
public class ClassXml implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "table")
    private List<TableXml> tables;

}
