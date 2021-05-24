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

package top.legendscloud.project.generator.herion;

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
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
public class MpGeneratorSourceCodeProjectContributor<T extends TypeDeclaration, C extends CompilationUnit<T>, S extends SourceCode<T, C>>
		implements ProjectContributor {
	private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	private final ProjectDescription projectDescription;

	private final Supplier<S> sourceFactory;

	private final SourceCodeWriter<S> sourceWriter;

	private final ObjectProvider<MpGeneratorTypeCustomizer<? extends TypeDeclaration>> mainTypeCustomizers;

	private final ObjectProvider<MpGeneratorCompilationUnitCustomizer<?, ?>> mainCompilationUnitCustomizers;

	private final ObjectProvider<MpGeneratorSourceCodeCustomizer<?, ?, ?>> mainSourceCodeCustomizers;

	public MpGeneratorSourceCodeProjectContributor(ProjectDescription projectDescription,
												   Supplier<S> sourceFactory, SourceCodeWriter<S> sourceWriter,
												   ObjectProvider<MpGeneratorTypeCustomizer<?>> mpGeneratorTypeCustomizers,
												   ObjectProvider<MpGeneratorCompilationUnitCustomizer<?, ?>> mpGeneratorCompilationUnitCustomizers,
												   ObjectProvider<MpGeneratorSourceCodeCustomizer<?, ?, ?>> mpGeneratorSourceCodeCustomizers) {
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
		Writer out = null;
		try {
			// step2 获取模版路径
			String templatePath="classpath:configuration/";
			Resource resource = this.resolver.getResource(templatePath);


			configuration.setDirectoryForTemplateLoading(new File(GenerationFilePath.CONFIT_PATH));

			// step3 创建数据模型
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("packagePath", projectDescription.getPackageName());

			// step4 加载模版文件
			Template template = configuration.getTemplate("MpGenerator.ftl");

			StringBuffer stringBuffer=new StringBuffer(projectDescription.getBuildSystem().getTestSource(projectRoot,this.projectDescription.getLanguage()).toString());
			stringBuffer.append("/");
			stringBuffer.append(projectDescription.getPackageName().replace('.', '/'));
			stringBuffer.append("/");
			stringBuffer.append("MpGenerator.java");
			System.out.println(stringBuffer.toString());
			Path output = projectRoot.resolve(stringBuffer.toString());
			if (!Files.exists(output)) {
				Files.createDirectories(output.getParent());
				Files.createFile(output);
			}


			String mappersFileName="src/main/resources/mappers";
			Path mappersFileNameOutput = projectRoot.resolve(mappersFileName);
			if (!Files.exists(mappersFileNameOutput)) {
				Files.createDirectories(mappersFileNameOutput.getParent());
			}


			String dbFileName="src/main/resources/db/V1.0.020190515__sys_demo.sql";
			Path dbFileNameOutput = projectRoot.resolve(dbFileName);
			if (!Files.exists(dbFileNameOutput)) {
				Files.createDirectories(dbFileNameOutput.getParent());
				Files.createFile(dbFileNameOutput);
			}
			String dbResourcePattern="classpath:configuration/V1.0.0__sys_demo.sql";
			Resource dbResource = this.resolver.getResource(dbResourcePattern);
			FileCopyUtils.copy(dbResource.getInputStream(),Files.newOutputStream(dbFileNameOutput, StandardOpenOption.APPEND));

			// step5 生成数据
			File docFile = new File(stringBuffer.toString());
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
			// step6 输出文件
			template.process(dataMap, out);
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^MpGenerator.java 文件创建成功 !");
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
		List<MpGeneratorTypeCustomizer<?>> customizers = this.mainTypeCustomizers
				.orderedStream().collect(Collectors.toList());
		LambdaSafe
				.callbacks(MpGeneratorTypeCustomizer.class, customizers,
						mainApplicationType)
				.invoke((customizer) -> customizer.customize(mainApplicationType));
	}

	@SuppressWarnings("unchecked")
	private void customizeMainCompilationUnit(C compilationUnit) {
		List<MpGeneratorCompilationUnitCustomizer<?, ?>> customizers = this.mainCompilationUnitCustomizers
				.orderedStream().collect(Collectors.toList());
		LambdaSafe
				.callbacks(MpGeneratorCompilationUnitCustomizer.class, customizers,
						compilationUnit)
				.invoke((customizer) -> customizer.customize(compilationUnit));
	}

	@SuppressWarnings("unchecked")
	private void customizeMainSourceCode(S sourceCode) {
		List<MpGeneratorSourceCodeCustomizer<?, ?, ?>> customizers = this.mainSourceCodeCustomizers
				.orderedStream().collect(Collectors.toList());
		LambdaSafe.callbacks(MpGeneratorSourceCodeCustomizer.class, customizers, sourceCode)
				.invoke((customizer) -> customizer.customize(sourceCode));
	}

}
