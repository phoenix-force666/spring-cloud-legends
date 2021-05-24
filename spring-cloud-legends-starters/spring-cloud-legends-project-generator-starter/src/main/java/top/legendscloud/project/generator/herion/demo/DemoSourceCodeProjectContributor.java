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

package top.legendscloud.project.generator.herion.demo;

import freemarker.template.Template;
import io.spring.initializr.generator.language.CompilationUnit;
import io.spring.initializr.generator.language.SourceCode;
import io.spring.initializr.generator.language.SourceCodeWriter;
import io.spring.initializr.generator.language.TypeDeclaration;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.generator.spring.util.LambdaSafe;
import org.springframework.beans.factory.ObjectProvider;
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
public class DemoSourceCodeProjectContributor<T extends TypeDeclaration, C extends CompilationUnit<T>, S extends SourceCode<T, C>>
		implements ProjectContributor {
	private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

	private final ProjectDescription projectDescription;

	private final Supplier<S> sourceFactory;

	private final SourceCodeWriter<S> sourceWriter;

	private final ObjectProvider<DemoTypeCustomizer<? extends TypeDeclaration>> mainTypeCustomizers;

	private final ObjectProvider<DemoCompilationUnitCustomizer<?, ?>> mainCompilationUnitCustomizers;

	private final ObjectProvider<DemoSourceCodeCustomizer<?, ?, ?>> mainSourceCodeCustomizers;

	public DemoSourceCodeProjectContributor(ProjectDescription projectDescription,
											Supplier<S> sourceFactory, SourceCodeWriter<S> sourceWriter,
											ObjectProvider<DemoTypeCustomizer<?>> mpGeneratorTypeCustomizers,
											ObjectProvider<DemoCompilationUnitCustomizer<?, ?>> mpGeneratorCompilationUnitCustomizers,
											ObjectProvider<DemoSourceCodeCustomizer<?, ?, ?>> mpGeneratorSourceCodeCustomizers) {
		this.projectDescription = projectDescription;
		this.sourceFactory = sourceFactory;
		this.sourceWriter = sourceWriter;
		this.mainTypeCustomizers = mpGeneratorTypeCustomizers;
		this.mainCompilationUnitCustomizers = mpGeneratorCompilationUnitCustomizers;
		this.mainSourceCodeCustomizers = mpGeneratorSourceCodeCustomizers;
	}

	@Override
	public void contribute(Path projectRoot) throws IOException {
		// step1 创建freeMarker配置实例
		freemarker.template.Configuration configuration = new freemarker.template.Configuration();
		configuration.setDirectoryForTemplateLoading(new File(GenerationFilePath.CONFIT_PATH));
		generator(configuration,"DemoUser.ftl",projectRoot,"entity/DemoUser.java");
		generator(configuration,"DemoUserController.ftl",projectRoot,"controller/DemoUserController.java");
		generator(configuration,"DemoApolloController.ftl",projectRoot,"controller/DemoApolloController.java");
		generator(configuration,"ApolloDemoProperties.ftl",projectRoot,"properties/ApolloDemoProperties.java");
	}



	private void generator(freemarker.template.Configuration configuration,String templateName,Path projectRoot,String filePath) throws IOException {
		Writer out = null;
		try {
			// step3 创建数据模型
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("entityPackagePath", projectDescription.getPackageName()+".user.entity");
			dataMap.put("controllerPackagePath", projectDescription.getPackageName()+".user.controller");
			dataMap.put("propertiesPackagePath", projectDescription.getPackageName()+".user.properties");
			dataMap.put("entityPath", projectDescription.getPackageName()+".user.entity.DemoUser");
			dataMap.put("propertiesPath", projectDescription.getPackageName()+".user.properties.ApolloDemoProperties");


			// step4 加载模版文件
			Template entityTemplate = configuration.getTemplate(templateName);

			StringBuffer stringBuffer=new StringBuffer(projectDescription.getBuildSystem().getMainSource(projectRoot,this.projectDescription.getLanguage()).toString());
			stringBuffer.append("/");
			stringBuffer.append(projectDescription.getPackageName().replace('.', '/'));
			stringBuffer.append("/user/");
			stringBuffer.append(filePath);
			System.out.println(stringBuffer.toString());
			Path entityOutput = projectRoot.resolve(stringBuffer.toString());
			if (!Files.exists(entityOutput)) {
				Files.createDirectories(entityOutput.getParent());
				Files.createFile(entityOutput);
			}


			// step5 生成数据
			File docFile = new File(stringBuffer.toString());
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
			// step6 输出文件
			entityTemplate.process(dataMap, out);
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^"+filePath+"   文件创建成功 !");
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
		String testName = templateName.substring(templateName.lastIndexOf("."));
		C compilationUnit = sourceCode.createCompilationUnit(this.projectDescription.getPackageName()+"."+filePath, testName);
		T testApplicationType = compilationUnit.createTypeDeclaration(testName);
		customizeMainApplicationType(testApplicationType);
		customizeMainSourceCode(sourceCode);
	}


	@SuppressWarnings("unchecked")
	private void customizeMainApplicationType(T mainApplicationType) {
		List<DemoTypeCustomizer<?>> customizers = this.mainTypeCustomizers
				.orderedStream().collect(Collectors.toList());
		LambdaSafe
				.callbacks(DemoTypeCustomizer.class, customizers,
						mainApplicationType)
				.invoke((customizer) -> customizer.customize(mainApplicationType));
	}

	@SuppressWarnings("unchecked")
	private void customizeMainCompilationUnit(C compilationUnit) {
		List<DemoCompilationUnitCustomizer<?, ?>> customizers = this.mainCompilationUnitCustomizers
				.orderedStream().collect(Collectors.toList());
		LambdaSafe
				.callbacks(DemoCompilationUnitCustomizer.class, customizers,
						compilationUnit)
				.invoke((customizer) -> customizer.customize(compilationUnit));
	}

	@SuppressWarnings("unchecked")
	private void customizeMainSourceCode(S sourceCode) {
		List<DemoSourceCodeCustomizer<?, ?, ?>> customizers = this.mainSourceCodeCustomizers
				.orderedStream().collect(Collectors.toList());
		LambdaSafe.callbacks(DemoSourceCodeCustomizer.class, customizers, sourceCode)
				.invoke((customizer) -> customizer.customize(sourceCode));
	}

}
