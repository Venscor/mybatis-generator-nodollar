package org.mybatis.generator.codegen.mybatis3.javamapper;


import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;

/**
 * @author: Venscor
 * @date: 2019/12/23
 * @description
 */
public class SafeAnnotatedClientGenerator extends AnnotatedClientGenerator{
    public SafeAnnotatedClientGenerator(String project) {
        super(project);
    }


    @Override
    public List<CompilationUnit> getExtraCompilationUnits() {
        boolean useLegacyBuilder = false;

        String prop = context.getJavaClientGeneratorConfiguration()
                .getProperty(PropertyRegistry.CLIENT_USE_LEGACY_BUILDER);
        if (StringUtility.stringHasValue(prop)) {
            useLegacyBuilder = Boolean.valueOf(prop);
        }
        SqlProviderGenerator sqlProviderGenerator = new SafeSqlProviderGenerator(getProject(), useLegacyBuilder);
        sqlProviderGenerator.setContext(context);
        sqlProviderGenerator.setIntrospectedTable(introspectedTable);
        sqlProviderGenerator.setProgressCallback(progressCallback);
        sqlProviderGenerator.setWarnings(warnings);
        return sqlProviderGenerator.getCompilationUnits();
    }
}
