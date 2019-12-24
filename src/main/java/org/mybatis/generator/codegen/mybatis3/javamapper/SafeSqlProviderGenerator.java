package org.mybatis.generator.codegen.mybatis3.javamapper;

import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.javamapper.SqlProviderGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider.*;

/**
 * @author: Venscor
 * @date: 2019/12/23
 * @description
 */
public class SafeSqlProviderGenerator extends SqlProviderGenerator {
    private boolean useLegacyBuilder;

    public SafeSqlProviderGenerator(String project, boolean useLegacyBuilder) {
        super(project, useLegacyBuilder);
        this.useLegacyBuilder = useLegacyBuilder;
    }

    @Override
    protected boolean addSelectByExampleWithBLOBsMethod(TopLevelClass topLevelClass) {
        boolean rc = false;
        if (introspectedTable.getRules().generateSelectByExampleWithBLOBs()) {
            AbstractJavaProviderMethodGenerator methodGenerator =
                    new SafeProviderSelectByExampleWithBLOBsMethodGenerator(useLegacyBuilder);
            initializeAndExecuteGenerator(methodGenerator, topLevelClass);
            rc = true;
        }

        return rc;
    }

    @Override
    protected boolean addSelectByExampleWithoutBLOBsMethod(TopLevelClass topLevelClass) {
        boolean rc = false;
        if (introspectedTable.getRules().generateSelectByExampleWithoutBLOBs()) {
            AbstractJavaProviderMethodGenerator methodGenerator =
                    new SafeProviderSelectByExampleWithoutBLOBsMethodGenerator(useLegacyBuilder);
            initializeAndExecuteGenerator(methodGenerator, topLevelClass);
            rc = true;
        }

        return rc;

    }
}
