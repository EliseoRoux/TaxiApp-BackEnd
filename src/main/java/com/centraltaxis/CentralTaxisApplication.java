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
        // Verificación de conexión y RLS
        Boolean rlsEnabled = (Boolean) entityManager
                .createNativeQuery("SELECT EXISTS (SELECT 1 FROM pg_policies WHERE tablename = 'cliente')")
                .getSingleResult();
        
        System.out.println("\n=== Configuración de Supabase ===");
        System.out.println(" Conexión exitosa a Supabase");
        System.out.println(" RLS habilitado: " + (rlsEnabled ? "SÍ" : "NO"));
        System.out.println("Modo desarrollo: políticas permisivas activas\n");
    }
}