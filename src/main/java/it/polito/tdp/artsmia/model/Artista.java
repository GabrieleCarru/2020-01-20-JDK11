package it.polito.tdp.artsmia.model;

public class Artista {

	private Integer artist_id;
	private String artist_name;
	/**
	 * @param artist_id
	 * @param artist_name
	 */
	public Artista(Integer artist_id, String artist_name) {
		super();
		this.artist_id = artist_id;
		this.artist_name = artist_name;
	}
	
	public Integer getArtist_id() {
		return artist_id;
	}

	public void setArtist_id(Integer artist_id) {
		this.artist_id = artist_id;
	}

	public String getArtist_name() {
		return artist_name;
	}

	public void setArtist_name(String artist_name) {
		this.artist_name = artist_name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artist_id == null) ? 0 : artist_id.hashCode());
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
		Artista other = (Artista) obj;
		if (artist_id == null) {
			if (other.artist_id != null)
				return false;
		} else if (!artist_id.equals(other.artist_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Artista [artist_id=" + artist_id + ", artist_name=" + artist_name + "]";
	}
}
