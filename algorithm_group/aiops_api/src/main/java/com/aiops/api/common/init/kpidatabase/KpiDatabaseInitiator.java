package com.aiops.api.common.init.kpidatabase;

import com.aiops.api.common.init.kpidatabase.xmlmodel.ClassXml;
import com.aiops.api.common.init.kpidatabase.xmlmodel.DatabaseXml;
import com.aiops.api.common.init.kpidatabase.xmlmodel.TableColumnXml;
import com.aiops.api.common.init.kpidatabase.xmlmodel.TableXml;
import com.aiops.api.common.enums.StatisticsStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-07 13:21
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KpiDatabaseInitiator implements ApplicationRunner {

    private final ConnectionHelper connectionHelper;

    @Override
    public void run(ApplicationArguments args) {
        String[] sourceArgs = args.getSourceArgs();
        for (String arg : sourceArgs) {
            if (arg.equals("kpi_init")) {
                //启动初始化工作
                log.info("Kpi数据库初始化...");
                initWork();
                log.info("Kpi数据库初始化完成!");
            }
        }
    }

    /**
     * 初始化工作
     */
    private void initWork() {
        Resource resource = new ClassPathResource("");
        try {
            DatabaseXml databaseXml = (DatabaseXml) convertXmlFileToObject(DatabaseXml.class, resource.getFile().getAbsolutePath() + "\\kpidatabase\\database.xml");

            Connection connection = connectionHelper.getConnection();
            connection.setAutoCommit(true);
            for (ClassXml classXml : databaseXml.getClasses()) {
                String className = classXml.getName();
                for (TableXml tableXml : classXml.getTables()) {
                    String sqlColumnPart = generateSqlColumnPart(tableXml.getColumns());
                    for (String kpiName : tableXml.getKpis()) {
                        for (StatisticsStep step : StatisticsStep.values()) {
                            String tableName = "kpi_" + className + "_" + kpiName + "_" + step.getName();
                            String dropSql = "DROP TABLE IF EXISTS `" + tableName + "`;";
                            String sql = "create table " + tableName + "(" + sqlColumnPart + ");";
                            log.info(dropSql);
                            log.info(sql);
                            PreparedStatement dropStatement = connection.prepareStatement(dropSql);
                            dropStatement.execute();
                            PreparedStatement statement = connection.prepareStatement(sql);
                            statement.execute();
                        }
                    }
                }
            }
            connection.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateSqlColumnPart(List<TableColumnXml> list) {
        return list.stream().map(a -> {
                    String result = "`" + a.getName() + "` " + a.getType() + " ";
                    if (a.getType().toLowerCase().equals("timestamp")) {
                        result += " DEFAULT CURRENT_TIMESTAMP NOT NULL ";
                    }
                    if (a.isPrimary()) {
                        result = result + "  NOT NULL AUTO_INCREMENT primary key";
                    }
                    return result;
                }
        ).reduce((a, b) -> a + " , " + b).orElse("");
    }

    private Object convertXmlFileToObject(Class clazz, String xmlPath) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader fr = null;
            try {
                fr = new FileReader(xmlPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            SAXParserFactory sax = SAXParserFactory.newInstance();
            sax.setNamespaceAware(false);
            XMLReader xmlReader = sax.newSAXParser().getXMLReader();
            Source source = new SAXSource(xmlReader, new InputSource(fr));
            xmlObject = unmarshaller.unmarshal(source);
        } catch (JAXBException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }
}
