package fichier;

public class Fichier{

    private int id;
    private String nom;
    private byte[] donnee;
    private String extension;

    public Fichier(int id, String nom, byte[] donnee, String extension) {
        this.id = id;
        this.nom = nom;
        this.donnee = donnee;
        this.extension = extension;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public byte[] getDonnee() {
		return donnee;
	}

	public void setDonnee(byte[] donnee) {
		this.donnee = donnee;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}


}