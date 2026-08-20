package org.example.examen_final_poo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Configuration Spring Boot exposant un DataSource JDBC vers PostgreSQL.
 *
 * IMPORTANT : ce DataSource ne fait QUE fournir des objets Connection.
 * Aucune abstraction ORM (pas de JpaRepository, pas de JdbcTemplate,
 * pas d'EntityManager). Toutes les requêtes SQL et le mapping
 * ResultSet -> POJO restent écrits manuellement dans la couche dao/.
 */
@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}