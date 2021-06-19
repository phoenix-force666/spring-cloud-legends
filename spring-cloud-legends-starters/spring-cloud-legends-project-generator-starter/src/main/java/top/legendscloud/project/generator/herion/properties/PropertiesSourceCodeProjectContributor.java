/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.legendscloud.project.generator.herion.properties;

import freemarker.template.Template;
import io.spring.initializr.generator.language.CompilationUnit;
import io.spring.initializr.generator.language.SourceCode;
import io.spring.initializr.generator.language.SourceCodeWriter;
import io.spring.initializr.generator.language.TypeDeclaration;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.generator.spring.util.LambdaSafe;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import top.legendscloud.project.generator.herion.GenerationFilePath;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Author herion
 * @Description //TODO 
 * @Date  2019/5/8
 * @Param 
 * @return 
 **/
public class PropertiesSourceCodeProjectContributor<T extends TypeDeclaration, C extends CompilationUnit<T>, S extends SourceCode<T, C>>
		implements ProjectContributor {
	private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	private final ProjectDescription projectDescription;

	private final Supplier<S> sourceFactory;

	private final SourceCodeWriter<S> sourceWriter;

	private final ObjectProvider<PropertiesTypeCustomizer<? extends TypeDeclaration>> mainTypeCustomizers;

	private final ObjectProvider<PropertiesCompilationUnitCustomizer<?, ?>> mainCompilationUnitCustomizers;

	private final ObjectProvider<PropertiesSourceCodeCustomizer<?, ?, ?>> mainSourceCodeCustomizers;

	public PropertiesSourceCodeProjectContributor(ProjectDescription projectDescription,
												  Supplier<S> sourceFactory, SourceCodeWriter<S> sourceWriter,
												  ObjectProvider<PropertiesTypeCustomizer<?>> propertiesCustomizers,
												  ObjectProvider<PropertiesCompilationUnitCustomizer<?, ?>> propertiesCompilationUnitCustomizers,
												  ObjectProvider<PropertiesSourceCodeCustomizer<?, ?, ?>> mainSourceCodeCustomizers) {
		this.projectDescription = projectDescription;
		this.sourceFactory = sourceFactory;
		this.sourceWriter = sourceWriter;
		this.mainTypeCustomizers = propertiesCustomizers;
		this.mainCompilationUnitCustomizers = propertiesCompilationUnitCustomizers;
		this.mainSourceCodeCustomizers = mainSourceCodeCustomizers;
	}

	@Override
	public void contribute(Path projectRoot) throws IOException {
		// step1 创建freeMarker配置实例
		freemarker.template.Configuration configuration = new freemarker.template.Configuration();
		Writer out = null;
		try {
			// step2 获取模版路径
			String templatePath="classpath:configuration/";
			Resource resource = this.resolver.getResource(templatePath);


			configuration.setDirectoryForTemplateLoading(new File(GenerationFilePath.CONFIT_PATH));

			// step3 创建数据模型
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("packagePath", projectDescription.getPackageName());
			dataMap.put("applicationName", projectDescription.getApplicationName());

			String dbType=DbTypeEnum.ORACLE.getDbType();
			if(projectDescription.getRequestedDependencies().containsKey(DbTypeEnum.ORACLE6.getKey()) || projectDescription.getRequestedDependencies().containsKey(DbTypeEnum.ORACLE7.getKey())){
				dbType=DbTypeEnum.getDbTypeByCode(DbTypeEnum.ORACLE.getDbType());
			}else if(projectDescription.getRequestedDependencies().containsKey(DbTypeEnum.MYSQL.getKey())){
				dbType=DbTypeEnum.getDbTypeByCode(DbTypeEnum.MYSQL.getDbType());
			}

			dataMap.put("dbType", dbType);

			// step4 加载模版文件
			Template template = configuration.getTemplate("application.ftl");

			final String fileName="src/main/resources/application.properties";
			Path output = projectRoot.resolve(fileName);
			if (!Files.exists(output)) {
				Files.createDirectories(output.getParent());
				Files.createFile(output);
			}

			// step5 生成数据
			File docFile = new File(output.toString());
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
			// step6 输出文件
			template.process(dataMap, out);
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^application.properties 文件创建成功 !");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.flush();
					out.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}



		S sourceCode = this.sourceFactory.get();
		String testName = "MpGenerator";
		C compilationUnit = sourceCode.createCompilationUnit(this.projectDescription.getPackageName()+".generator", testName);
		T testApplicationType = compilationUnit.createTypeDeclaration(testName);
		customizeMainApplicationType(testApplicationType);
		customizeMainSourceCode(sourceCode);
	}

	@SuppressWarnings("unchecked")
	private void customizeMainApplicationType(T mainApplicationType) {
		List<PropertiesTypeCustomizer<?>> customizers = this.mainTypeCustomizers
				.orderedStream().collect(Collectors.toList());
		LambdaSafe
				.callbacks(PropertiesTypeCustomizer.class, customizers,
						mainApplicationType)
				.invoke((customizer) -> customizer.customize(mainApplicationType));
	}

	@SuppressWarnings("unchecked")
	private void customizeMainCompilationUnit(C compilationUnit) {
		List<PropertiesCompilationUnitCustomizer<?, ?>> customizers = this.mainCompilationUnitCustomizers
				.orderedStream().collect(Collectors.toList());
		LambdaSafe
				.callbacks(PropertiesCompilationUnitCustomizer.class, customizers,
						compilationUnit)
				.invoke((customizer) -> customizer.customize(compilationUnit));
	}

	@SuppressWarnings("unchecked")
	private void customizeMainSourceCode(S sourceCode) {
		List<PropertiesSourceCodeCustomizer<?, ?, ?>> customizers = this.mainSourceCodeCustomizers
				.orderedStream().collect(Collectors.toList());
		LambdaSafe.callbacks(PropertiesSourceCodeCustomizer.class, customizers, sourceCode)
				.invoke((customizer) -> customizer.customize(sourceCode));
	}

}
