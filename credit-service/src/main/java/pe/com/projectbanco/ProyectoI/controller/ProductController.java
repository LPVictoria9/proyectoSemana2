package pe.com.projectbanco.ProyectoI.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.projectbanco.ProyectoI.model.Product;
import pe.com.projectbanco.ProyectoI.service.IProductrService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private IProductrService iProductrService;
    //private IPersonalService iPersonalService;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Mono<Product>> createProduct(@RequestBody Product product) {
        log.info("Start controller method Create");
        Mono<Product> oProduct = iProductrService.create(product);
        return new ResponseEntity<>(oProduct, HttpStatus.CREATED);
    }

    @GetMapping(value = "/findAll", produces = "application/json")
    public ResponseEntity<Flux<Product>> findAllProducts() {
        log.info("Start controller method findAll");
        Flux<Product> listProduct = iProductrService.findAll();
        return new ResponseEntity<Flux<Product>>(listProduct, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<Mono<Product>> changeProduct(@RequestBody Product product) {
        log.info("Start controller method change");
        Mono<Product> p = iProductrService.update(product);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }


    @DeleteMapping("delete/{id}")
    public Flux<ResponseEntity<Void>> deleteProduct(@PathVariable("id") String id)
    {
        return iProductrService.findByIdProduct(id).flatMap(p -> {
            return iProductrService.delete(p).then(Mono.just( new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("findById/{id}")
    public  ResponseEntity<Flux<Product>> findById(@PathVariable("id") String id){
        log.info("Start controller method findById =>", id);
        Flux<Product> oListCustomer = iProductrService.findByIdProduct(id);
        return new ResponseEntity<>(oListCustomer,HttpStatus.OK);
    }

}