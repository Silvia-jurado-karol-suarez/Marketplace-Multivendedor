package com.marketplace.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pedidos")
public class Pedido {


	@Id
    private String id;
	
	private List<Producto> productos;
    private double total;
    private String estado;
	
    public Pedido() {
		
	}

	public Pedido(String id, List<Producto> productos, double total, String estado) {
		
		this.id = id;
		this.productos = productos;
		this.total = total;
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
    
    
}
