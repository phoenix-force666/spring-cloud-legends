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

import io.spring.initializr.generator.language.CompilationUnit;
import io.spring.initializr.generator.language.SourceCode;
import io.spring.initializr.generator.language.SourceCodeWriter;
import io.spring.initializr.generator.language.TypeDeclaration;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.generator.spring.util.LambdaSafe;
import org.springframework.beans.factory.ObjectProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * {@link ProjectContributor} for the application's main source code.
 *
 * @param <T> language-specific type declaration
 * @param <C> language-specific compilation unit
 * @param <S> language-specific source code
 * @author Andy Wilkinson
 * @author Stephane Nicoll
 */
public class MybatisConfigSourceCodeProjectContributor<T extends TypeDeclaration, C extends CompilationUnit<T>, S extends SourceCode<T, C>>
		implements ProjectContributor {

	private final ProjectDescription projectDescription;

	private final Supplier<S> sourceFactory;

	private final SourceCodeWriter<S> sourceWriter;

	private final ObjectProvider<MybatisConfigTypeCustomizer<? extends TypeDeclaration>> mainTypeCustomizers;

	private final ObjectProvider<MybatisConfigCompilationUnitCustomizer<?, ?>> mainCompilationUnitCustomizers;

	private final ObjectProvider<MybatisConfigSourceCodeCustomizer<?, ?, ?>> mainSourceCodeCustomizers;

	public MybatisConfigSourceCodeProjectContributor(ProjectDescription projectDescription,
													 Supplier<S> sourceFactory, SourceCodeWriter<S> sourceWriter,
													 ObjectProvider<MybatisConfigTypeCustomizer<?>> mainTypeCustomizers,
													 ObjectProvider<MybatisConfigCompilationUnitCustomizer<?, ?>> mainCompilationUnitCustomizers,
													 ObjectProvider<MybatisConfigSourceCodeCustomizer<?, ?, ?>> mainSourceCodeCustomizers) {
		this.projectDescription = projectDescription;
		this.sourceFactory = sourceFactory;
		this.sourceWriter = sourceWriter;
		this.mainTypeCustomizers = mainTypeCustomizers;
		this.mainCompilationUnitCustomizers = mainCompilationUnitCustomizers;
		this.mainSourceCodeCustomizers = mainSourceCodeCustomizers;
	}

	@Override
	public void contribute(Path projectRoot) throws IOException {
		S sourceCode = this.sourceFactory.get();
		String testName = "MybatisConfig";
		C compilationUnit = sourceCode.createCompilationUnit(this.projectDescription.getPackageName()+".config", testName);
		T testApplicationType = compilationUnit.createTypeDeclaration(testName);
		customizeMainApplicationType(testApplicationType);
		customizeMainSourceCode(sourceCode);
		this.sourceWriter
				.writeTo(
						this.projectDescription.getBuildSystem().getMainSource(
								projectRoot, this.projectDescription.getLanguage()),
						sourceCode);
	}

	@SuppressWarnings("unchecked")
	private void customizeMainApplicationType(T mainApplicationType) {
		List<MybatisConfigTypeCustomizer<?>> customizers = this.mainTypeCustomizers
				.orderedStream().collect(Collectors.toList());
		LambdaSafe
				.callbacks(MybatisConfigTypeCustomizer.class, customizers,
						mainApplicationType)
				.invoke((customizer) -> customizer.customize(mainApplicationType));
	}

	@SuppressWarnings("unchecked")
	private void customizeMainCompilationUnit(C compilationUnit) {
		List<MybatisConfigCompilationUnitCustomizer<?, ?>> customizers = this.mainCompilationUnitCustomizers
				.orderedStream().collect(Collectors.toList());
		LambdaSafe
				.callbacks(MybatisConfigCompilationUnitCustomizer.class, customizers,
						compilationUnit)
				.invoke((customizer) -> customizer.customize(compilationUnit));
	}

	@SuppressWarnings("unchecked")
	private void customizeMainSourceCode(S sourceCode) {
		List<MybatisConfigSourceCodeCustomizer<?, ?, ?>> customizers = this.mainSourceCodeCustomizers
				.orderedStream().collect(Collectors.toList());
		LambdaSafe.callbacks(MybatisConfigSourceCodeCustomizer.class, customizers, sourceCode)
				.invoke((customizer) -> customizer.customize(sourceCode));
	}

}
