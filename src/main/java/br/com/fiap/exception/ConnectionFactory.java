package br.com.fiap.exception;


import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory extends RuntimeException{
    public ConnectionFactory(String message) {
        super(message);
    }
        private static final AtomicReference<ConnectionFactory> instance = new AtomicReference<>();

        private ConnectionFactory() {

        }

        public ConnectionFactory build() {
            ConnectionFactory result = instance.get();
            if (Objects.isNull(result)) {
                ConnectionFactory factory = new ConnectionFactory();
                if (instance.compareAndSet(null, factory)) {
                    result = factory;
                } else {
                    result = instance.get();
                }
            }
            return result;
        }


        public HikariConfig getConnection() {
            var credenciais = getCredenciais();
            try {
                return credenciais;
            } catch (SQLException e) {
                System.err.println("Não foi possível realizar a conexão com o banco de dados: " + e.getMessage());
            }
            return null;
        }

        private HikariConfig getCredenciais() {

            String url = null, pass = null, user = null, driver = null, debugar = "false";
            var config = new HikariConfig();
            Properties prop = new Properties();
            FileInputStream file;
            Integer pullSize = 3;

            try {
                file = new FileInputStream("src/main/resources/application.properties");
                prop.load(file);
                file.close();

                url = prop.getProperty("datasource.url");
                user = prop.getProperty("datasource.username");
                pass = prop.getProperty("datasource.password");
                driver = prop.getProperty("datasource.driver-class-name");
                debugar = prop.getProperty("datasource.debugar");
                pullSize = Integer.valueOf(prop.getProperty("datasource.pull.size"));

            } catch (FileNotFoundException e) {
                System.err.println("Não encontramos o arquivo de configuração: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Não foi possível ler o arquivo de configuração: " + e.getMessage());
            }

            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(pass);
            config.setMaximumPoolSize(pullSize);

            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmCacheSize", "250");
            config.addDataSourceProperty("prepStmCacheSQLLimit", "2048");

            return config;
        }
    }



}
