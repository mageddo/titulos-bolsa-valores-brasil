package com.mageddo.titulosbolsa.bean;


/**
 *
 * @author elvis
 */
public class Titulo {
	private String nome;
	private String codigo;
	private Long volume;
	private Double ultimaTransacaoValor;
	private Long ultimaTransacaoData;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public Double getUltimaTransacaoValor() {
		return ultimaTransacaoValor;
	}

	public void setUltimaTransacaoValor(Double ultimaTransacaoValor) {
		this.ultimaTransacaoValor = ultimaTransacaoValor;
	}

	public Long getUltimaTransacaoData() {
		return ultimaTransacaoData;
	}

	public void setUltimaTransacaoData(Long ultimaTransacaoData) {
		this.ultimaTransacaoData = ultimaTransacaoData;
	}	
	
	
}
