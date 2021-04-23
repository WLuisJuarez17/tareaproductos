package com.example.demo.controllers;

import java.util.Collection;


import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Producto;
import com.example.demo.repositories.ProductRepository;

@RestController
@RequestMapping(value = "/prodcutos")
public class ProductController {
	
	@Autowired
	ProductRepository repository; 
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<Producto> getListaProductos(){
		 Iterable<Producto> listaProdcutos = repository.findAll();
		return (Collection<Producto>) listaProdcutos;
	}
	
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Producto getProducto(@PathVariable(name = "id") Long id) {
		Optional<Producto> prodcuto = repository.findById(id);
		Producto result = null;
		if(prodcuto.isPresent()) {
			result = prodcuto.get();
		}
		return result;
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Producto createProducto(@RequestBody Producto producto) {
		Producto nuevoProducto = repository.save(producto);
		return nuevoProducto;
		
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void deleteProducto(@PathVariable(name = "id") Long id) {
		repository.deleteById(id);
	}
	
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Producto updateProducto(@PathVariable(name = "id")Long id, 
			@RequestBody Producto producto) {
		Optional<Producto> oProdcuto = repository.findById(id);
		if(oProdcuto.isPresent()) {
			Producto actual = oProdcuto.get();
			actual.setId(id);
			actual.setName(producto.getName());
			actual.setPrecio(producto.getPrecio());
			Producto updatedProdcut = repository.save(actual);
			return updatedProdcut;
		}
		return null;
		
	}
}
