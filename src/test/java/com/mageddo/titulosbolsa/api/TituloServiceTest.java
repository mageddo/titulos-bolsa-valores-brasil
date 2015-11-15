package com.mageddo.titulosbolsa.api;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mageddo.titulosbolsa.bean.Titulo;

public class TituloServiceTest {
	
	private TituloService service = new TituloService();
	
	@Test
	public void getTodosTitulosTest() throws IOException {
		List<Titulo> lista = service.getTodosTitulos();
		Assert.assertTrue(!lista.isEmpty());
	}
	
	@Test
	public void getTitulosPaginaTest() throws IOException {
		List<Titulo> lista = service.getTitulos(0);
		List<Titulo> lista2 = service.getTitulos(1);
		Assert.assertFalse(lista.isEmpty() && lista2.isEmpty());
		if(!lista.isEmpty()){
			if(!lista2.isEmpty()){
				Assert.assertNotEquals(lista.get(0).getCodigo(),lista2.get(0).getCodigo());
			}
		}
	}
}
