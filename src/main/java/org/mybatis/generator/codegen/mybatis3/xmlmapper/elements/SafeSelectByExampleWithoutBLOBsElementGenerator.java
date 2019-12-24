package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * @author: Venscor
 * @date: 2019/12/20
 * @description
 */
public class SafeSelectByExampleWithoutBLOBsElementGenerator extends SelectByExampleWithoutBLOBsElementGenerator {
    public SafeSelectByExampleWithoutBLOBsElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        String fqjt = introspectedTable.getExampleType();

        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", //$NON-NLS-1$
                introspectedTable.getSelectByExampleStatementId()));
        answer.addAttribute(new Attribute(
                "resultMap", introspectedTable.getBaseResultMapId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("parameterType", fqjt)); //$NON-NLS-1$

        context.getCommentGenerator().addComment(answer);

        answer.addElement(new TextElement("select")); //$NON-NLS-1$
        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "distinct")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("distinct")); //$NON-NLS-1$
        answer.addElement(ifElement);

        StringBuilder sb = new StringBuilder();
        if (stringHasValue(introspectedTable
                .getSelectByExampleQueryId())) {
            sb.append('\'');
            sb.append(introspectedTable.getSelectByExampleQueryId());
            sb.append("' as QUERYID,"); //$NON-NLS-1$
            answer.addElement(new TextElement(sb.toString()));
        }
        answer.addElement(getBaseColumnListElement());

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getExampleIncludeElement());

        //TODO：改成不要$拼接方式，需要查找所以其他地方生成的Orderby
        XmlElement outerIfElement = new XmlElement("if");
        outerIfElement.addAttribute(new Attribute("test", "orderByClauses != null and orderByClauses.size > 0"));
        outerIfElement.addElement(new TextElement("order by"));

        XmlElement forEachelemt = new XmlElement("foreach");
        forEachelemt.addAttribute(new Attribute("collection", "orderByClauses"));
        forEachelemt.addAttribute(new Attribute("index", "orderByName"));
        forEachelemt.addAttribute(new Attribute("item", "ascOrDesc"));
        forEachelemt.addAttribute(new Attribute("separator", ","));

        List<IntrospectedColumn> basedColumns = introspectedTable.getAllColumns();
        for (IntrospectedColumn column : basedColumns) {
            String columnName = column.getActualColumnName();

            XmlElement orderbyIfElement = new XmlElement("if");
            String orderbyAttributeValue = "orderByName != null and orderByName == '" + columnName + "'.toString()";
            orderbyIfElement.addAttribute(new Attribute("test", orderbyAttributeValue));
            String orderByText = columnName;
            orderbyIfElement.addElement(new TextElement(orderByText));

            XmlElement descIfElement = new XmlElement("if");
            descIfElement.addAttribute(new Attribute("test", "ascOrDesc !=null and ascOrDesc == 'desc'.toString()"));
            descIfElement.addElement(new TextElement("desc"));

            orderbyIfElement.addElement(descIfElement);

            forEachelemt.addElement(orderbyIfElement);
        }

        outerIfElement.addElement(forEachelemt);
        answer.addElement(outerIfElement);

//        ifElement = new XmlElement("if"); //$NON-NLS-1$
//        ifElement.addAttribute(new Attribute("test", "orderByClause != null")); //$NON-NLS-1$ //$NON-NLS-2$
//        ifElement.addElement(new TextElement("order by ${orderByClause}")); //$NON-NLS-1$
//        answer.addElement(ifElement);

        if (context.getPlugins()
                .sqlMapSelectByExampleWithoutBLOBsElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
