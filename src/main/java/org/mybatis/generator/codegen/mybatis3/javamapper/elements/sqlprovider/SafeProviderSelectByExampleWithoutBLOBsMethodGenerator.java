package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getSelectListPhrase;
import static org.mybatis.generator.internal.util.StringUtility.escapeStringForJava;

/**
 * @author: Venscor
 * @date: 2019/12/23
 * @description
 */
public class SafeProviderSelectByExampleWithoutBLOBsMethodGenerator extends ProviderSelectByExampleWithoutBLOBsMethodGenerator {
    public SafeProviderSelectByExampleWithoutBLOBsMethodGenerator(boolean useLegacyBuilder) {
        super(useLegacyBuilder);
    }

    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<String> staticImports = new TreeSet<>();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();

        if (useLegacyBuilder) {
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.BEGIN"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SELECT"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.FROM"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SQL"); //$NON-NLS-1$
        } else {
            importedTypes.add(NEW_BUILDER_IMPORT);
            importedTypes.add(FullyQualifiedJavaType.getNewArrayListInstance());
        }

        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        importedTypes.add(fqjt);

        Method method = new Method(getMethodName());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.addParameter(new Parameter(fqjt, "example")); //$NON-NLS-1$

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        if (useLegacyBuilder) {
            method.addBodyLine("BEGIN();"); //$NON-NLS-1$
        } else {
            method.addBodyLine("SQL sql = new SQL();"); //$NON-NLS-1$
        }

        boolean distinctCheck = true;
        for (IntrospectedColumn introspectedColumn : getColumns()) {
            if (distinctCheck) {
                method.addBodyLine("if (example != null && example.isDistinct()) {"); //$NON-NLS-1$
                method.addBodyLine(String.format("%sSELECT_DISTINCT(\"%s\");", //$NON-NLS-1$
                        builderPrefix,
                        escapeStringForJava(getSelectListPhrase(introspectedColumn))));
                method.addBodyLine("} else {"); //$NON-NLS-1$
                method.addBodyLine(String.format("%sSELECT(\"%s\");", //$NON-NLS-1$
                        builderPrefix,
                        escapeStringForJava(getSelectListPhrase(introspectedColumn))));
                method.addBodyLine("}"); //$NON-NLS-1$
            } else {
                method.addBodyLine(String.format("%sSELECT(\"%s\");", //$NON-NLS-1$
                        builderPrefix,
                        escapeStringForJava(getSelectListPhrase(introspectedColumn))));
            }

            distinctCheck = false;
        }

        method.addBodyLine(String.format("%sFROM(\"%s\");", //$NON-NLS-1$
                builderPrefix,
                escapeStringForJava(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime())));
        if (useLegacyBuilder) {
            method.addBodyLine("applyWhere(example, false);"); //$NON-NLS-1$
        } else {
            method.addBodyLine("applyWhere(sql, example, false);"); //$NON-NLS-1$
        }


        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();

        method.addBodyLine(""); //$NON-NLS-1$
        if (columns != null && columns.size() > 0) {
            method.addBodyLine("List<String> columnNameList = new ArrayList<>();");
            for (IntrospectedColumn column : columns) {
                if (column.getActualColumnName() != null) {
                    method.addBodyLine("columnNameList.add(\"" + column.getActualColumnName() + "\");");
                }
            }
        }

        method.addBodyLine(""); //$NON-NLS-1$
        method.addBodyLine("if (example.getOrderByClauses() != null && example.getOrderByClauses().size() > 0) {");
        method.addBodyLine("for (Map.Entry<String, String> entry : example.getOrderByClauses().entrySet()) {");
        method.addBodyLine("String columnName = entry.getKey();");
        method.addBodyLine("String descOrAsc = entry.getValue();");
        method.addBodyLine("if (columnNameList.contains(columnName)) {");
        method.addBodyLine("if (descOrAsc.equals(\"desc\")) {");
        method.addBodyLine("sql.ORDER_BY(columnName + \" desc\");");
        method.addBodyLine("} else {");
        method.addBodyLine("sql.ORDER_BY(columnName);");
        method.addBodyLine("}");
        method.addBodyLine("}");
        method.addBodyLine("}");
        method.addBodyLine("}");


        method.addBodyLine(""); //$NON-NLS-1$


//        method.addBodyLine("if (example != null && example.getOrderByClause() != null) {"); //$NON-NLS-1$
//        method.addBodyLine(String.format("%sORDER_BY(example.getOrderByClause());", builderPrefix)); //$NON-NLS-1$
//        method.addBodyLine("}"); //$NON-NLS-1$

        method.addBodyLine(""); //$NON-NLS-1$
        if (useLegacyBuilder) {
            method.addBodyLine("return SQL();"); //$NON-NLS-1$
        } else {
            method.addBodyLine("return sql.toString();"); //$NON-NLS-1$
        }

        if (callPlugins(method, topLevelClass)) {
            topLevelClass.addStaticImports(staticImports);
            topLevelClass.addImportedTypes(importedTypes);
            topLevelClass.addMethod(method);
        }
    }
}
