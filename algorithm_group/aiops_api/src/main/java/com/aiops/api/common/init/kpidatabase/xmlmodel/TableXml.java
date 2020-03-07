package com.aiops.api.common.init.kpidatabase.xmlmodel;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-07 15:59
 */
@XmlRootElement(name = "table")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"kpis", "columns"})
@Data
public class TableXml implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "kpi")
    private List<String> kpis;

    @XmlElement(name = "column")
    private List<TableColumnXml> columns;
}
