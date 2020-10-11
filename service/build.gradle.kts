// Spring Boot 2.2.x, 2.3.x (https://spring.io/projects/spring-cloud)
val springCloudVersion = "Hoxton.RELEASE"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
    }
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
}