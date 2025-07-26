package com.centraltaxis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
public class CentralTaxisApplication implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(CentralTaxisApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Prueba simple de conexión
        String dbName = (String) entityManager
                .createNativeQuery("SELECT current_database()")
                .getSingleResult();

        String dbUser = (String) entityManager
                .createNativeQuery("SELECT current_user")
                .getSingleResult();

        System.out.println("✅ Conexión exitosa a Supabase!");
        System.out.println("📊 Base de datos: " + dbName);
        System.out.println("👤 Usuario: " + dbUser);
    }
}