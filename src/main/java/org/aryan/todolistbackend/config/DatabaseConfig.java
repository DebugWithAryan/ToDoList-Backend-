package org.aryan.todolistbackend.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${DATABASE_URL")
    private  String databaseUrl;

    @Bean
    public DataSource dataSource(){
        String jdbcUrl;
        String username;
        String password;

        if (databaseUrl != null && !databaseUrl.isEmpty() && !databaseUrl.equals("jdbc:postgresql://localhost:5432/todoapp")){
            try {
                String cleanUrl = databaseUrl.replace("postgresql://", "").replace("postgres://", "");

                String[] parts = cleanUrl.split("@");

                if (parts.length == 2) {
                    String[] credentials = parts[0].split(":");
                    username = credentials[0];
                    password = credentials.length > 1 ? credentials[1] : "";

                    String hostPart = parts[1];

                    String host;
                    String port = "5432";
                    String database;

                    if (hostPart.contains(":")) {
                        String[] hostAndRest = hostPart.split(":");
                        host = hostAndRest[0];
                        String[] portAndDb = hostAndRest[1].split("/");
                        port = portAndDb[0];
                        database = portAndDb.length > 1 ? portAndDb[1] : "";
                    } else {
                        String[] hostAndDb = hostPart.split("/");
                        host = hostAndDb[0];
                        database = hostAndDb.length > 1 ? hostAndDb[1] : "";
                    }

                    jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);

                    System.out.println("✅ Successfully parsed DATABASE_URL");
                    System.out.println("   Host: " + host);
                    System.out.println("   Port: " + port);
                    System.out.println("   Database: " + database);
                    System.out.println("   Username: " + username);

                } else {
                    throw new IllegalArgumentException("Invalid DATABASE_URL format");

                }
            }catch (Exception e){
                throw new IllegalArgumentException("Invalid DATABASE_URL format");

            }
        }else {
            throw new IllegalArgumentException("Invalid DATABASE_URL format");
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("org.postgresql.Driver");

        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);

        System.out.println("🔗 Connecting to database: " + jdbcUrl.replaceAll(":[^:@]+@", ":****@"));

        return new HikariDataSource(config);

    }
}
