package com.mageddo.titulosbolsa.api;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mageddo.titulosbolsa.bean.Titulo;

/**
 *
 * @author elvis
 */
public class TituloService {
	 
	public List<Titulo> getTodosTitulos() throws IOException {
		List<Titulo> todosTitulos = new ArrayList<>();
		List<Titulo> resultados;
		for(int i=0; ;i++){
			resultados = getTitulos(i);
			if(resultados.isEmpty())
				break;
			todosTitulos.addAll(resultados);
		}
		return todosTitulos;
	}
	
	/**
	 * 
	 * @param pagina página a receber os resultados, 0 é a primeira
	 * @return os títulos encontrados ou uma lista vazia se a paǵina não conter significando que nas próximas páginas
	 * não existem mais registros
	 * @throws IOException
	 */
	public List<Titulo> getTitulos(int pagina) throws IOException{
		List<Titulo> titulos = new ArrayList<>();
		Document d = Jsoup.parse(new URL(String.format("https://br.financas.yahoo.com/q/cp?s=%%5EBVSP&c=%d", pagina)), 15000);
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
			String valorUltimaData = ultimaTransacao.substring(indiceSeparacaoUltimaTransacao+1);
			Calendar c = Calendar.getInstance();
			
			if(ultimaTransacao.indexOf(" de ") != -1){
				String[] arrayValorUltimaData = ultimaTransacao.substring(indiceSeparacaoUltimaTransacao+1).split(" de ");
				c.set(c.get(Calendar.YEAR), mapMonth(arrayValorUltimaData[1]), new Integer(arrayValorUltimaData[0]));
				c.set(Calendar.HOUR_OF_DAY, 17);
				c.set(Calendar.MINUTE, 0);
			}else{
				final String REGEX_HORA = "(\\d+):(\\d+)";
				final Matcher mPadrao = Pattern.compile(REGEX_HORA).matcher(valorUltimaData);
				if(mPadrao.find() && mPadrao.groupCount() == 2){
					int hora = new Integer(mPadrao.group(1));
					c.set(Calendar.HOUR_OF_DAY, hora);
					c.set(Calendar.MINUTE, new Integer(mPadrao.group(2)));
				}
			}
			t.setUltimaTransacaoData(c.getTime().getTime());
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
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
