package com.mageddo.titulosbolsa.api;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mageddo.titulosbolsa.bean.Titulo;

/**
 *
 * @author elvis
 */
public class TitulosService {
	 
	public List<Titulo> getTodosTitulos() throws IOException {
		List<Titulo> titulos = new ArrayList<>();
		Document d = Jsoup.parse(new URL("https://br.financas.yahoo.com/q/cp?s=%5EBVSP"), 15000);
		Elements linhas = d.select(".yfnc_tableout1 table tr");
		for(Element e: linhas){
			Elements childs = e.select("td");
			if(childs.isEmpty())
				continue;
			
			Titulo t = new Titulo();
			t.setCodigo(childs.get(0).text());
			t.setNome(childs.get(1).text());
			
			String ultimaTransacao = childs.get(2).text();
			int indiceSeparacaoUltimaTransacao = ultimaTransacao.indexOf(' ');
			String ultimoValorTransacao = ultimaTransacao
					.substring(0, indiceSeparacaoUltimaTransacao)
					.replaceAll(",", ".");
			
			t.setUltimaTransacaoValor(new Double(ultimoValorTransacao));
			String[] valorUltimaData = ultimaTransacao.substring(indiceSeparacaoUltimaTransacao+1).split(" de ");
			Calendar c = Calendar.getInstance();
			c.set(c.get(Calendar.YEAR), mapMonth(valorUltimaData[1]), new Integer(valorUltimaData[0]));
			t.setUltimaTransacaoData(new Date(c.getTime().getTime()).getTime());
			titulos.add(t);
		}
		return titulos;
	}

	private int mapMonth(String string) {
		
		if(string.equals("jan"))
			return 0;
		if(string.equals("fev"))
			return 1;
		if(string.equals("marc"))
			return 2;
		if(string.equals("abr"))
			return 3;
		if(string.equals("mai"))
			return 4;
		if(string.equals("jun"))
			return 5;
		if(string.equals("jul"))
			return 6;
		if(string.equals("ago"))
			return 7;
		if(string.equals("set"))
			return 8;
		if(string.equals("out"))
			return 9;
		if(string.equals("nov"))
			return 10;
		if(string.equals("dez"))
			return 11;
		
		return 0;
	}
}
