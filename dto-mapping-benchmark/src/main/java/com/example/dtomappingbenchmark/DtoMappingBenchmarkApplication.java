package com.example.dtomappingbenchmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.dtomappingbenchmark.v4") // 혹은 "v1"
public class DtoMappingBenchmarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtoMappingBenchmarkApplication.class, args);
    }

}
