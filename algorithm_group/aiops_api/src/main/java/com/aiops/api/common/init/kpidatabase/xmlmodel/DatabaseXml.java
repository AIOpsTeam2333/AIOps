package com.aiops.api.common.init.kpidatabase.xmlmodel;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-07 15:53
 */
@XmlRootElement(name = "database")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@Data
public class DatabaseXml implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "class")
    private List<ClassXml> classes;
}
