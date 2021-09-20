package fr.isika.cdi07.projet3demo.main;

public class TestStringEmail {
	
	public static void main(String[] args) {
		
		String maChaine = "toto@gmail.com; tiit@toot.com,    gg@test.fr";
		System.out.println("ma Chaine : " + maChaine);
		
		String maChaine2 = maChaine.replace(',',';');
		
		String[] tabChaine = maChaine2.split(";");
		for (String monMot : tabChaine) {
			System.out.println("mot :" + monMot.trim());
		}
		
		
			
	}

}
