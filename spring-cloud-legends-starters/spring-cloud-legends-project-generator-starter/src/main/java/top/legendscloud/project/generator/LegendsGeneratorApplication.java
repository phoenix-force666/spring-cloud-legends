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

package top.legendscloud.project.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author herion
 * @Date  2019/4/29
 * @Param
 * @return
 **/
@SpringBootApplication
@EnableCaching
@EnableAsync
@RefreshScope
public class LegendsGeneratorApplication {

//	@Autowired
//	private InitializrProperties initializrProperties;
//
//	@Autowired
//	private InitializrMetadataProvider metadataProvider;


	public static void main(String[] args) {
		SpringApplication.run(LegendsGeneratorApplication.class, args);
	}

//	@PostConstruct
//	public void init() {
//		System.out.println("init...");
//		System.out.println(initializrProperties.getBootVersions().toString());
//		InitializrMetadata initializrMetadata=metadataProvider.get();
//		for (DefaultMetadataElement defaultMetadataElement : initializrMetadata.getBootVersions().getContent()) {
//			System.out.println("metadataProvider:"+ JSON.toJSONString(defaultMetadataElement));
//		}
////		initializrMetadataUpdateStrategy.update()
//		for (DefaultMetadataElement defaultMetadataElement : initializrProperties.getBootVersions()) {
//			System.out.println("initializrProperties:"+JSON.toJSONString(defaultMetadataElement));
//		}
////		initializrMetadata.getBootVersions().getContent().clear();
////		initializrMetadata.getBootVersions().getContent().addAll(initializrProperties.getBootVersions());
//
//		initializrMetadata.updateSpringBootVersions(initializrProperties.getBootVersions());
//	}

}
