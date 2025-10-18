package org.aryan.todolistbackend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Bean
    public DataSource dataSource() {
        try {
            if (databaseUrl == null || databaseUrl.isEmpty()) {
                throw new IllegalArgumentException("DATABASE_URL not found in environment variables");
            }

            URI dbUri = new URI(databaseUrl.replace("postgresql://", "postgres://"));
            String[] userInfo = dbUri.getUserInfo().split(":");
            String username = userInfo[0];
            String password = userInfo.length > 1 ? userInfo[1] : "";

            int port = dbUri.getPort() == -1 ? 5432 : dbUri.getPort();  // ✅ default to 5432
            String jdbcUrl = String.format("jdbc:postgresql://%s:%d%s",
                    dbUri.getHost(), port, dbUri.getPath());

            System.out.println("✅ Successfully parsed DATABASE_URL");
            System.out.println("   JDBC URL: " + jdbcUrl);
            System.out.println("   Username: " + username);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);
            config.setDriverClassName("org.postgresql.Driver");

            // Connection Pool settings
            config.setMaximumPoolSize(5);
            config.setMinimumIdle(1);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);

            return new HikariDataSource(config);

        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid DATABASE_URL format: " + databaseUrl, e);
        }
    }
}
