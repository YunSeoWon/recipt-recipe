package com.recipt.recipe.infrastructure.jpa

import com.recipt.recipe.infrastructure.configuration.JsonConfig
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.stereotype.Repository
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ComponentScans(
    ComponentScan(
        basePackageClasses = [AbstractReciptRepository::class],
        useDefaultFilters = false,
        includeFilters = [ComponentScan.Filter(Repository::class)]
    ),
    ComponentScan(
        basePackageClasses = [JsonConfig::class],
        includeFilters = [ComponentScan.Filter(type = FilterType.ANNOTATION, classes = [Configuration::class])]
    )
)
abstract class ReciptJpaTest