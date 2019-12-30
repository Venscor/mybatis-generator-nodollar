package org.mybatis.generator.codegen.mybatis3.javamapper;

import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.SafeXMLMapperGenerator;

/**
 * @author: Venscor
 * @date: 2019/12/20
 * @description
 */
public class SafeJavaMapperGenerator extends JavaMapperGenerator {
    public SafeJavaMapperGenerator() {
        super();
    }

    public SafeJavaMapperGenerator(String project, boolean requiresMatchedXMLGenerator) {
        super(requiresMatchedXMLGenerator);
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return new SafeXMLMapperGenerator();
    }
}
