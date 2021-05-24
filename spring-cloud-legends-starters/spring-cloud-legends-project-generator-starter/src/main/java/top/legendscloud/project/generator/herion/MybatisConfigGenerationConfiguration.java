///*
// * Copyright 2012-2019 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package top.legendscloud.project.generator.herion;
//
//import io.spring.initializr.generator.language.Annotation;
//import io.spring.initializr.generator.language.TypeDeclaration;
//import io.spring.initializr.generator.project.ProjectDescription;
//import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//
///**
// * @Author herion
// * @Description //TODO
// * @Date  2019/5/8
// * @Param
// * @return
// **/
//@ProjectGenerationConfiguration
//public class MybatisConfigGenerationConfiguration {
//
//	@Autowired
//	private ProjectDescription projectDescription;
//
//	@Bean
//	public MybatisConfigTypeCustomizer<TypeDeclaration> springBootMybatisAnnotator() {
//		System.out.println("MybatisConfigTypeCustomizer w我是注解被执行了");
//		return (typeDeclaration) ->{
//			typeDeclaration.annotate(Annotation.name("org.springframework.boot.SpringBootConfiguration"));
//			typeDeclaration.annotate(Annotation.name("org.springframework.transaction.annotation.EnableTransactionManagement"));
//			typeDeclaration.annotate(Annotation.name("org.mybatis.spring.annotation.MapperScan",(annotation) -> annotation.attribute("value",String.class,projectDescription.getPackageName()+".**.mapper*")));
//		};
//
//	}
//}
