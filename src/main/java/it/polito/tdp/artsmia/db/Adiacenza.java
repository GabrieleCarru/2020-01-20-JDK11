package it.polito.tdp.artsmia.db;

public class Adiacenza implements Comparable<Adiacenza>{

	private Integer id1;
	private String nome1;
	private Integer id2;
	private String nome2;
	private Integer peso;
	
	/**
	 * @param id1
	 * @param nome1
	 * @param id2
	 * @param 
	 * @param peso
	 */
	public Adiacenza(Integer id1, String nome1, Integer id2, String nome2, Integer peso) {
		super();
		this.id1 = id1;
		this.nome1 = nome1;
		this.id2 = id2;
		this.nome2 = nome2;
		this.peso = peso;
	}
	public Integer getId1() {
		return id1;
	}
	public Integer getId2() {
		return id2;
	}
	public Integer getPeso() {
		return peso;
	}
	
	public String getNome1() {
		return nome1;
	}
	public void setNome1(String nome1) {
		this.nome1 = nome1;
	}
	public String getNome2() {
		return nome2;
	}
	public void setNome2(String nome2) {
		this.nome2 = nome2;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id1 == null) ? 0 : id1.hashCode());
		result = prime * result + ((id2 == null) ? 0 : id2.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenza other = (Adiacenza) obj;
		if (id1 == null) {
			if (other.id1 != null)
				return false;
		} else if (!id1.equals(other.id1))
			return false;
		if (id2 == null) {
			if (other.id2 != null)
				return false;
		} else if (!id2.equals(other.id2))
			return false;
		return true;
	}
	@Override
	public String toString() {
		//return nome1 + " - " + nome2 + " ---> " + peso;
		return nome1 + " - " + nome2 + " ---> " + peso;
	}
	@Override
	public int compareTo(Adiacenza o) {
		return -(this.peso.compareTo(o.getPeso()));
	}
	
	
	
}
