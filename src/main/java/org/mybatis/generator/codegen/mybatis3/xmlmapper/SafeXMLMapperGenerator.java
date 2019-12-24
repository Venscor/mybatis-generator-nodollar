package org.mybatis.generator.codegen.mybatis3.xmlmapper;


import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SafeSelectByExampleWithoutBLOBsElementGenerator;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByExampleWithBLOBsElementGenerator;

/**
 * @author: Venscor
 * @date: 2019/12/20
 * @description
 */
public class SafeXMLMapperGenerator extends XMLMapperGenerator {
    @Override
    protected void addSelectByExampleWithoutBLOBsElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateSelectByExampleWithoutBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new SafeSelectByExampleWithoutBLOBsElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    @Override
    protected void addSelectByExampleWithBLOBsElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateSelectByExampleWithBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new SelectByExampleWithBLOBsElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
}
