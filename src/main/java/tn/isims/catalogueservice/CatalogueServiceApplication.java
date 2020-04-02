package tn.isims.catalogueservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tn.isims.catalogueservice.dao.CategorieRepository;
import tn.isims.catalogueservice.dao.ProductRepository;
import tn.isims.catalogueservice.entities.Categorie;
import tn.isims.catalogueservice.entities.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class CatalogueServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogueServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CategorieRepository categorieRepository , ProductRepository productRepository){
        return args -> {
                categorieRepository.deleteAll();
                productRepository.deleteAll();

            Stream.of("C1 Ordinateurs","C2 Imprimantes").forEach(s -> {
                categorieRepository.save(new Categorie(s.split(" ")[0],s.split(" ")[1],new ArrayList<>()));
            });
            categorieRepository.findAll().forEach(System.out::println);

            Categorie c1 = categorieRepository.findById("C1").get();

            Stream.of("P1","P2","P3","P4").forEach(name -> {
                Product p=  productRepository.save(new Product(null,name,Math.random()*100,c1));
                c1.getProducts().add(p);
                categorieRepository.save(c1);

            });

            Categorie c2 = categorieRepository.findById("C2").get();
            Stream.of("P5","P6").forEach(name -> {
               Product p = productRepository.save(new Product(null,name,Math.random()*100,c2));
                c2.getProducts().add(p);
                categorieRepository.save(c2);

            });

            productRepository.findAll().forEach(product -> {
                System.out.println(product.toString());
            });
        };
    }

}
