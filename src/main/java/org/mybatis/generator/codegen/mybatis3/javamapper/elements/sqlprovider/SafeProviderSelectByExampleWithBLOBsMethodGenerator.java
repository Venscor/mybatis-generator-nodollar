package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @author: Venscor
 * @date: 2019/12/23
 * @description
 */
public class SafeProviderSelectByExampleWithBLOBsMethodGenerator extends SafeProviderSelectByExampleWithoutBLOBsMethodGenerator {
    public SafeProviderSelectByExampleWithBLOBsMethodGenerator(boolean useLegacyBuilder) {
        super(useLegacyBuilder);
    }

    @Override
    public List<IntrospectedColumn> getColumns() {
        return introspectedTable.getAllColumns();
    }

    @Override
    public String getMethodName() {
        return introspectedTable.getSelectByExampleWithBLOBsStatementId();
    }

    @Override
    public boolean callPlugins(Method method, TopLevelClass topLevelClass) {
        return context.getPlugins().providerSelectByExampleWithBLOBsMethodGenerated(method, topLevelClass,
                introspectedTable);
    }

}
