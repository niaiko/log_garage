package mg.cnaps.services;


import java.util.List;

//import java.sql.Date;
//import java.util.List;
import mg.cnaps.models.TypeRepMod;


public interface TypeRepService extends CRUDService<TypeRepMod> {
	 long seqDemande();
	 
	 List<TypeRepMod> getTypeRepByLib(String libelle);
	 
}
